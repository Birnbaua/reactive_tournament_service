package at.birnbaua.reactive_tournament_service.utils.localdatetime

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import org.springframework.boot.jackson.JsonComponent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JsonComponent
class LocalDateTimeDeserializer(t: Class<LocalDateTime>?) : StdDeserializer<LocalDateTime>(t) {
    constructor() : this(null)

    override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): LocalDateTime? {
        return if (p0 != null) {
            LocalDateTime.parse(p0.text, DateTimeFormatter.ISO_DATE_TIME)
        } else {
            null
        }
    }
}