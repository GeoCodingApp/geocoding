package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.answerelement;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerViewElement;

/**
 * AnswerElement to json serializer is used by room type converter.
 *
 * @author MikeAshi
 */
public class AnswerElementSerializer extends StdSerializer<AnswerViewElement> {

    public AnswerElementSerializer() {
        this(null);
    }

    protected AnswerElementSerializer(Class<AnswerViewElement> t) {
        super(t);
    }

    @Override
    public void serialize(AnswerViewElement value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeStartObject();
        jgen.writeStringField("type", value.getType().toString());
        switch (value.getType()) {
            case TEXT:
                jgen.writeStringField("text", value.getText());
                break;
            case LOCATION:
                jgen.writeStringField("location", value.getLatitude() + "|" + value.getLongitude());
                break;
            case QR:
                jgen.writeStringField("qr", value.getQR());
                break;
        }
        jgen.writeEndObject();
    }
}
