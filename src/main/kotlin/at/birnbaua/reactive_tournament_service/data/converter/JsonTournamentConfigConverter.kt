package at.birnbaua.reactive_tournament_service.data.converter

import at.birnbaua.reactive_tournament_service.data.entity.config.TournamentConfig
import at.birnbaua.reactive_tournament_service.utils.duration.DurationDeserializer
import at.birnbaua.reactive_tournament_service.utils.duration.DurationSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.core.convert.converter.Converter
import java.time.Duration

class JsonTournamentConfigConverter : Converter<String,TournamentConfig> {
    private val mapper = ObjectMapper()
    init {
        val module = SimpleModule()
        module.addDeserializer(Duration::class.java, DurationDeserializer())
        module.addSerializer(Duration::class.java, DurationSerializer())
        mapper.registerModule(module)
    }

    override fun convert(source: String): TournamentConfig {
        return mapper.readValue(source.substring(1,source.length-1).replace("\\",""), TournamentConfig::class.java)
    }
}