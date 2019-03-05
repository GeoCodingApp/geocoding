package de.uni_stuttgart.informatik.sopra.sopraapp.ie.answer;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.answer.AnswerDeserializer;

/**
 * AnswerImporter uses the AnswerDeserializer to deserialize a json string to Answer list.
 *
 * @author MikeAshi
 */
public class AnswerImporter {
    private static final String TAG = "AnswerImporter";

    /**
     * Returns a List of answers from json
     *
     * @param json json
     * @return a List of Puzzles
     */
    public static List<Answer> toList(String json) {
        List<Answer> answers = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Answer.class, new AnswerDeserializer());
        mapper.registerModule(module);
        try {
            answers = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Answer.class));
        } catch (IOException e) {
            Log.d(TAG, "toList: " + e.getMessage());
        }
        return answers;
    }
}
