package at.birnbaua.reactive_tournament_service.startup

import at.birnbaua.reactive_tournament_service.data.converter.JsonTournamentConfigConverter
import at.birnbaua.reactive_tournament_service.data.converter.TournamentConfigJsonConverter
import at.birnbaua.reactive_tournament_service.data.entity.Tournament
import at.birnbaua.reactive_tournament_service.data.entity.config.TournamentConfig
import at.birnbaua.reactive_tournament_service.data.repository.TournamentRepository
import at.birnbaua.reactive_tournament_service.data.service.TeamService
import at.birnbaua.reactive_tournament_service.data.service.TournamentService
import at.birnbaua.reactive_tournament_service.utils.duration.DurationDeserializer
import at.birnbaua.reactive_tournament_service.utils.duration.DurationSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.Duration


@Configuration
@EnableScheduling
class StartUpScheduler {

    @Autowired
    private lateinit var repo: TournamentRepository

    @Autowired
    private lateinit var service: TournamentService

    @Autowired
    private lateinit var ts: TeamService

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var cacheManager: CacheManager

    private val map = ObjectMapper()

    @Scheduled(initialDelay = 1000*15, fixedRate = 1000*3600*24)
    fun data() {
        repo.execute().block()
        repo.existsById("xxx").flatMap { b ->
            val t = Tournament()
            if(b) {
                t.isNew = false
            }
            service.save(t)
        }.block()
    }

    @Scheduled(initialDelay = 0, fixedRate = 1000*60*50)
    fun test() {
        //println(ts.findTeamsByTournament("xxx").count().block())
    }
}