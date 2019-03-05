package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Base64;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ImagesRepositoryTest {
    private static String ENC_IMAG = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg==";
    private AppDatabase db;
    private ImagesRepository mImagesRepository;
    private String IMG_ID = "a random image id for testing";
    private Image image;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mImagesRepository = new ImagesRepository(db);
        image = new Image(new byte[0]);
        mImagesRepository.add(image);
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void add() {
        int count = mImagesRepository.getAll().size();
        Image img = new Image(new byte[0]);
        img.setId(IMG_ID);
        image.setImage(Base64.decode(ENC_IMAG, Base64.DEFAULT));
        mImagesRepository.add(img);
        assertEquals(count + 1, mImagesRepository.getAll().size());
    }

    @Test
    public void delete() {
        int count = mImagesRepository.getAll().size();
        mImagesRepository.delete(image);
        assertNotEquals(count, mImagesRepository.getAll().size());
    }
}