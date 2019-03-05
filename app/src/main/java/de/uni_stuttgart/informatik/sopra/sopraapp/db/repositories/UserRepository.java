package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.content.Context;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.UserDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

/**
 * Acts as a data holder to store all Users.
 *
 * @author MikeAshi
 */
public class UserRepository {

    private UserDao mUserDao;

    /**
     * Create new UserRepository instance.
     *
     * @param context application context
     */
    public UserRepository(Context context) {
        mUserDao = AppDatabase.getInstance(context).UserDao();
    }

    /**
     * Create new UserRepository instance.
     *
     * @param db AppDatabase
     */
    public UserRepository(AppDatabase db) {
        mUserDao = db.UserDao();
    }

    /**
     * Gets all users.
     *
     * @return List of all users in the database
     */
    public List<User> getAll() {
        return mUserDao.getAll();
    }

    /**
     * Gets a user from the database using his id.
     *
     * @param id user's id
     * @return the user with the given id
     */
    public Optional<User> getById(String id) {
        return mUserDao.getById(id);
    }

    /**
     * Gets a user from the database using his name.
     *
     * @param name user's name
     * @return the user with the given name
     */
    public Optional<User> getByName(String name) {
        return mUserDao.getByName(name.toLowerCase());
    }

    /**
     * Inserts a user in the database.
     * If the user already exists, returns false.
     *
     * @param user
     * @return true if the user was inserted
     */
    public boolean add(User user) {
        if (getByName(user.getName()).isPresent() || getById(user.getId()).isPresent()) {
            return false;
        }
        mUserDao.add(user);
        return true;
    }

    public void addAll(User... user) {
        mUserDao.add(user);
    }

    /**
     * Updates a given user.
     *
     * @param user user to be updated
     */
    public void update(User user) {
        mUserDao.update(user);
    }

    /**
     * Returns a list of all admins in the database.
     *
     * @return list of all admins.
     */
    public List<User> getAdmins() {
        return mUserDao.getAdmins();
    }

    /**
     * Deletes a given user.
     *
     * @param user user to be deleted
     */
    public void delete(User user) {
        mUserDao.delete(user);
    }

    /**
     * Deletes a user by his id.
     *
     * @param id user's id
     */
    public void deleteById(String id) {
        mUserDao.deleteById(id);
    }

    /**
     * Deletes all users in the database.
     */
    public void deleteAll() {
        mUserDao.deleteAll();
    }
}
