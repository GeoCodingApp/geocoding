package de.uni_stuttgart.informatik.sopra.sopraapp.ie;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.AnswerRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.ImagesRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.json.ExporterHelper;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

/**
 * Export class exports all the users , puzzles , answers,
 * images, lists and the users progress of a given event
 * as encrypted file.
 *
 * @author MikeAshi
 */
public class Exporter {
    private static final String TAG = "Exporter";


    private Event mEvent;
    private List<EventUserPuzzleListJoin> joins = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<PuzzleList> puzzleLists = new ArrayList<>();
    private List<Puzzle> puzzles = new ArrayList<>();
    private List<Answer> answers = new ArrayList<>();
    private List<Image> images = new ArrayList<>();
    private Context mContext;
    private ObjectMapper mapper = new ObjectMapper();
    private UserRepository mUserRepository;
    private EventUserPuzzleListJoinRepository mEUPLJoinRepo;
    private PuzzleRepository mPuzzleRepository;
    private PuzzleListRepository mPuzzleListRepository;
    private AnswerRepository mAnswerRepository;
    private ImagesRepository mImagesRepository;

    /**
     * Create an initialize the exporter object.
     *
     * @param context context.
     * @param event   event to be exported.
     */
    public Exporter(Context context, Event event) {
        mContext = context;
        mEvent = event;
        mEUPLJoinRepo = new EventUserPuzzleListJoinRepository(mContext);
        mUserRepository = new UserRepository(mContext);
        mPuzzleListRepository = new PuzzleListRepository(mContext);
        mPuzzleRepository = new PuzzleRepository(mContext);
        mAnswerRepository = new AnswerRepository(mContext);
        mImagesRepository = new ImagesRepository(mContext);
        getEventUsersAndPuzzleLists();
        getEventPuzzles();
        getEventAnswersAndImages();
    }

    private void getEventUsersAndPuzzleLists() {
        joins = mEUPLJoinRepo.getByEvent(mEvent.getId());
        for (EventUserPuzzleListJoin join : joins) {
            Optional<User> user = mUserRepository.getById(join.getUserId());
            if (user.isPresent()) users.add(user.get());
            Optional<PuzzleList> puzzleList = mPuzzleListRepository.getById(join.getPuzzleListId());
            if (puzzleList.isPresent()) puzzleLists.add(puzzleList.get());
        }
    }

    private void getEventPuzzles() {
        for (PuzzleList list : puzzleLists) {
            for (String id : list.getPuzzlesIds()) {
                Optional<Puzzle> puzzle = mPuzzleRepository.getById(id);
                if (puzzle.isPresent()) puzzles.add(puzzle.get());
            }
        }
    }

    private void getEventAnswersAndImages() {
        for (Puzzle puzzle : puzzles) {
            Optional<Answer> answer = mAnswerRepository.getPuzzleSolution(puzzle.getId());
            if (answer.isPresent()) answers.add(answer.get());
            for (PuzzleViewElement element : puzzle.getElements()) {
                if (element.getType() == PuzzleViewElement.Type.IMG) {
                    images.add(mImagesRepository.getById(element.getImgId()));
                }
            }
        }
    }

    /**
     * Returns a File containing the encrypted data.
     *
     * @return a file containing the encrypted data.
     * @throws JsonProcessingException
     */
    public File export() throws JsonProcessingException {
        return writeToFile(getEncryptedJson());
    }

    private ObjectNode getEncryptedJson() throws JsonProcessingException {
        return ExporterHelper.encrypt(getJson());
    }

    protected String getJson() throws JsonProcessingException {
        ObjectNode json = mapper.createObjectNode();
        // write event
        json.setAll(ExporterHelper.getEventJson(mEvent));
        // write users
        json.setAll(ExporterHelper.getUsersJson(users));
        // write joins
        json.setAll(ExporterHelper.getJoinJson(joins));
        // write lists
        json.setAll(ExporterHelper.getPuzzleListsJson(puzzleLists));
        // write puzzles
        json.setAll(ExporterHelper.getPuzzlesJson(puzzles));
        // write Answers
        json.setAll(ExporterHelper.getAnswersJson(answers));
        // write Images
        json.setAll(ExporterHelper.getImagesJson(images));
        return mapper.writeValueAsString(json);
    }

    private File writeToFile(ObjectNode json) {
        File file = new File(mContext.getFilesDir(), "exported.zip");
        Log.d(TAG, "writeToFile: " + file.getAbsolutePath());
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(file, json);
        } catch (IOException e) {
            Log.d(TAG, "writeToFile: " + e.getMessage());
        }
        return file;
    }
}
