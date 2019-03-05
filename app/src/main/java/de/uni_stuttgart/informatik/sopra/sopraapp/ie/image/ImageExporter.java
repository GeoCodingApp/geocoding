package de.uni_stuttgart.informatik.sopra.sopraapp.ie.image;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.image.ImageSerializer;

/**
 * ImageExporter uses the ImageSerializer to serialize a List of Images returning it as json.
 *
 * @author MikeAshi
 */
public class ImageExporter {
    private static final String TAG = "AnswerExporter";
    protected static ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns a list of images as json.
     *
     * @param images join to be exported
     * @return json representation of the given images
     */
    public static String toJson(List<Image> images) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Image.class, new ImageSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(images);
        } catch (JsonProcessingException e) {
            Log.d(TAG, "toJson: " + e.getMessage());
        }
        return "";
    }
}
