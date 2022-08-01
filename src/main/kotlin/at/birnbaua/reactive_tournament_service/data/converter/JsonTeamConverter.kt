package at.birnbaua.reactive_tournament_service.data.converter

import at.birnbaua.reactive_tournament_service.data.entity.Team
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter

class JsonTeamConverter : Converter<String, ArrayList<Team>> {
    private val mapper = ObjectMapper()
    override fun convert(source: String): ArrayList<Team> {
        return try {
            mapper.readValue(source.substring(1,source.length), object : TypeReference<ArrayList<Team>>(){})
        } catch(e: Exception) {
            print("something went wrong in the json team serializer")
            arrayListOf()
        }
    }
}