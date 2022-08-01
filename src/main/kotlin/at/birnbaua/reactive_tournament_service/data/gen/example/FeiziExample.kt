package at.birnbaua.reactive_tournament_service.data.gen.example

import at.birnbaua.reactive_tournament_service.data.entity.config.GameRoundConfig
import at.birnbaua.reactive_tournament_service.data.entity.config.TournamentConfig
import at.birnbaua.reactive_tournament_service.data.entity.config.TournamentType
import java.time.Duration

class FeiziExample {
    companion object {
        fun genTournamentConfig() : TournamentConfig {
            val config = TournamentConfig("Feizi's Spielplan")
            config.title = "Feizi"
            config.desc = "Feizi sein Spielplan mit Kreuz und Platzierungsspielen"
            config.type = TournamentType.FEIZI
            val gameRound1 = GameRoundConfig("Feizi - Vorrunde")
            gameRound1.title = "Vorrunde"
            gameRound1.noOfSets = 2
            gameRound1.isOnPoints = false
            gameRound1.breakBetweenSets = Duration.ZERO
            gameRound1.timePerSet = Duration.ofMinutes(8)
            gameRound1.breakBetweenMatches = Duration.ofMinutes(5)
            val gameRound2 = GameRoundConfig("Feizi - Zwischenrunde")
            gameRound2.title = "Zwischenrunde"
            gameRound2.noOfSets = 2
            gameRound2.isOnPoints = false
            gameRound2.breakBetweenSets = Duration.ZERO
            gameRound2.timePerSet = Duration.ofMinutes(8)
            gameRound2.breakBetweenMatches = Duration.ofMinutes(5)
            val gameRound3 = GameRoundConfig("Feizi - Kreuzspiele")
            gameRound3.title = "Kreuzspiele"
            gameRound3.noOfSets = 1
            gameRound3.isOnPoints = true
            gameRound3.breakBetweenSets = Duration.ZERO
            gameRound3.pointsPerSet = 21
            gameRound3.timePerSet = Duration.ofMinutes(20)
            gameRound3.breakBetweenMatches = Duration.ZERO
            val gameRound4 = GameRoundConfig("Feizi - Platzierungsspiele")
            gameRound4.title = "Platzierungsspiele"
            gameRound4.noOfSets = 1
            gameRound4.isOnPoints = true
            gameRound4.breakBetweenSets = Duration.ZERO
            gameRound4.pointsPerSet = 21
            gameRound4.timePerSet = Duration.ofMinutes(20)
            gameRound4.breakBetweenMatches = Duration.ZERO
            config.gameRounds[1] = gameRound1
            config.gameRounds[2] = gameRound2
            config.gameRounds[3] = gameRound3
            config.gameRounds[4] = gameRound4
            config.breakAfterGameRound[1] = Duration.ofMinutes(15)
            config.breakAfterGameRound[2] = Duration.ofMinutes(15)
            config.breakAfterGameRound[3] = Duration.ofMinutes(10)
            config.breakAfterGameRound[4] = Duration.ofMinutes(10)
            return config
        }
    }
}