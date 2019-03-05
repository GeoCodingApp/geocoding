package de.uni_stuttgart.informatik.sopra.sopraapp.db.converters;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.answerelement.AnswerElementDeserializer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.answerelement.AnswerElementSerializer;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerViewElement;

/**
 * AnswerConverter used by room to store/restore ArrayList<AnswerViewElement> in the database.
 *
 * @author MikeAshi
 */
public class AnswerConverter {
    private static final String TAG = "AnswerConverter";

    @TypeConverter
    public static String toString(ArrayList<AnswerViewElement> elements) {
        Log.d(TAG, "toString: Converting Answer Elements to json");
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(AnswerViewElement.class, new AnswerElementSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(elements);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @TypeConverter
    public static ArrayList<AnswerViewElement> toList(String json) {
        Log.d(TAG, "toString: Converting json to Answer Elements");

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AnswerViewElement.class, new AnswerElementDeserializer());
        mapper.registerModule(module);
        try {
            ArrayList<AnswerViewElement> arrayList = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, AnswerViewElement.class));
            return arrayList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
