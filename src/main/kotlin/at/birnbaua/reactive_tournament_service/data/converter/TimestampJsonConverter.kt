package at.birnbaua.reactive_tournament_service.data.converter

import org.springframework.core.convert.converter.Converter
import java.sql.Timestamp
import java.time.LocalDateTime

class TimestampJsonConverter : Converter<Timestamp,LocalDateTime> {
    override fun convert(source: Timestamp): LocalDateTime {
        return source.toLocalDateTime()
    }

}