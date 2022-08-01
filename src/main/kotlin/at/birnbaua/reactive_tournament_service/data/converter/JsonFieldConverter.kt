package at.birnbaua.reactive_tournament_service.data.converter

import at.birnbaua.reactive_tournament_service.data.entity.Field
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter

class JsonFieldConverter : Converter<String, ArrayList<Field>> {
    private val mapper = ObjectMapper()
    override fun convert(source: String): ArrayList<Field> {
        return mapper.readValue(source.substring(1,source.length), object : TypeReference<ArrayList<Field>>(){})
    }
}