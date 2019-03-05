package de.uni_stuttgart.informatik.sopra.sopraapp.ie.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author MikeAshi
 */
public class UserExporterTest {
    private static final String TAG = "UserExporterTest";
    private List<User> users = new ArrayList<>();
    private String json;


    @Before
    public void setUp() throws Exception {
        initUserList();
        initJson();
    }

    @Test
    public void export() {
        UserExporter userExporter = new UserExporter();
        String userListAsJson = userExporter.toJson(users);
        assertEquals(json, userListAsJson);
    }

    @Test
    public void exceptionTest() throws JsonProcessingException {
        ObjectMapper mapper = spy(new ObjectMapper());
        when(mapper.writeValueAsString(anyList())).thenThrow(new JsonProcessingException("Exception thrown") {
        });
        UserExporter userExporter = new UserExporter();
        userExporter.mapper = mapper;
        json = userExporter.toJson(new ArrayList<User>());
        assertEquals("", json);
    }

    private void initUserList() {
        users.add(new User("id1", "user1", "pass1", false));
        users.add(new User("id2", "user2", "pass2", true));
        users.add(new User("id3", "user3", "pass3", false));
    }

    private void initJson() {
        json = "[" +
                "{" +
                "\"id\":\"id1\"," +
                "\"name\":\"user1\"," +
                "\"password\":\"pass1\"," +
                "\"isAdmin\":false" +
                "}," +
                "{" +
                "\"id\":\"id2\"," +
                "\"name\":\"user2\"," +
                "\"password\":\"pass2\"," +
                "\"isAdmin\":true" +
                "}," +
                "{" +
                "\"id\":\"id3\"," +
                "\"name\":\"user3\"," +
                "\"password\":\"pass3\"," +
                "\"isAdmin\":false" +
                "}" +
                "]";
    }
}