package at.birnbaua.reactive_tournament_service.data.converter

import at.birnbaua.reactive_tournament_service.data.entity.Team
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter
import java.util.SortedSet

class TeamJsonConverter : Converter<ArrayList<Team>,String> {
    private val mapper = ObjectMapper()
    override fun convert(source: ArrayList<Team>): String {
        return mapper.writeValueAsString(source)
    }
}