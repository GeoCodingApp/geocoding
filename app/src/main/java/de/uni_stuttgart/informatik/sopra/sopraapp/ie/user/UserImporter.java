package de.uni_stuttgart.informatik.sopra.sopraapp.ie.user;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.user.UserDeserializer;

/**
 * UserImporter uses the userDeserializer to deserialize a json string to a list of users.
 *
 * @author MikeAshi
 */
public class UserImporter {
    private static final String TAG = "UserImporter";

    /**
     * Returns a List of Users from json
     *
     * @param json json
     * @return a List of Users
     */
    public static List<User> toList(String json) {
        List<User> users = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(User.class, new UserDeserializer());
        mapper.registerModule(module);
        try {
            users = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class));
        } catch (IOException e) {
            Log.d(TAG, "toList: " + e.getMessage());
        }
        return users;
    }
}
