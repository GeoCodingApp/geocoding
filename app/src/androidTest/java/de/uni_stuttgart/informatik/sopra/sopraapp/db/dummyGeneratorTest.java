package de.uni_stuttgart.informatik.sopra.sopraapp.db;

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

import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.UserDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.util.Encryptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@RunWith(AndroidJUnit4.class)
public class dummyGeneratorTest {
    private UserDao UserDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();

        UserDao = db.UserDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void userGeneratorTest() {
        // create dummy users
        dummyGenerator dG = new dummyGenerator(db);
        //
        List<User> users = UserDao.getAll();
        assertTrue(users.size() > 0);
        //
        Optional<User> dbUser = UserDao.getByName("gruppe7");
        if (!dbUser.isPresent()) {
            fail("User not present");
        }
        assertEquals(Encryptor.encrypt("zjqp1y", dbUser.get().getId()), dbUser.get().getPassword());
    }

}
