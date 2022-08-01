package at.birnbaua.reactive_tournament_service.data.repository

import at.birnbaua.reactive_tournament_service.data.entity.Team
import at.birnbaua.reactive_tournament_service.data.entity.Tournament
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface TournamentRepository : R2dbcRepository<Tournament,String> {

    @Modifying
    @Query("$1")
    fun execute(statement: String) : Mono<Void>

    @Modifying
    @Query("CREATE TABLE IF NOT EXISTS tournament (\n" +
            "    id VARCHAR_IGNORECASE(16) PRIMARY KEY,\n" +
            "    name VARCHAR_IGNORECASE(255) NOT NULL,\n" +
            "    title VARCHAR(255) NOT NULL DEFAULT 'Default Title',\n" +
            "    desc VARCHAR(4000) NOT NULL DEFAULT '',\n" +
            "    start TIMESTAMP NOT NULL,\n" +
            "    `end` TIMESTAMP NOT NULL,\n" +
            "    config JSON NOT NULL,\n" +
            "    fields JSON NOT NULL,\n" +
            "    teams JSON NOT NULL,\n" +
            "    game_rounds JSON NOT NULL,\n" +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),\n" +
            "    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP()\n" +
            ");")
    fun execute() : Mono<Void>

    @Query("SELECT t.teams FROM TOURNAMENT t WHERE t.id=?1")
    fun findTeamsOfTournament(id: String) : Mono<String>

    @Query("SELECT t.fields FROM TOURNAMENT t WHERE t.id=?1")
    fun findFieldsOfTournament(id: String) : Mono<String>
}