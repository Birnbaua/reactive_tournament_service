package at.birnbaua.reactive_tournament_service.utils.localdatetime

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.springframework.boot.jackson.JsonComponent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JsonComponent
class LocalDateTimeSerializer(t: Class<LocalDateTime>?) : StdSerializer<LocalDateTime>(t) {
    constructor() : this(null)

    override fun serialize(value: LocalDateTime?, jgen: JsonGenerator?, provider: SerializerProvider?) {
        if(value != null) {
            jgen?.writeString(value.format(DateTimeFormatter.ISO_DATE_TIME))
        } else {
            jgen?.writeObject(null)
        }
    }
}