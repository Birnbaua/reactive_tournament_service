package at.birnbaua.reactive_tournament_service.data.entity.gameround.match

import at.birnbaua.reactive_tournament_service.data.entity.Team
import at.birnbaua.reactive_tournament_service.utils.team.TeamSerializer
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.sql.Timestamp
import java.time.LocalDateTime

data class Match(
    var no: Int = -1
) : Comparable<Match> {

    var field: Int = -1

    var teamA: Team = Team.DEFAULT
    var teamB: Team = Team.DEFAULT
    var referee: Team = Team.DEFAULT

    var start: Timestamp = Timestamp.valueOf(LocalDateTime.MIN)
    var end: Timestamp = Timestamp.valueOf(LocalDateTime.MIN)
    var sets = sortedSetOf<Set>()

    override fun compareTo(other: Match): Int {
        return this.no - other.no
    }

    fun isMatchOf(team: Int) : Boolean {
        return teamA.no == team || teamB.no == team || referee.no == team
    }

    fun isMatchOf(team: Team) : Boolean {
        return teamA == team || teamB == team || referee == team
    }
}
