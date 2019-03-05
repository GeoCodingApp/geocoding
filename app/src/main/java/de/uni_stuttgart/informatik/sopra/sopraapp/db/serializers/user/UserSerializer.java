package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.user;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

/**
 * User to json serializer.
 *
 * @author MikeAshi
 */
public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    protected UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeStartObject();
        jgen.writeStringField("id", user.getId());
        jgen.writeStringField("name", user.getName());
        jgen.writeStringField("password", user.getPassword());
        jgen.writeBooleanField("isAdmin", user.isAdmin());
        jgen.writeEndObject();
    }
}
