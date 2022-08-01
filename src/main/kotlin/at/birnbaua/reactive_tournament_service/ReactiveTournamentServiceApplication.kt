package at.birnbaua.reactive_tournament_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
class ReactiveTournamentServiceApplication

fun main(args: Array<String>) {
    runApplication<ReactiveTournamentServiceApplication>(*args)
}
