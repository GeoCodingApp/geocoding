package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzleElement;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;


/**
 * PuzzleElement to json serializer is used by room type converter.
 *
 * @author MikeAshi
 */
public class PuzzleElementSerializer extends StdSerializer<PuzzleViewElement> {
    public PuzzleElementSerializer() {
        this(null);
        //
    }

    protected PuzzleElementSerializer(Class<PuzzleViewElement> t) {
        super(t);
    }

    @Override
    public void serialize(PuzzleViewElement value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeStartObject();
        jgen.writeStringField("type", value.getType().toString());
        switch (value.getType()) {
            case TEXT:
                jgen.writeStringField("text", value.getText());
                break;
            case IMG:
                jgen.writeStringField("imgId", value.getImgId());
                break;
            case CODE:
                jgen.writeStringField("code", value.getText());
                break;
        }
        jgen.writeEndObject();
    }
}
