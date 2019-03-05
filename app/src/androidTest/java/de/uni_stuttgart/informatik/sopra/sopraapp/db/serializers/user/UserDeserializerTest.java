package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author MikeAshi
 */
public class UserDeserializerTest {
    private static final String TAG = "UserDeserializerTest";
    // User Info
    private static final String USER_ID = "DEFAULT_USER_ID";
    private static final String USER_NAME = "DEFAULT_USER_NAME".toLowerCase();
    private static final String USER_PASSWORD = "DEFAULT_USER_PASSWORD";
    private static final Boolean USER_IS_ADMIN = false;
    // Admin Info
    private static final String ADMIN_ID = "ADMIN_ID";
    private static final String ADMIN_NAME = "ADMIN_NAME".toLowerCase();
    private static final String ADMIN_PASSWORD = "ADMIN_PASSWORD";
    private static final Boolean ADMIN_IS_ADMIN = true;
    // users as json
    private String userAsJson;
    private String adminAsJson;
    //
    private SimpleModule module;
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        initObjectMapper();
        initUsersAsJson();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void userDeserializeTest() {
        try {
            User user = mapper.readValue(userAsJson, User.class);
            assertEquals(USER_ID, user.getId());
            assertEquals(USER_NAME, user.getName());
            assertEquals(USER_PASSWORD, user.getPassword());
            assertEquals(USER_IS_ADMIN, user.isAdmin());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void adminDeserializeTest() {
        try {
            User admin = mapper.readValue(adminAsJson, User.class);
            assertEquals(ADMIN_ID, admin.getId());
            assertEquals(ADMIN_NAME, admin.getName());
            assertEquals(ADMIN_PASSWORD, admin.getPassword());
            assertEquals(ADMIN_IS_ADMIN, admin.isAdmin());
        } catch (IOException e) {
            fail();
        }
    }

    private void initObjectMapper() {
        mapper = new ObjectMapper();
        module = new SimpleModule();
        module.addDeserializer(User.class, new UserDeserializer());
        mapper.registerModule(module);
    }

    private void initUsersAsJson() {
        userAsJson = "{" +
                "\"id\":\"" + USER_ID + "\"," +
                "\"name\":\"" + USER_NAME + "\"," +
                "\"password\":\"" + USER_PASSWORD + "\"," +
                "\"isAdmin\":" + USER_IS_ADMIN +
                "}";
        adminAsJson = "{" +
                "\"id\":\"" + ADMIN_ID + "\"," +
                "\"name\":\"" + ADMIN_NAME + "\"," +
                "\"password\":\"" + ADMIN_PASSWORD + "\"," +
                "\"isAdmin\":" + ADMIN_IS_ADMIN +
                "}";
    }
}