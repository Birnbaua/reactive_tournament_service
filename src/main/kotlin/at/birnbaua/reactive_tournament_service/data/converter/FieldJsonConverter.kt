package at.birnbaua.reactive_tournament_service.data.converter

import at.birnbaua.reactive_tournament_service.data.entity.Field
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter

class FieldJsonConverter : Converter<ArrayList<Field>, String> {
    private val mapper = ObjectMapper()
    override fun convert(source: ArrayList<Field>): String {
        return mapper.writeValueAsString(source)
    }
}