package at.birnbaua.reactive_tournament_service.data.converter

import at.birnbaua.reactive_tournament_service.data.entity.gameround.GameRound
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter

class JsonGameRoundConverter : Converter<String, ArrayList<GameRound>> {
    private val mapper = ObjectMapper()
    override fun convert(source: String): ArrayList<GameRound> {
        return mapper.readValue(source.substring(1,source.length), object : TypeReference<ArrayList<GameRound>>(){})
    }
}