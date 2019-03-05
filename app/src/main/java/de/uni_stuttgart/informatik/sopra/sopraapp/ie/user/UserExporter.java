package de.uni_stuttgart.informatik.sopra.sopraapp.ie.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.user.UserSerializer;

/**
 * UserExporter uses the userSerializer to serialize a list of users returning it as json.
 *
 * @author MikeAshi
 */
public class UserExporter {
    protected static ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns a list of users as json.
     *
     * @param users join to be exported
     * @return json representation of the given event
     */
    public static String toJson(List<User> users) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
