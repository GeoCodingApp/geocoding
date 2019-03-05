package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.join;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;

/**
 * json to EventUserPuzzleListJoin.
 *
 * @author MikeAshi
 */
public class JoinDeserializer extends StdDeserializer<EventUserPuzzleListJoin> {

    public JoinDeserializer() {
        this(null);
    }

    protected JoinDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EventUserPuzzleListJoin deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String id = node.get("id").asText();
        String eventId = node.get("event_id").asText();
        String puzzleListId = node.get("puzzle_list_id").asText();
        String userId = node.get("user_id").asText();
        String solvedPuzzlesEncoded = node.get("solved_puzzles").asText();
        String solvedPuzzles = new String(Base64.decode(solvedPuzzlesEncoded, Base64.NO_WRAP));
        return new EventUserPuzzleListJoin(id, eventId, puzzleListId, userId, solvedPuzzles);
    }
}
