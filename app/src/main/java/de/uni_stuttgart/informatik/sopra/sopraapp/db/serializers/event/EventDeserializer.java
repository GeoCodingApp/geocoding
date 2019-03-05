package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.EventStatusConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;

/**
 * json to Event Deserializer.
 *
 * @author MikeAshi
 */
public class EventDeserializer extends StdDeserializer<Event> {

    public EventDeserializer() {
        this(null);
    }

    protected EventDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Event deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String id = node.get("id").asText();
        String name = node.get("name").asText();
        Event.Status status = EventStatusConverter.toEnum(node.get("status").asInt());
        return new Event(id, name, status);
    }
}
