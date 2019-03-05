package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.event;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.EventStatusConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;

/**
 * Event to json serializer.
 *
 * @author MikeAshi
 */
public class EventSerializer extends StdSerializer<Event> {

    public EventSerializer() {
        this(null);
    }

    protected EventSerializer(Class<Event> t) {
        super(t);
    }

    @Override
    public void serialize(Event event, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", event.getId());
        gen.writeStringField("name", event.getName());
        gen.writeStringField("status", String.valueOf(EventStatusConverter.toInt(event.getStatus())));
        gen.writeEndObject();
    }
}
