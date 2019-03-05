package de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

/**
 * Data Access Object for the users table.
 *
 * @author MikeAshi
 */
@Dao
public interface UserDao {
    /**
     * Gets all users from the table.
     *
     * @return List of all users in the table
     */
    @Query("select * from users")
    List<User> getAll();

    /**
     * Gets a user from the table using his id.
     *
     * @param id user's id
     * @return the user with the given id
     */
    @Query("select * from users where id = :id")
    Optional<User> getById(String id);

    /**
     * Gets a user from the table using his name.
     *
     * @param name user's name
     * @return the user with the given name
     */
    @Query("select * from users where name = :name")
    Optional<User> getByName(String name);

    /**
     * Inserts a user in the database. If the user already exists, replace it.
     * Always check if the user already exist
     * before adding new user.
     *
     * @param user the user to be inserted.
     */
    @Insert
    void add(User user);

    /**
     * Inserts user array in the database. if a user already exists, he will be replaced
     *
     * @param user users array
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(User... user);

    /**
     * Updates a given user.
     *
     * @param user user to be updated
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    /**
     * Returns a list of all admins in the table.
     *
     * @return list of all admins.
     */
    @Query("select * from users where isAdmin = 1")
    List<User> getAdmins();

    /**
     * Deletes a given user.
     *
     * @param user user to be deleted
     */
    @Delete
    void delete(User user);

    /**
     * Deletes a user by his id.
     *
     * @param id user's id
     */
    @Query("delete from users where id = :id")
    void deleteById(String id);

    /**
     * Deletes all users in the table.
     */
    @Query("DELETE FROM users")
    void deleteAll();
}
