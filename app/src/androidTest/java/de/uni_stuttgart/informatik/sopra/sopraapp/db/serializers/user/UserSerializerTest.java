package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author MikeAshi
 */
public class UserSerializerTest {

    // USER Info
    private static final String USER_ID = "DEFAULT_USER_ID";
    private static final String USER_NAME = "DEFAULT_USER_NAME";
    private static final String USER_PASSWORD = "DEFAULT_USER_PASSWORD";
    private static final Boolean USER_IS_ADMIN = false;
    // Admin Info
    private static final String ADMIN_ID = "ADMIN_ID";
    private static final String ADMIN_NAME = "ADMIN_NAME";
    private static final String ADMIN_PASSWORD = "ADMIN_PASSWORD";
    private static final Boolean ADMIN_IS_ADMIN = true;
    private ObjectMapper mapper;
    private SimpleModule module;
    private User user;
    private User admin;

    @Before
    public void setUp() throws Exception {
        initObjectMapper();
        // init user
        user = new User(USER_ID, USER_NAME, USER_PASSWORD, USER_IS_ADMIN);
        // init admin
        admin = new User(ADMIN_ID, ADMIN_NAME, ADMIN_PASSWORD, ADMIN_IS_ADMIN);
    }

    private void initObjectMapper() {
        mapper = new ObjectMapper();
        module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializer());
        mapper.registerModule(module);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void userSerializeTest() {
        try {
            String userAsJson = mapper.writeValueAsString(user);
            assertEquals(
                    "{" +
                            "\"id\":\"" + USER_ID + "\"," +
                            "\"name\":\"" + USER_NAME.toLowerCase() + "\"," +
                            "\"password\":\"" + USER_PASSWORD + "\"," +
                            "\"isAdmin\":" + USER_IS_ADMIN +
                            "}"
                    , userAsJson);
        } catch (JsonProcessingException e) {
            fail();
        }
    }

    @Test
    public void adminSerializeTest() {
        try {
            String adminAsJson = mapper.writeValueAsString(admin);
            assertEquals(
                    "{" +
                            "\"id\":\"" + ADMIN_ID + "\"," +
                            "\"name\":\"" + ADMIN_NAME.toLowerCase() + "\"," +
                            "\"password\":\"" + ADMIN_PASSWORD + "\"," +
                            "\"isAdmin\":" + ADMIN_IS_ADMIN +
                            "}"
                    , adminAsJson);
        } catch (JsonProcessingException e) {
            fail();
        }
    }
}