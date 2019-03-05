package de.uni_stuttgart.informatik.sopra.sopraapp.ie.answer;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.serializers.answer.AnswerSerializer;

/**
 * AnswerExporter uses the AnswerSerializer to serialize a List of Answers returning it as json.
 *
 * @author MikeAshi
 */
public class AnswerExporter {
    private static final String TAG = "AnswerExporter";
    protected static ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns a list of answers as json.
     *
     * @param answers join to be exported
     * @return json representation of the given answers
     */
    public static String toJson(List<Answer> answers) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Answer.class, new AnswerSerializer());
        mapper.registerModule(module);
        try {
            return mapper.writeValueAsString(answers);
        } catch (JsonProcessingException e) {
            Log.d(TAG, "toJson: " + e.getMessage());
        }
        return "";
    }
}
