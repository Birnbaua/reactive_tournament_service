package at.birnbaua.reactive_tournament_service.data.entity.gameround.match

import java.sql.Timestamp
import java.time.LocalDateTime

data class Set(
    var no: Int = -1
) : Comparable<Set> {
    var pointsA: Int = 0
    var pointsB: Int = 0
    var start: Timestamp = Timestamp.valueOf(LocalDateTime.MIN)
    var end: Timestamp = Timestamp.valueOf(LocalDateTime.MAX)

    override fun compareTo(other: Set): Int {
        return this.no - other.no
    }
}
