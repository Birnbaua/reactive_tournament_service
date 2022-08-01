package at.birnbaua.reactive_tournament_service.data.entity.config

import java.time.Duration

data class TournamentConfig(
    var name: String = "Double Elimination"
) {
    var title: String = "Default Title"
    var desc: String = ""
    var type: TournamentType = TournamentType.DOUBLE_ELIMINATION
    var gameRounds = sortedMapOf<Int,GameRoundConfig>()
    var breakAfterGameRound = sortedMapOf<Int, Duration>()
}
