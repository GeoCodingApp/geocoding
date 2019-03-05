package de.uni_stuttgart.informatik.sopra.sopraapp.ie.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

import static org.junit.Assert.assertEquals;

/**
 * @author MikeAshi
 */
public class ExporterHelperTest {
    private ObjectMapper mapper = new ObjectMapper();
    private List<User> users = new ArrayList<>();
    private Event event;
    private String userJson;
    private String eventJson;

    @Before
    public void setUp() throws Exception {
        initEvent();
        initEventJson();
        initUserList();
        initUserJson();
    }

    @Test
    public void getEventJson() throws JsonProcessingException {
        ExporterHelper exporterHelper = new ExporterHelper();
        JsonNode eventNode = exporterHelper.getEventJson(event);
        String eventAsJson = mapper.writeValueAsString(eventNode.get("event"));
        assertEquals(eventJson, eventAsJson);
    }

    @Test
    public void getUsersJson() throws JsonProcessingException {
        JsonNode usersNode = ExporterHelper.getUsersJson(users);
        String userListAsJson = mapper.writeValueAsString(usersNode.get("users"));
        assertEquals(userJson, userListAsJson);
    }


    private void initEvent() {
        event = new Event("id", "name", Event.Status.STARTED);
    }

    private void initEventJson() {
        eventJson = "{\"id\":\"id\",\"name\":\"name\",\"status\":\"0\"}";
    }

    private void initUserList() {
        users.add(new User("id1", "user1", "pass1", false));
        users.add(new User("id2", "user2", "pass2", true));
        users.add(new User("id3", "user3", "pass3", false));
    }

    private void initUserJson() {
        userJson = "[" +
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