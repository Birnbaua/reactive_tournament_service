package at.birnbaua.reactive_tournament_service.utils.team

import at.birnbaua.reactive_tournament_service.data.entity.Team
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

class TeamSerializer(t: Class<Team>) : StdSerializer<Team>(t) {
    override fun serialize(value: Team?, jgen: JsonGenerator?, p2: SerializerProvider?) {
        if(value != null && jgen != null) {
            jgen.writeStartObject()
            jgen.writeNumberField("no", value.no)
            jgen.writeStringField("name",value.name)
            jgen.writeEndObject()
        }
    }

}