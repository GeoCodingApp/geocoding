package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.user;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

/**
 * json to User Deserializer.
 *
 * @author MikeAshi
 */
public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer() {
        this(null);
    }

    protected UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String id = node.get("id").asText();
        String name = node.get("name").asText();
        String password = node.get("password").asText();
        boolean isAdmin = node.get("isAdmin").asBoolean();
        return new User(id, name, password, isAdmin);
    }
}
