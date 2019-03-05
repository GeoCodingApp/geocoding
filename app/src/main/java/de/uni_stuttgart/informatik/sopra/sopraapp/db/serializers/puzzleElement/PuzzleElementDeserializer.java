package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzleElement;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

/**
 * json to PuzzleElement Deserializer is used by room type converter.
 *
 * @author MikeAshi
 */
public class PuzzleElementDeserializer extends StdDeserializer<PuzzleViewElement> {
    public PuzzleElementDeserializer() {
        this(null);
    }

    protected PuzzleElementDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PuzzleViewElement deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        PuzzleViewElement element = new PuzzleViewElement();
        String jType = node.get("type").asText();
        if (jType.equals("IMG")) {
            element.setType(PuzzleViewElement.Type.IMG);
            element.setImgId(node.get("imgId").asText());
        } else if (jType.equals("CODE")) {
            element.setType(PuzzleViewElement.Type.CODE);
            element.setText(node.get("code").asText());
        } else {
            element.setType(PuzzleViewElement.Type.TEXT);
            element.setText(node.get("text").asText());
        }
        return element;
    }
}
