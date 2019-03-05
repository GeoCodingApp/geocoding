package de.uni_stuttgart.informatik.sopra.sopraapp.ie.image;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.image.ImageDeserializer;

/**
 * ImageImporter uses the ImageDeserializer to deserialize a json string to Images list.
 *
 * @author MikeAshi
 */
public class ImageImporter {
    private static final String TAG = "AnswerImporter";

    /**
     * Returns a List of images from json
     *
     * @param json json
     * @return a List of images
     */
    public static List<Image> toList(String json) {
        List<Image> images = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Image.class, new ImageDeserializer());
        mapper.registerModule(module);
        try {
            images = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Image.class));
        } catch (IOException e) {
            Log.d(TAG, "toList: " + e.getMessage());
        }
        return images;
    }
}
