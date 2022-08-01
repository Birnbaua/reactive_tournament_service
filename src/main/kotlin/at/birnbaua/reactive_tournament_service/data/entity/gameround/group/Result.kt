package at.birnbaua.reactive_tournament_service.data.entity.gameround.group

import at.birnbaua.reactive_tournament_service.data.entity.Team
import at.birnbaua.reactive_tournament_service.utils.team.TeamSerializer
import com.fasterxml.jackson.databind.annotation.JsonSerialize

data class Result(
    @JsonSerialize(using = TeamSerializer::class)
    var team: Team = Team.DEFAULT
) {
    var rank: Int = 0
    var points: Int = 0
    var gamePoints: Int = 0
    var ownPoints: Int = 0
    var opponentPoints: Int = 0
    var wins: Int = 0
    var draws: Int = 0
    var defeats: Int = 0
    var internal: Int = 0
    var external: Int = 0
    var hasCollidingRankInternal: Boolean = false
    var hasCollidingRankExternal: Boolean = false
}
