package at.birnbaua.reactive_tournament_service.data.entity

import at.birnbaua.reactive_tournament_service.data.entity.config.TournamentConfig
import at.birnbaua.reactive_tournament_service.data.entity.gameround.GameRound
import at.birnbaua.reactive_tournament_service.data.gen.example.FeiziExample
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Table("tournament")
data class Tournament(
    @Id
    @JvmField var id: String = "xxx"

) : Persistable<String> {
    @Column("name")
    var name: String = "Your name"

    @Column("title")
    var title: String = "Your title"

    @Column("desc")
    var desc: String = ""

    @Column("start")
    var start = LocalDateTime.now()

    @Column("`end`")
    var end = LocalDateTime.now()

    @Column("config")
    var config: TournamentConfig = FeiziExample.genTournamentConfig()

    @Column("teams")
    var teams = arrayListOf<Team>()
    @Column("fields")
    var fields = arrayListOf<Field>()
    @Column("game_rounds")
    var gameRounds = arrayListOf<GameRound>()

    @JsonIgnore
    @org.springframework.data.annotation.Transient
    @JvmField var isNew: Boolean = true

    override fun getId(): String {
        return this.id
    }

    override fun isNew(): Boolean {
        return isNew
    }
}