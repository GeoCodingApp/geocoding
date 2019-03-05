package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzleList;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.PuzzleListConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;

/**
 * PuzzleList to json serializer.
 *
 * @author MikeAshi
 */
public class PuzzleListSerializer extends StdSerializer<PuzzleList> {

    public PuzzleListSerializer() {
        this(null);
    }

    protected PuzzleListSerializer(Class<PuzzleList> t) {
        super(t);
    }

    @Override
    public void serialize(PuzzleList list, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", list.getId());
        gen.writeStringField("name", list.getName());
        gen.writeStringField("event_id", list.getEventId());
        gen.writeStringField("puzzles_ids", Base64.encodeToString(PuzzleListConverter.toString(list.getPuzzlesIds()).getBytes(), Base64.NO_WRAP));
        gen.writeEndObject();
    }
}
