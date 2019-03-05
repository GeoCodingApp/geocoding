package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.answer;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.AnswerConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerViewElement;

/**
 * json to Answer.
 *
 * @author MikeAshi
 */
public class AnswerDeserializer extends StdDeserializer<Answer> {

    public AnswerDeserializer() {
        this(null);
    }

    protected AnswerDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Answer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String id = node.get("id").asText();
        String puzzleId = node.get("puzzle_id").asText();
        String elementsAsJson = node.get("elements").asText();
        String decoded = new String(Base64.decode(elementsAsJson, Base64.NO_WRAP));
        ArrayList<AnswerViewElement> elements = AnswerConverter.toList(decoded);
        return new Answer(id, elements, puzzleId);
    }
}
