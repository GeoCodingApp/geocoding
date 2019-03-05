package de.uni_stuttgart.informatik.sopra.sopraapp.ie.user;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author MikeAshi
 */
public class UserImporterTest {
    private List<User> users;
    private String json;

    @Before
    public void setUp() throws Exception {
        initJson();
    }

    @Test
    public void toList() {
        UserImporter userImporter = new UserImporter();
        users = userImporter.toList(json);
        assertEquals(3, users.size());
        assertEquals("user1", users.get(0).getName());
        assertEquals("user2", users.get(1).getName());
        assertEquals("user3", users.get(2).getName());
    }

    @Test
    public void exceptionTest() {
        users = UserImporter.toList("not a valid json");
        assertTrue(users.isEmpty());
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