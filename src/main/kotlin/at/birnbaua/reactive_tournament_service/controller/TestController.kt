package at.birnbaua.reactive_tournament_service.controller

import at.birnbaua.reactive_tournament_service.data.entity.Tournament
import at.birnbaua.reactive_tournament_service.data.repository.TournamentRepository
import at.birnbaua.reactive_tournament_service.data.service.TournamentService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import java.time.LocalTime
import java.util.concurrent.atomic.AtomicLong


@RestController
class TestController {

    @Autowired
    private lateinit var repo: TournamentRepository

    @Autowired
    private lateinit var service: TournamentService

    @Autowired
    private lateinit var mapper: ObjectMapper

    private val sink: Sinks.Many<ServerSentEvent<String>>  = Sinks.many().replay().latest()
    private val counter = AtomicLong()

    @GetMapping
    fun test() : Flux<Tournament> {
        return repo.findAll()
    }

    @GetMapping("/tournament")
    fun getTournament() : Mono<Tournament> {
        return service.findById("xxx")
    }

    @GetMapping("/sse-stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun testEventStream() : Flux<ServerSentEvent<String>> {
        return sink.asFlux()
    }

    @Scheduled(initialDelay = 0, fixedRate = 1000*60*9999)
    fun sched() {
        service.findById("xxx").doOnNext { t -> sink.tryEmitNext(
            ServerSentEvent.builder<String>()
                .id(counter.incrementAndGet().toString())
                .event("tournament")
                .data(mapper.writeValueAsString(t))
                .build()
        ) }.block()
    }
}
