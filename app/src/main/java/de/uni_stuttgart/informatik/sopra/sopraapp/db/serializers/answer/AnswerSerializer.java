package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.answer;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.AnswerConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;

/**
 * Answer to json serializer.
 *
 * @author MikeAshi
 */
public class AnswerSerializer extends StdSerializer<Answer> {

    public AnswerSerializer() {
        this(null);
    }

    protected AnswerSerializer(Class<Answer> t) {
        super(t);
    }

    @Override
    public void serialize(Answer answer, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", answer.getId());
        gen.writeStringField("puzzle_id", answer.getPuzzleId());
        gen.writeStringField("elements", Base64.encodeToString(AnswerConverter.toString(answer.getElements()).getBytes(), Base64.NO_WRAP));
        gen.writeEndObject();
    }
}
