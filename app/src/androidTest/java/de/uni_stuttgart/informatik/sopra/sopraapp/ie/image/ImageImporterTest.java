package de.uni_stuttgart.informatik.sopra.sopraapp.ie.image;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author MikeAshi
 */
public class ImageImporterTest {
    List<Image> images = new ArrayList<>();
    String json;

    @Before
    public void setUp() throws Exception {
        json = "[{\"id\":\"96e638aa-7a88-4fbb-a5d2-5a381be28509\",\"image\":\"iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg==\"}]";
    }

    @Test
    public void toList() {
        images = ImageImporter.toList(json);
        assertEquals(1, images.size());
        assertEquals("96e638aa-7a88-4fbb-a5d2-5a381be28509", images.get(0).getId());
    }

    @Test
    public void exceptionTest() {
        ImageImporter imageImporter = new ImageImporter();
        images = imageImporter.toList("not a valid json");
        assertTrue(images.isEmpty());
    }
}