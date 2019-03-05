package de.uni_stuttgart.informatik.sopra.sopraapp.controllers;

import android.content.Context;

import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.util.Encryptor;

/**
 * this class is responsible of the login
 * and logout functionality and it provides
 * a pointer to the currentUser.
 *
 * @author MikeAshi
 */
public class LoginController {
    private static LoginController instance;

    UserRepository mUserRepository;
    User currentUser = null;

    private LoginController(Context context) {
        mUserRepository = new UserRepository(context);
    }

    private LoginController(AppDatabase db) {
        mUserRepository = new UserRepository(db);
    }

    public static LoginController getInstance(Context context) {
        if (instance == null) {
            instance = new LoginController(context);
        }
        return instance;
    }

    public static LoginController getInstance(AppDatabase db) {
        if (instance == null) {
            instance = new LoginController(db);
        }
        return instance;
    }

    /**
     * @return current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * login a user
     *
     * @param name     username
     * @param password
     * @return true if the given credentials are correct
     */
    public boolean login(String name, String password) {
        Optional<User> dbUser = mUserRepository.getByName(name);
        if (dbUser.isPresent()) {
            if (dbUser.get().getPassword().equals(Encryptor.encrypt(password, dbUser.get().getId()))) {
                currentUser = dbUser.get();
                return true;
            }
        }
        return false;
    }

    /**
     * logout the current user
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * check if the current user is admin
     *
     * @return true if current user is admin
     */
    public boolean isAdmin() {
        return currentUser.isAdmin();
    }
}
