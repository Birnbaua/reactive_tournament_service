package at.birnbaua.reactive_tournament_service.data.service

import at.birnbaua.reactive_tournament_service.controller.EntityType
import at.birnbaua.reactive_tournament_service.controller.NotificationController
import at.birnbaua.reactive_tournament_service.controller.NotificationType
import at.birnbaua.reactive_tournament_service.data.dto.TournamentOverviewDTO
import at.birnbaua.reactive_tournament_service.data.entity.Tournament
import at.birnbaua.reactive_tournament_service.data.exception.ResourceNotFoundException
import at.birnbaua.reactive_tournament_service.data.repository.OverviewRepository
import at.birnbaua.reactive_tournament_service.data.repository.TournamentRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.LocalDateTime

@Service
class TournamentService(@Autowired var notificationController: NotificationController) {

    @Autowired
    private lateinit var repo: TournamentRepository
    @Autowired
    private lateinit var overviewRepo: OverviewRepository

    @Autowired
    private lateinit var mapper: ObjectMapper

    private val log = LoggerFactory.getLogger(TournamentService::class.java)

    init {
        notificationController.onNewSendTournament { id ->
            existsById(id).flatMap {
                if(it) {
                    findById(id).map { t -> ServerSentEvent.builder<String>().id("0").event("${EntityType.TOURNAMENT}-${NotificationType.READ}").data(mapper.writeValueAsString(t)).build() }
                } else {
                    return@flatMap Mono.error(ResourceNotFoundException(""))
                }
            }
        }
        notificationController.onNewSendOverview {
            Mono.just(
                ServerSentEvent.builder<String>().id("0").event("${EntityType.OVERVIEW}-${NotificationType.READ}")
                    .data(
                        mapper.writeValueAsString(findAllOverview().subscribeOn(Schedulers.boundedElastic()).collectList())
                    ).build()
            )
        }
    }

    fun findAllOverview() : Flux<TournamentOverviewDTO> {
        return overviewRepo.findAll()
    }

    fun findAllOverviewFuture() : Flux<TournamentOverviewDTO> {
        return overviewRepo.findAllByEndIsBefore(LocalDateTime.now())
    }

    fun findAllOverviewPast() : Flux<TournamentOverviewDTO> {
        return overviewRepo.findAllByEndIsAfter(LocalDateTime.now())
    }

    fun findById(id: String) : Mono<Tournament> {
        return repo.findById(id).cache()
    }

    fun save(id: String, pub: Mono<Tournament>) : Mono<Tournament> {
        return this.existsById(id).zipWith(pub)
            .flatMap { pair ->
                pair.t2.isNew = pair.t1
                this.save(pair.t2)
            }
    }

    fun save(entity: Tournament) : Mono<Tournament> {
        return repo.save(entity)
            .doOnSuccess {
                log.info("Saved tournament with id: ${it.id}")
                notificationController.sendEvent(it.id,"${EntityType.TOURNAMENT}-${NotificationType.UPDATE}", mapper.writeValueAsString(it))
                notificationController.sendEvent(EntityType.OVERVIEW.name,"${EntityType.OVERVIEW}-${NotificationType.UPDATE}", mapper.writeValueAsString(it))
            }
    }

    fun existsById(id: String) : Mono<Boolean> {
        return repo.existsById(id)
    }

    fun deleteById(id: String) : Mono<Void> {
        return repo.deleteById(id)
            .doOnSuccess {
                log.info("Deleted tournament with id: $id")
                notificationController.sendEvent(id,"${EntityType.TOURNAMENT}-${NotificationType.DELETE}","{\"id\":\"$id\"}")
                notificationController.sendEvent(EntityType.OVERVIEW.name,"${EntityType.TOURNAMENT}-${NotificationType.DELETE}","{\"id\":\"$id\"}")
            }
    }
}