package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.join;


import android.util.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;

/**
 * EventUserPuzzleListJoin to json serializer.
 *
 * @author MikeAshi
 */
public class JoinSerializer extends StdSerializer<EventUserPuzzleListJoin> {

    public JoinSerializer() {
        this(null);
    }

    protected JoinSerializer(Class<EventUserPuzzleListJoin> t) {
        super(t);
    }


    @Override
    public void serialize(EventUserPuzzleListJoin join, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", join.getId());
        gen.writeStringField("event_id", join.getEventId());
        gen.writeStringField("puzzle_list_id", join.getPuzzleListId());
        gen.writeStringField("user_id", join.getUserId());
        String puzzles = join.getSolvedPuzzles();
        gen.writeStringField("solved_puzzles", Base64.encodeToString(puzzles.getBytes(), Base64.NO_WRAP));
        gen.writeEndObject();
    }
}
