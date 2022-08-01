package at.birnbaua.reactive_tournament_service.data.service

import at.birnbaua.reactive_tournament_service.data.entity.Team
import at.birnbaua.reactive_tournament_service.data.exception.ResourceNotFoundException
import at.birnbaua.reactive_tournament_service.data.repository.TournamentRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TeamService {

    @Autowired
    private lateinit var repo: TournamentRepository

    @Autowired
    private lateinit var ts: TournamentService

    @Autowired
    private lateinit var mapper: ObjectMapper

    private val log = LoggerFactory.getLogger(TeamService::class.java)

    @Cacheable(value = ["teams"], key = "#id")
    fun findTeamsByTournament(id: String) : Flux<Team> {
        return repo.findTeamsOfTournament(id)
            .doOnError { throw ResourceNotFoundException("Tournament with id: <$id> not existing in the database.") }
            .flatMapIterable { str ->
                mapper.readValue(str.substring(1,str.length-1), object : TypeReference<ArrayList<Team>>(){})
            }.cache()
    }

    fun findTeamByTournamentAndNo(id: String, no: Int) : Mono<Team> {
        return findTeamsByTournament(id)
            .doOnError { throw ResourceNotFoundException("Team with no: $no of tournament with id: <$id> not existing in the database.") }
            .filter { t -> t.no == no }.last().cache()
    }

    @CachePut(value = ["tournaments","tournamentsExists","teams"], key = "#id")
    fun replace(id: String, teams: Flux<Team>) : Flux<Team> {
        val tournament = ts.findById(id).doOnNext { t ->
            log.debug("Clear team list of tournament $id")
            t.teams.clear()
        }
        return teams.zipWith(tournament)
            .doOnNext{
                log.debug("Add team ${it.t1.name}")
                it.t2.teams.add(it.t1)
            }.map { it.t2 }
            .concatWith(ts.save(id,tournament)).last()
            .doOnNext { log.debug("Replace ${it.teams.size} teams for tournament ${it.id}") }
            .flatMapIterable { it.teams }
    }

    @CacheEvict(value = ["tournaments","tournamentsExists","teams"], key = "#id")
    fun deleteTeamsById(id: String) : Mono<Unit> {
        return ts.findById(id)
            .doOnError { throw ResourceNotFoundException("Tournament with id: <$id> not existing in the database.") }
            .doOnNext { t ->
                log.debug("Clear teams of tournament with id: $id")
                t.teams.clear()
            }.flatMap { t ->
                log.debug("Delete teams of tournament $id")
                ts.save(t)
            }.map {}
    }
}