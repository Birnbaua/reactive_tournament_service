package at.birnbaua.reactive_tournament_service.data.converter

import at.birnbaua.reactive_tournament_service.data.entity.config.TournamentConfig
import at.birnbaua.reactive_tournament_service.utils.duration.DurationSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.core.convert.converter.Converter
import java.time.Duration


class TournamentConfigJsonConverter : Converter<TournamentConfig, String> {
    private val mapper = ObjectMapper()
    init {
        val module = SimpleModule()
        module.addSerializer(Duration::class.java, DurationSerializer())
        mapper.registerModule(module)
    }
    override fun convert(source: TournamentConfig): String {
        return mapper.writeValueAsString(source)
    }
}