package at.birnbaua.reactive_tournament_service.utils.duration

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import org.springframework.boot.jackson.JsonComponent
import java.time.Duration

@JsonComponent
class DurationDeserializer(t: Class<Duration>?) : StdDeserializer<Duration>(t) {
    constructor() : this(null)

    override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): Duration {
        val arr = p0?.text?.split(":")
        val duration = Duration.ZERO
        arr?.get(0)?.let { duration.plusHours(it.toLong()) }
        arr?.get(1)?.let { duration.plusMinutes(it.toLong()) }
        arr?.get(2)?.let { duration.plusSeconds(it.toLong()) }
        return duration
    }
}