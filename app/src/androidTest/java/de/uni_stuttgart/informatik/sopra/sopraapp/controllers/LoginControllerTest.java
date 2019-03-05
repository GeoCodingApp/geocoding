package de.uni_stuttgart.informatik.sopra.sopraapp.controllers;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class LoginControllerTest {
    private AppDatabase db;
    private LoginController loginController;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        loginController = LoginController.getInstance(context);
        AppDatabase.reset();
    }

    @After
    public void closeDb() throws IOException {
        AppDatabase.reset();
    }

    @Test
    public void LoginTest() {
        assertFalse(loginController.login("Leonard", "pass"));
        assertTrue(loginController.login("Gruppe1", "d7h8ic"));
        assertTrue(loginController.login("Gruppe4", "dwk3d9"));
        loginController.logout();
        assertNull(loginController.getCurrentUser());
    }

    @Test
    public void isAdminTest() {
        loginController.login("Gruppe1", "d7h8ic");
        assertFalse(loginController.isAdmin());
        loginController.logout();
        loginController.login("admin", "pass");
        assertTrue(loginController.isAdmin());
    }


}
