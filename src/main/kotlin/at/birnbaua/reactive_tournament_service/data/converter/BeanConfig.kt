package at.birnbaua.reactive_tournament_service.data.converter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.H2Dialect





@Configuration
class BeanConfig {

    @Bean
    fun customConversions(): R2dbcCustomConversions? {
        val converters: MutableList<Converter<*, *>> = ArrayList()
        converters.add(FieldJsonConverter())
        converters.add(JsonFieldConverter())
        converters.add(TeamJsonConverter())
        converters.add(JsonTeamConverter())
        converters.add(GameRoundJsonConverter())
        converters.add(JsonGameRoundConverter())
        converters.add(TimestampJsonConverter())
        converters.add(LocalDateTimeTimestampConverter())
        converters.add(TournamentConfigJsonConverter())
        converters.add(JsonTournamentConfigConverter())
        return R2dbcCustomConversions.of(H2Dialect.INSTANCE, converters)
    }

}