package de.uni_stuttgart.informatik.sopra.sopraapp.ie;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.AnswerRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.ImagesRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.answer.AnswerExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.answer.AnswerImporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.event.EventImporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.image.ImageExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.image.ImageImporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.join.JoinExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.join.JoinImporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzle.PuzzleExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzle.PuzzleImporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzleList.PuzzleListExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.puzzleList.PuzzleListImporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.user.UserExporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.user.UserImporter;

import static de.uni_stuttgart.informatik.sopra.sopraapp.ie.json.ImporterHelper.checkJson;
import static de.uni_stuttgart.informatik.sopra.sopraapp.ie.json.ImporterHelper.decryptJson;
import static de.uni_stuttgart.informatik.sopra.sopraapp.ie.json.ImporterHelper.getJsonFromFile;

public class Importer {
    private static final String TAG = "Importer";
    private ObjectMapper mapper = new ObjectMapper();
    private Context mContext;
    private JsonNode decryptedJson;
    private UserRepository mUserRepository;
    private ImagesRepository mImagesRepository;
    private PuzzleRepository mPuzzleRepository;
    private AnswerRepository mAnswerRepository;
    private EventRepository meEventRepository;
    private PuzzleListRepository mPuzzleListRepository;
    private EventUserPuzzleListJoinRepository mEventUserPuzzleListJoinRepository;
    private InputStream file;

    public Importer(Context context) {
        mContext = context;
        mImagesRepository = new ImagesRepository(context);
        mUserRepository = new UserRepository(context);
        mPuzzleRepository = new PuzzleRepository(context);
        mAnswerRepository = new AnswerRepository(context);
        meEventRepository = new EventRepository(context);
        mPuzzleListRepository = new PuzzleListRepository(context);
        mEventUserPuzzleListJoinRepository = new EventUserPuzzleListJoinRepository(context);

        logBefore();
    }

    private void logAfter() {
        Log.d(TAG, "logAfter: users" + UserExporter.toJson(mUserRepository.getAll()));
        Log.d(TAG, "logAfter: puzzles" + PuzzleExporter.toJson(mPuzzleRepository.getAll()));
        Log.d(TAG, "logAfter: lists" + PuzzleListExporter.toJson(mPuzzleListRepository.getAll()));
        Log.d(TAG, "logAfter: joins" + JoinExporter.toJson(mEventUserPuzzleListJoinRepository.getAll()));
        Log.d(TAG, "logAfter: Answer" + AnswerExporter.toJson(mAnswerRepository.getAll()));
        Log.d(TAG, "logAfter: images" + ImageExporter.toJson(mImagesRepository.getAll()));
        List<Event> events = meEventRepository.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Event e : events) {
            stringBuilder.append("{");
            stringBuilder.append("id:" + e.getId());
            stringBuilder.append("name:" + e.getName());
            stringBuilder.append("}");
        }
        Log.d(TAG, "logAfter: Events " + stringBuilder.toString());
    }

    private void logBefore() {
        Log.d(TAG, "logBefore: users" + UserExporter.toJson(mUserRepository.getAll()));
        Log.d(TAG, "logBefore: puzzles" + PuzzleExporter.toJson(mPuzzleRepository.getAll()));
        Log.d(TAG, "logBefore: lists" + PuzzleListExporter.toJson(mPuzzleListRepository.getAll()));
        Log.d(TAG, "logBefore: joins" + JoinExporter.toJson(mEventUserPuzzleListJoinRepository.getAll()));
        Log.d(TAG, "logBefore: Answer" + AnswerExporter.toJson(mAnswerRepository.getAll()));
        Log.d(TAG, "logBefore: images" + ImageExporter.toJson(mImagesRepository.getAll()));
        List<Event> events = meEventRepository.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Event e : events) {
            stringBuilder.append("{");
            stringBuilder.append("id:" + e.getId());
            stringBuilder.append("name:" + e.getName());
            stringBuilder.append("}");
        }
        Log.d(TAG, "logBefore: Events " + stringBuilder.toString());
    }

    public void doImport() throws StreamCorruptedException {
        JsonNode encryptedJson = getJsonFromFile(file);
        if (checkJson(encryptedJson)) {
            try {
                decryptedJson = decryptJson(encryptedJson);
            } catch (IOException e) {
                throw new StreamCorruptedException("corrupted file");
            }
        } else {
            throw new StreamCorruptedException("corrupted file");
        }
        importUsers();
        importImages();
        importPuzzles();
        importAnswers();
        importEvent();
        importPuzzleLists();
        importJoins();
        logAfter();
    }

    private void importJoins() {
        String joinsAsJson = decryptedJson.get("joins").toString();
        List<EventUserPuzzleListJoin> joins = JoinImporter.toList(joinsAsJson);
        for (EventUserPuzzleListJoin join : joins) {
            mEventUserPuzzleListJoinRepository.add(join);
        }
    }

    private void importPuzzleLists() {
        List<PuzzleList> alreadyExistingLists = mPuzzleListRepository.getAll();
        String listsAsJson = decryptedJson.get("lists").toString();
        List<PuzzleList> lists = PuzzleListImporter.toList(listsAsJson);
        for (PuzzleList list : lists) {
            for (PuzzleList alreadyExistingList : alreadyExistingLists) {
                if (alreadyExistingList.getName().equals(list.getName())) {
                    mPuzzleListRepository.delete(alreadyExistingList);
                }
            }
            mPuzzleListRepository.add(list);
        }
    }

    private void importEvent() {
        List<Event> alreadyExistingEvents = meEventRepository.getAll();
        String eventAsJson = decryptedJson.get("event").toString();
        Event event = EventImporter.toObject(eventAsJson);
        for (Event alreadyExistingEvent : alreadyExistingEvents) {
            if (alreadyExistingEvent.getName().equals(event.getName())) {
                meEventRepository.delete(alreadyExistingEvent);
            }
        }
        meEventRepository.add(event);
    }

    private void importAnswers() {
        String answersAsJson = decryptedJson.get("answers").toString();
        List<Answer> answers = AnswerImporter.toList(answersAsJson);
        for (Answer answer : answers) {
            mAnswerRepository.add(answer);
        }
    }

    private void importPuzzles() {
        List<Puzzle> alreadyExistingPuzzles = mPuzzleRepository.getAll();
        String puzzlesListAsJson = decryptedJson.get("puzzles").toString();
        List<Puzzle> puzzles = PuzzleImporter.toList(puzzlesListAsJson);
        for (Puzzle puzzle : puzzles) {
            for (Puzzle alreadyExistingPuzzle : alreadyExistingPuzzles) {
                if (alreadyExistingPuzzle.getName().equals(puzzle.getName())) {
                    mPuzzleRepository.delete(alreadyExistingPuzzle);
                }
            }
            mPuzzleRepository.add(puzzle);
        }
    }

    private void importImages() {
        String imagesListAsJson = decryptedJson.get("images").toString();
        List<Image> images = ImageImporter.toList(imagesListAsJson);
        for (Image img : images) {
            mImagesRepository.add(img);
        }
    }

    private void importUsers() {
        List<User> alreadyExistingUsers = mUserRepository.getAll();
        String userListAsJson = decryptedJson.get("users").toString();
        List<User> userList = UserImporter.toList(userListAsJson);
        for (User user : userList) {
            for (User alreadyExistingUser : alreadyExistingUsers) {
                if (alreadyExistingUser.getName().equals(user.getName())) {
                    mUserRepository.delete(alreadyExistingUser);
                }
            }
            mUserRepository.add(user);
        }
    }

    public void setFile(InputStream file) {
        this.file = file;
    }
}
