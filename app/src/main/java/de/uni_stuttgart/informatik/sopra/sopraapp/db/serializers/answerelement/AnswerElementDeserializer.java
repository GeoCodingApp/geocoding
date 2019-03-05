package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.answerelement;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerViewElement;

/**
 * json to AnswerElement Deserializer is used by room type converter.
 *
 * @author MikeAshi
 */
public class AnswerElementDeserializer extends StdDeserializer<AnswerViewElement> {

    public AnswerElementDeserializer() {
        this(null);
    }

    protected AnswerElementDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AnswerViewElement deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        AnswerViewElement answer = new AnswerViewElement();
        String jType = node.get("type").asText();
        if (jType.equals("TEXT")) {
            answer.setType(AnswerViewElement.Type.TEXT);
            answer.setText(node.get("text").asText());
        } else if (jType.equals("LOCATION")) {
            answer.setType(AnswerViewElement.Type.LOCATION);
            String[] location = node.get("location").asText().split("\\|");
            answer.setLatitude(location[0]);
            answer.setLongitude(location[1]);
        } else {
            answer.setType(AnswerViewElement.Type.QR);
            answer.setQR(node.get("qr").asText());
        }
        return answer;
    }
}
