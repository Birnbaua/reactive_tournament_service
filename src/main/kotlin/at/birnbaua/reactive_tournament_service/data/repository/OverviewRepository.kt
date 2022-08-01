package at.birnbaua.reactive_tournament_service.data.repository

import at.birnbaua.reactive_tournament_service.data.dto.TournamentOverviewDTO
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

interface OverviewRepository : R2dbcRepository<TournamentOverviewDTO, String> {

    fun findAllByEndIsBefore(date: LocalDateTime) : Flux<TournamentOverviewDTO>
    fun findAllByEndIsAfter(date: LocalDateTime) : Flux<TournamentOverviewDTO>
}