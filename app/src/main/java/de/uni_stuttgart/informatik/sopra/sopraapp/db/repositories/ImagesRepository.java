package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.content.Context;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.ImageDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;

/**
 * Acts as a data holder to store all Images.
 *
 * @author MikeAshi
 */
public class ImagesRepository {
    private ImageDao mImageDao;

    /**
     * Create new ImagesRepository instance.
     *
     * @param context application context
     */
    public ImagesRepository(Context context) {
        mImageDao = AppDatabase.getInstance(context).ImageDao();
    }

    /**
     * Create new ImagesRepository instance.
     *
     * @param db AppDatabase
     */
    public ImagesRepository(AppDatabase db) {
        mImageDao = db.ImageDao();
    }

    /**
     * Gets all images from the table .
     *
     * @return a list of all images
     */
    public List<Image> getAll() {
        return mImageDao.getAll();
    }


    /**
     * Gets an image from the table using its id.
     *
     * @param id image's id
     * @return the image with the given id
     */
    public Image getById(String id) {
        return mImageDao.getById(id);
    }

    /**
     * Deletes a given image.
     *
     * @param image hint to be deleted
     */
    public void delete(Image image) {
        mImageDao.delete(image);
    }

    /**
     * Inserts one or more image to the table.
     *
     * @param image image/s to be saved
     */
    public void add(Image... image) {
        mImageDao.add(image);
    }

}
