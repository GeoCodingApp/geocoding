package de.uni_stuttgart.informatik.sopra.sopraapp.ie.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;

import java.util.Date;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.answer.AnswerExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.event.EventExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.image.ImageExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.join.JoinExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzle.PuzzleExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzleList.PuzzleListExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.user.UserExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.util.Encryptor;

/**
 * Exporter Helper class contains toJson methods.
 *
 * @author MikeAshi
 */
public class ExporterHelper {
    private static ObjectMapper mapper = new ObjectMapper();

    public static ObjectNode getEventJson(Event event) {
        ObjectNode eventNode = mapper.createObjectNode();
        String eventJson = EventExporter.toJson(event);
        eventNode.putRawValue("event", new RawValue(eventJson));
        return eventNode;
    }

    public static ObjectNode getUsersJson(List<User> users) {
        ObjectNode usersNode = mapper.createObjectNode();
        String usersJson = UserExporter.toJson(users);
        usersNode.putRawValue("users", new RawValue(usersJson));
        return usersNode;
    }

    public static ObjectNode getJoinJson(List<EventUserPuzzleListJoin> joins) {
        ObjectNode joinsNode = mapper.createObjectNode();
        String joinsJson = JoinExporter.toJson(joins);
        joinsNode.putRawValue("joins", new RawValue(joinsJson));
        return joinsNode;
    }

    public static ObjectNode getPuzzleListsJson(List<PuzzleList> lists) {
        ObjectNode listsNode = mapper.createObjectNode();
        String puzzlesJson = PuzzleListExporter.toJson(lists);
        listsNode.putRawValue("lists", new RawValue(puzzlesJson));
        return listsNode;
    }

    public static ObjectNode getPuzzlesJson(List<Puzzle> puzzles) {
        ObjectNode puzzlesNode = mapper.createObjectNode();
        String puzzlesJson = PuzzleExporter.toJson(puzzles);
        puzzlesNode.putRawValue("puzzles", new RawValue(puzzlesJson));
        return puzzlesNode;
    }

    public static ObjectNode getAnswersJson(List<Answer> answers) {
        ObjectNode answersNode = mapper.createObjectNode();
        String puzzlesJson = AnswerExporter.toJson(answers);
        answersNode.putRawValue("answers", new RawValue(puzzlesJson));
        return answersNode;
    }

    public static ObjectNode getImagesJson(List<Image> images) {
        ObjectNode imagesNode = mapper.createObjectNode();
        String puzzlesJson = ImageExporter.toJson(images);
        imagesNode.putRawValue("images", new RawValue(puzzlesJson));
        return imagesNode;
    }

    public static ObjectNode encrypt(String json) {
        String date = new Date().toString();
        String encryptedDate = Encryptor.encrypt(json, date);
        String checkSum = Encryptor.encrypt(encryptedDate);
        ObjectNode encryptedJson = mapper.createObjectNode();
        encryptedJson.put("date", date);
        encryptedJson.put("data", encryptedDate);
        encryptedJson.put("checksum", checkSum);
        return encryptedJson;
    }


}
