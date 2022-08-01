package at.birnbaua.reactive_tournament_service.data.service

import at.birnbaua.reactive_tournament_service.StorageProperties
import at.birnbaua.reactive_tournament_service.data.entity.Field
import at.birnbaua.reactive_tournament_service.data.entity.Team
import at.birnbaua.reactive_tournament_service.data.entity.Tournament
import at.birnbaua.reactive_tournament_service.data.entity.gameround.GameRound
import at.birnbaua.reactive_tournament_service.data.entity.gameround.group.Group
import at.birnbaua.reactive_tournament_service.data.entity.gameround.match.Match
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.net.URI
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Service
class TournamentStore {

    private var store: StorageProperties = StorageProperties()


    private val map: ConcurrentMap<String,Tournament> = ConcurrentHashMap(24)
    private val webClient: WebClient = WebClient.builder().baseUrl("${store.url}:${store.port}${store.path}").build()

    fun findTournamentById(id: String) : Mono<Tournament> {
        return getTournament(id)
    }

    fun findAllTeamsById(id: String) : Flux<Team> {
        return getTournament(id).flatMapIterable { t -> t.teams }
    }

    fun findAllFieldsById(id: String) : Flux<Field> {
        return getTournament(id).flatMapIterable { t -> t.fields }
    }

    fun findAllGameRoundsById(id: String) : Flux<GameRound> {
        return getTournament(id).flatMapIterable { t -> t.gameRounds }
    }

    fun findGameRoundByIdAndNo(id: String, no: Int) : Mono<GameRound> {
        return getTournament(id).map { t -> t.gameRounds.first { gr -> gr.no == no } }
    }

    fun findTeamByIdAndNo(id: String, no: Int) : Mono<Team> {
        return getTournament(id).map { t -> t.teams.first { team -> team.no == no } }
    }

    fun findFieldByIdAndNo(id: String, no: Int) : Mono<Field> {
        return getTournament(id).map { t -> t.fields.first { field -> field.no == no } }
    }

    fun findMatchesByGameRound(id: String, no: Int) : Flux<Match> {
        return getTournament(id).flatMapIterable { t -> t.gameRounds.first { gr -> gr.no == no }.matches }
    }

    fun findMatchesByGameRoundAndTeam(id: String, gameRound: Int, team: Int) : Flux<Match> {
        return getTournament(id)
            .flatMapIterable { t -> t.gameRounds.first { gr -> gr.no == gameRound }.matches }
            .filter { m ->
                m.isMatchOf(team)
            }
    }

    fun findMatchesByGameRoundAndGroup(id: String, gameRound: Int, group: Int) : Flux<Match> {
        val g = findGroupByGameRoundAndNo(id,gameRound,group)
        return getTournament(id)
            .flatMapIterable { t -> t.gameRounds.first { gr -> gr.no == gameRound }.matches }
            .flatMap { m -> Mono.just(m).zipWith(g) }
            .filter { pair ->
                var isMatch = false
                for(team in pair.t2.teams) {
                    if(pair.t1.isMatchOf(team)) {
                        isMatch = true
                        break
                    }
                }
                isMatch
            }.map { pair -> pair.t1 }
    }

    fun findGroupsByGameRound(id: String, no: Int) : Flux<Group> {
        return getTournament(id).flatMapIterable { t -> t.gameRounds.first { gr -> gr.no == no }.groups }
    }

    fun findGroupByGameRoundAndNo(id: String, gameRound: Int, no: Int) : Mono<Group> {
        return getTournament(id).flatMapIterable { t -> t.gameRounds.first { gr -> gr.no == gameRound }.groups }.filter { g -> g.no == no }.toMono()
    }

    fun saveGameRound(id: String, no: Int, gameRound: Mono<GameRound>) : Mono<GameRound> {
        return saveTournament(id,
            getTournament(id)
                .doOnNext { t ->
                    t.gameRounds.remove(t.gameRounds.first { gr -> gr.no == no })
                }.flatMap { t ->
                    gameRound.flatMap { gr ->
                        t.gameRounds.add(gr)
                        Mono.just(t)
                    }
                }
            ).map { t -> t.gameRounds.first { gr -> gr.no == no } }
    }

    private fun saveTournament(id: String, tournament: Mono<Tournament>) : Mono<Tournament> {
        return tournament
            .map { t ->
                t.id = id
                t
            }
            .flatMap { t ->
                webClient.put().uri(URI.create("/$t.id")).retrieve()
                Mono.just(t.id)
            }.doOnNext { t ->
                map.remove(t)
            }.flatMap { t ->
                getTournament(t)
            }
    }

    private fun getTournament(id: String) : Mono<Tournament> {
        return if(map.containsKey(id).not()) {
            webClient.get().uri(URI.create("/$id")).retrieve().bodyToMono(Tournament::class.java).doOnNext { e -> map[id] = e }
        } else {
            Mono.just(map[id]!!)
        }
    }
}