package de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

import de.uni_stuttgart.informatik.sopra.sopraapp.util.Encryptor;


/**
 * basic user Entity class,
 * this class is used by room to create users table
 * to access or modify saved users entities please use
 * the {@link de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository}
 * class or your modifications will not be persisted
 *
 * @author MikeAshi
 */
@Entity(tableName = "users")
public class User {
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String password;
    private boolean isAdmin;

    /**
     * Create new user instance.
     *
     * @param name     user name
     * @param password password
     * @param isAdmin  true if user has admin access
     */
    @Ignore
    public User(String name, String password, boolean isAdmin) {
        this.id = UUID.randomUUID().toString();
        this.name = name.toLowerCase();
        this.password = Encryptor.encrypt(password, this.id);
        this.isAdmin = isAdmin;
    }

    public User(String id, String name, String password, boolean isAdmin) {
        this.id = id;
        this.name = name.toLowerCase();
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = Encryptor.encrypt(password, this.id);
    }

    public String getDecryptedPassword() {
        return Encryptor.decrypt(this.password, this.id);
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }
}
