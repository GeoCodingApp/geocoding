package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzleList;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.PuzzleListConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;

/**
 * json to PuzzleList.
 *
 * @author MikeAshi
 */
public class PuzzleListDeserializer extends StdDeserializer<PuzzleList> {

    public PuzzleListDeserializer() {
        this(null);
    }

    protected PuzzleListDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PuzzleList deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String id = node.get("id").asText();
        String name = node.get("name").asText();
        String eventId = node.get("event_id").asText();
        String puzzlesIdsAsJson = node.get("puzzles_ids").asText();
        String decoded = new String(Base64.decode(puzzlesIdsAsJson, Base64.NO_WRAP));
        return new PuzzleList(id, name, eventId, PuzzleListConverter.toList(decoded));
    }
}
