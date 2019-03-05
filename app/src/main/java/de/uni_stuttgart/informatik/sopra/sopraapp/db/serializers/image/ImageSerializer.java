package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.image;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;

/**
 * image to json serializer.
 *
 * @author MikeAshi
 */
public class ImageSerializer extends StdSerializer<Image> {

    public ImageSerializer() {
        this(null);
    }

    protected ImageSerializer(Class<Image> t) {
        super(t);
    }

    @Override
    public void serialize(Image image, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", image.getId());
        gen.writeStringField("image", Base64.encodeToString(image.getImage(), Base64.NO_WRAP));
        gen.writeEndObject();
    }
}
