package de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;

/**
 * Data Access Object for the Image table.
 *
 * @author Mike Ashi
 */
@Dao
public interface ImageDao {
    /**
     * Gets all images from the table .
     *
     * @return a list of all images
     */
    @Query("select * from images")
    List<Image> getAll();

    /**
     * Gets an image from the table using its id.
     *
     * @param id image's id
     * @return the image with the given id
     */
    @Query("select * from images where id=:id")
    Image getById(String id);

    /**
     * Inserts one or more image to the table.
     *
     * @param image image/s to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Image... image);

    /**
     * Deletes a given image.
     *
     * @param image hint to be deleted
     */
    @Delete
    void delete(Image image);
}
