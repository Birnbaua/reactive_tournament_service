package at.birnbaua.reactive_tournament_service.data.converter

import at.birnbaua.reactive_tournament_service.data.entity.gameround.GameRound
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter

class GameRoundJsonConverter : Converter<ArrayList<GameRound>,String> {
    private val mapper = ObjectMapper()
    override fun convert(source: ArrayList<GameRound>): String? {
        return mapper.writeValueAsString(source)
    }
}