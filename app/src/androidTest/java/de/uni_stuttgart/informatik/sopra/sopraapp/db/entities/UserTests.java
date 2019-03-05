package de.uni_stuttgart.informatik.sopra.sopraapp.db.entities;

import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UserTests {

    private static final String DEFAULT_USER_ID = "DEFAULT_USER_ID";
    private static final String DEFAULT_USER_FIRST_NAME = "DEFAULT_USER_FIRST_NAME";
    private static final String DEFAULT_USER_PASSWORD = "DEFAULT_USER_PASSWORD";
    private User testUser;

    @Before
    public void initUsers() {
        this.testUser = new User(DEFAULT_USER_ID, DEFAULT_USER_FIRST_NAME, "", false);
        this.testUser.setPassword(DEFAULT_USER_PASSWORD);
    }

    @Test
    public void testConstructor() {
        assertEquals(DEFAULT_USER_ID, testUser.getId());
        assertEquals(DEFAULT_USER_FIRST_NAME.toLowerCase(), testUser.getName());
        assertEquals(DEFAULT_USER_PASSWORD, testUser.getDecryptedPassword());
        assertFalse(testUser.isAdmin());
    }

    /**
     * Tests if default user is an admin (should be negative).
     */
    @Test
    public void testDefaultUserIsAdmin() {
        assertFalse(this.testUser.isAdmin());
    }

    /**
     * Tests setAdmin method.
     */
    @Test
    public void testSetAdmin() {
        this.testUser.setAdmin(true);
        assertTrue(this.testUser.isAdmin());
        this.testUser.setAdmin(false);
        assertFalse(this.testUser.isAdmin());

    }

    /**
     * Tests setId .
     */
    @Test
    public void testSetId() {
        String newId = "new ID";
        assertNotEquals(newId, testUser.getId());
        testUser.setId(newId);
        assertEquals(newId, testUser.getId());
        testUser.setId(DEFAULT_USER_ID);
    }

    /**
     * Checks if password is stored encrypted.
     */
    @Test
    public void testUserPasswordEncryption() {
        assertNotEquals(DEFAULT_USER_PASSWORD, this.testUser.getPassword());
    }

    /**
     * Tests password encryption when setting a new password via User#setPassword.
     */
    @Test
    public void testUserSetPasswordEncryption() {
        String testPassword = "this is some random testing password";
        this.testUser.setPassword(testPassword);
        assertNotEquals("Test passwords must be different", testPassword, DEFAULT_USER_PASSWORD);
        assertNotEquals(testPassword, this.testUser.getPassword());
    }

    /**
     * Tests if the user password is decrypted properly.
     */
    @Test
    public void testUserPasswordDecryption() {
        assertEquals(DEFAULT_USER_PASSWORD, this.testUser.getDecryptedPassword());
    }
}
