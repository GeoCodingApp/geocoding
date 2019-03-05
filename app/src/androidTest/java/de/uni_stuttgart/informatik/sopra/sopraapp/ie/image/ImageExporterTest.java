package de.uni_stuttgart.informatik.sopra.sopraapp.ie.image;

import android.util.Base64;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author MikeAshi
 */
public class ImageExporterTest {
    private static final String TAG = "ImageExporterTest";
    List<Image> images = new ArrayList<>();
    String json;

    @Before
    public void setUp() throws Exception {
        images.add(new Image("96e638aa-7a88-4fbb-a5d2-5a381be28509", Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg==", Base64.DEFAULT)));
        json = "[{\"id\":\"96e638aa-7a88-4fbb-a5d2-5a381be28509\",\"image\":\"iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg==\"}]";
    }

    @Test
    public void toJson() {
        String imageAsJson = ImageExporter.toJson(images);
        Log.d(TAG, "toJson: " + imageAsJson);
        assertEquals(json, imageAsJson);
    }

    @Test
    public void exceptionTest() throws JsonProcessingException {
        ImageExporter imageExporter = new ImageExporter();
        ObjectMapper mapper = spy(new ObjectMapper());
        when(mapper.writeValueAsString(anyList())).thenThrow(new JsonProcessingException("Exception thrown") {
        });
        imageExporter.mapper = mapper;
        json = imageExporter.toJson(new ArrayList<Image>());
        assertEquals("", json);
    }
}