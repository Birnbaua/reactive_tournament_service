package at.birnbaua.reactive_tournament_service.utils.duration

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.springframework.boot.jackson.JsonComponent
import java.time.Duration

@JsonComponent
class DurationSerializer(t: Class<Duration>?) : StdSerializer<Duration>(t) {
    constructor() : this(null)

    override fun serialize(value: Duration?, jgen: JsonGenerator?, provider: SerializerProvider?) {
        jgen?.writeString("${value?.toHours()}:${value?.toMinutesPart()}:${value?.toSecondsPart()}")
    }
}