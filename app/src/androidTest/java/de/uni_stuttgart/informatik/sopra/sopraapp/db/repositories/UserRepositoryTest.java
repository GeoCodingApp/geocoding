package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.util.Encryptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {
    private UserRepository userRepository;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userRepository = new UserRepository(db);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void addUserTest() {
        User user = new User("admin", "admin", true);
        userRepository.add(user);
        //
        Optional<User> returnedUser = userRepository.getByName("admin");
        if (!returnedUser.isPresent()) {
            fail("Returned User not present");
        }
        User rUser = returnedUser.get();
        assertEquals("admin", rUser.getName());
        assertEquals(Encryptor.encrypt("admin", user.getId()), rUser.getPassword());
        assertTrue(rUser.isAdmin());
        ///
        boolean result = userRepository.add(user);
        assertFalse(result);
    }

    @Test
    public void updateUserTest() {
        // Creating a new user
        User user = new User("admin", "admin", true);
        userRepository.add(user);
        // get the new user
        Optional<User> returnedUser = userRepository.getByName("admin");
        if (!returnedUser.isPresent()) {
            fail("Returned User not present");
        }
        // update user
        User rUser = returnedUser.get();
        rUser.setName("user");
        rUser.setPassword("pass");
        rUser.setAdmin(false);
        userRepository.update(rUser);
        // check updates
        returnedUser = userRepository.getById(rUser.getId());
        if (!returnedUser.isPresent()) {
            fail("Returned User not present");
        }
        rUser = returnedUser.get();
        //
        assertEquals("user", rUser.getName());
        assertEquals(Encryptor.encrypt("pass", rUser.getId()), rUser.getPassword());
        assertFalse(rUser.isAdmin());
    }

    @Test
    public void addAllTest() {
        User[] users = {
                new User("user1", "pass1", false),
                new User("user2", "pass2", false),
                new User("user3", "pass3", false),
                new User("user4", "pass4", false),
                new User("user5", "pass5", false),
                new User("user6", "pass6", false),
                new User("user7", "pass7", false),
        };
        // add users array
        userRepository.addAll(users);
        // check if users are inserted
        List<User> returnedUsers = userRepository.getAll();
        assertEquals(7, returnedUsers.size());
    }

    @Test
    public void getAdminsTest() {
        User[] users = {
                new User("admin1", "admin", true),
                new User("user1", "pass1", false),
                new User("user2", "pass2", false),
                new User("user3", "pass3", false),
                new User("user4", "pass4", false),
                new User("user5", "pass5", false),
                new User("admin2", "admin", true),
                new User("user6", "pass6", false),
                new User("user7", "pass7", false),
                new User("admin3", "admin", true),
                new User("admin4", "admin", true),
        };
        // add users array
        userRepository.addAll(users);
        // get Admins
        List<User> returnedAdmins = userRepository.getAdmins();
        assertEquals(4, returnedAdmins.size());
    }

    @Test
    public void deleteUserTest() {
        User user = new User("user", "pass", false);
        userRepository.add(user);
        // check if the user is inserted
        Optional<User> returnedUser = userRepository.getById(user.getId());
        if (!returnedUser.isPresent()) {
            fail("Returned User not present");
        }
        // delete user
        userRepository.delete(user);
        returnedUser = userRepository.getById(user.getId());
        if (returnedUser.isPresent()) {
            fail("user was not deleted");
        }
    }

    @Test
    public void deleteByIdTest() {
        User user = new User("user", "pass", false);

        userRepository.add(user);
        // check if the user is inserted
        Optional<User> returnedUser = userRepository.getById(user.getId());
        if (!returnedUser.isPresent()) {
            fail("Returned User not present");
        }
        // delete user
        userRepository.deleteById(user.getId());
        returnedUser = userRepository.getById(user.getId());
        if (returnedUser.isPresent()) {
            fail("user was not deleted");
        }

    }

    @Test
    public void deleteAllTest() {
        User[] users = {
                new User("admin1", "admin", true),
                new User("user1", "pass1", false),
                new User("user2", "pass2", false),
                new User("user3", "pass3", false),
                new User("user4", "pass4", false),
                new User("user5", "pass5", false),
                new User("admin2", "admin", true),
                new User("user6", "pass6", false),
                new User("user7", "pass7", false),
                new User("admin3", "admin", true),
                new User("admin4", "admin", true),
        };
        // add users array
        userRepository.addAll(users);
        List<User> returnedUsers = userRepository.getAll();
        assertEquals(11, returnedUsers.size());
        // delete all
        userRepository.deleteAll();
        returnedUsers = userRepository.getAll();
        assertEquals(0, returnedUsers.size());
    }
}
