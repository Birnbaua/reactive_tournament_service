package at.birnbaua.reactive_tournament_service.controller

import kotlinx.coroutines.reactive.awaitFirst
import org.slf4j.LoggerFactory
import org.springframework.http.codec.ServerSentEvent
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@CrossOrigin
@RestController
@RequestMapping("/notification")
class NotificationController {

    private data class Entry(
        var id: String,
        var type: EntityType = EntityType.TOURNAMENT,
        var sink: Sinks.Many<ServerSentEvent<String>> = Sinks.many().replay().latest(),
        var counter: AtomicLong = AtomicLong(0),
        var onStart: (String) -> Flux<ServerSentEvent<String>> = { Flux.empty() }
    )
    private val entries = ConcurrentHashMap<String,Entry>()
    private val types = ConcurrentHashMap<EntityType,(String) -> Mono<ServerSentEvent<String>>>()
    private val log = LoggerFactory.getLogger(NotificationController::class.java)

    @GetMapping("/tournament")
    fun overviewNotification(request: ServerHttpRequest) : Flux<ServerSentEvent<String>> {
        return notification(EntityType.OVERVIEW.name, EntityType.TOURNAMENT, request)
    }

    @GetMapping("/tournament/{id}")
    fun tournamentNotification(@PathVariable id: String, request: ServerHttpRequest) : Flux<ServerSentEvent<String>> {
        return notification(id, EntityType.TOURNAMENT, request)
    }

    @GetMapping("/tournament/{id}/gameround/{no}")
    fun gameRoundNotification(@PathVariable id: String, @PathVariable no: Int, request: ServerHttpRequest) : Flux<ServerSentEvent<String>> {
        return notification("$id-$no", EntityType.GAME_ROUND, request)
    }

    fun sendEvent(id: String, eventType: String, data: String) {
        if(entries.containsKey(id)) {
            val entry = entries[id]!!
            entry.sink.tryEmitNext(
                ServerSentEvent.builder<String>()
                    .id("${entry.counter.incrementAndGet()}")
                    .event(eventType)
                    .data(data)
                    .retry(Duration.ofSeconds(5))
                    .build()
            )
        }
    }

    private fun notification(id: String, type: EntityType = EntityType.OTHER, request: ServerHttpRequest) : Flux<ServerSentEvent<String>> {
        if(entries.containsKey(id).not()) {
            /*
            entries.putIfAbsent(id,Entry(id))
            Important to use this method because if 2 or more requests enter the if clause simultaneously, no starving sinks are created.
             */
            entries.putIfAbsent(id,Entry(id))
            /*
            if 2 or more requests enter the if clause simultaneously, the current entity is sent 2 or more times
            which is not considered a problem, because of low probability and no noticeable performance loss for server and clients if it happens.

            There was the possibility to use a synchronized block each and every time the method is called, but this has a higher performance impact as
            threads are blocked during the check and the execution of the onNew function.
             */
            return if(type != EntityType.OTHER) {
                types[type]!!.invoke(id).doOnNext { t -> entries[id]!!.sink.tryEmitNext(t) }.concatWith(sinkToFlux(id,request)).skip(1)
            } else {
                entries[id]!!.onStart.invoke(id).doOnNext { t -> entries[id]!!.sink.tryEmitNext(t) }.concatWith(sinkToFlux(id,request)).skip(1)
            }
        }
        return sinkToFlux(id,request)
    }

    /**
     * Helper function for creating a flux of the sink
     */
    private fun sinkToFlux(id: String, request: ServerHttpRequest) : Flux<ServerSentEvent<String>> {
        val address = request.remoteAddress
        log.info("New subscriber <$address> registered for notification channel with id: <$id>")
        return entries[id]!!.sink.asFlux()
            .doOnCancel { log.info("Canceled connection of subscriber <$address> for notification channel with id: <$id>") }
            .doOnError { log.info("An error occurred with subscriber <$address> registered for notification channel with id: <$id>") }
    }

    fun onNewSendTournament(onNew: (String)  -> Mono<ServerSentEvent<String>>) {
        types[EntityType.TOURNAMENT] = onNew
    }

    fun onNewSendOverview(onNew: (String)  -> Mono<ServerSentEvent<String>>) {
        types[EntityType.OVERVIEW] = onNew
    }

    fun onNewSendGameRound(onNew: (String)  -> Mono<ServerSentEvent<String>>) {
        types[EntityType.GAME_ROUND] = onNew
    }

    fun onNewSendTeams(onNew: (String)  -> Mono<ServerSentEvent<String>>) {
        types[EntityType.TEAM] = onNew
    }

    fun onNewSendFields(onNew: (String)  -> Mono<ServerSentEvent<String>>) {
        types[EntityType.FIELD] = onNew
    }

    fun onNewSendConfig(onNew: (String)  -> Mono<ServerSentEvent<String>>) {
        types[EntityType.CONFIG] = onNew
    }

    fun onNewSendGeneral(onNew: (String)  -> Mono<ServerSentEvent<String>>) {
        types[EntityType.GENERAL] = onNew
    }
}