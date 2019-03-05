package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.image;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;

/**
 * json to image.
 *
 * @author MikeAshi
 */
public class ImageDeserializer extends StdDeserializer<Image> {

    public ImageDeserializer() {
        this(null);
    }

    protected ImageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Image deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String id = node.get("id").asText();
        byte[] image = Base64.decode(node.get("image").asText(), Base64.NO_WRAP);
        return new Image(id, image);
    }
}
