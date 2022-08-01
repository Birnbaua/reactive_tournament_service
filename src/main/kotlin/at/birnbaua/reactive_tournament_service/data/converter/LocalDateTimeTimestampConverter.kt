package at.birnbaua.reactive_tournament_service.data.converter

import org.springframework.core.convert.converter.Converter
import java.sql.Timestamp
import java.time.LocalDateTime

class LocalDateTimeTimestampConverter : Converter<LocalDateTime,Timestamp> {
    override fun convert(source: LocalDateTime): Timestamp? {
        return Timestamp.valueOf(source)
    }
}