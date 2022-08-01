package at.birnbaua.reactive_tournament_service.data.entity.gameround

import at.birnbaua.reactive_tournament_service.data.entity.abstract.AbstractEntity
import at.birnbaua.reactive_tournament_service.data.entity.config.GameRoundConfig
import at.birnbaua.reactive_tournament_service.data.entity.gameround.group.Group
import at.birnbaua.reactive_tournament_service.data.entity.gameround.match.Match
import java.sql.Timestamp
import java.time.LocalDateTime

open class GameRound(
    override var no: Int = -1
) : Comparable<GameRound>, AbstractEntity() {

    var name: String = "GameRound $no"
    var title: String = "Title $no"
    var desc: String = ""
    var start: Timestamp = Timestamp.valueOf(LocalDateTime.MIN)
    var end: Timestamp = Timestamp.valueOf(LocalDateTime.MIN)
    var config: GameRoundConfig? = null
    var groups = sortedSetOf<Group>()
    var matches = sortedSetOf<Match>()

    override fun compareTo(other: GameRound): Int {
        return this.no - other.no
    }
}
