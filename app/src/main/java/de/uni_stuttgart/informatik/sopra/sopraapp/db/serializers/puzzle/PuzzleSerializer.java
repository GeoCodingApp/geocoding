package de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.puzzle;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.PuzzleConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;

/**
 * Puzzle to json serializer.
 *
 * @author MikeAshi
 */
public class PuzzleSerializer extends StdSerializer<Puzzle> {

    public PuzzleSerializer() {
        this(null);
    }

    protected PuzzleSerializer(Class<Puzzle> t) {
        super(t);
    }

    @Override
    public void serialize(Puzzle puzzle, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", puzzle.getId());
        gen.writeStringField("name", puzzle.getName());
        gen.writeStringField("description", puzzle.getDescription());
        gen.writeStringField("elements", Base64.encodeToString(PuzzleConverter.toString(puzzle.getElements()).getBytes(), Base64.NO_WRAP));
        gen.writeEndObject();
    }
}
