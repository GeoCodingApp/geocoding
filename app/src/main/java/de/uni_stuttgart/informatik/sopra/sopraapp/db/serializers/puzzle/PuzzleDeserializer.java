package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzle;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.PuzzleConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

/**
 * json to Puzzle.
 *
 * @author MikeAshi
 */
public class PuzzleDeserializer extends StdDeserializer<Puzzle> {

    public PuzzleDeserializer() {
        this(null);
    }

    protected PuzzleDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Puzzle deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String id = node.get("id").asText();
        String name = node.get("name").asText();
        String description = node.get("description").asText();
        String elementsAsJson = node.get("elements").asText();
        String decoded = new String(Base64.decode(elementsAsJson, Base64.NO_WRAP));
        List<PuzzleViewElement> elements = PuzzleConverter.toList(decoded);
        return new Puzzle(id, name, description, elements);
    }
}
