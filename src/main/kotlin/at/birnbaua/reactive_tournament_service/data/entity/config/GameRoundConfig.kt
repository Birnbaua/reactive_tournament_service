package at.birnbaua.reactive_tournament_service.data.entity.config

import java.time.Duration

data class GameRoundConfig(
    var name: String = "Default Config"
) : Comparable<GameRoundConfig> {

    var title: String = "Default Title"
    var desc: String = ""
    var noOfSets: Int = 3
    var timePerSet: Duration = Duration.ofMinutes(8)
    var breakBetweenSets: Duration = Duration.ofMinutes(3)
    var breakBetweenMatches: Duration = Duration.ofMinutes(5)
    var pointsPerSet = 21
    var isOnPoints = false

    override fun compareTo(other: GameRoundConfig): Int {
        return this.name.compareTo(other.name)
    }
}