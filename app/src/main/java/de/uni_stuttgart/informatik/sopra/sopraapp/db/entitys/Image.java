package de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Image Entity class,
 * this class is used by room to create Images table
 * to access or modify saved Image entities please use
 * the {@link de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.ImagesRepository}
 * class or your modifications will not be persisted
 *
 * @author Mike Ashi
 */
@Entity(tableName = "images")
public class Image {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Image(@NonNull String id, byte[] image) {
        this.id = id;
        this.image = image;
    }

    /**
     * Create new Image instance.
     *
     * @param image bitmap as byte array
     */
    @Ignore
    public Image(byte[] image) {
        this.id = UUID.randomUUID().toString();
        this.image = image;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
