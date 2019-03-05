package de.uni_stuttgart.informatik.sopra.sopraapp.ie;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;
import de.uni_stuttgart.informatik.sopra.sopraapp.util.Encryptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author MikeAshi
 */
public class ExporterTest {
    private static final String TAG = "ExporterTest";

    private Exporter exporter;
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private PuzzleRepository puzzleRepository;
    private PuzzleListRepository puzzleListRepository;
    private EventUserPuzzleListJoinRepository eventUserPuzzleListJoinRepository;
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        eventRepository = new EventRepository(context);
        userRepository = new UserRepository(context);
        puzzleRepository = new PuzzleRepository(context);
        puzzleListRepository = new PuzzleListRepository(context);
        eventUserPuzzleListJoinRepository = new EventUserPuzzleListJoinRepository(context);
        AppDatabase.reset();
        exporter = new Exporter(context, eventRepository.getAll().get(0));
    }

    @After
    public void tearDown() throws Exception {
        AppDatabase.reset();
    }

    @Test
    public void exportTest() throws JsonProcessingException {
        File file = exporter.export();
        Log.d(TAG, "exportTest: file path :  " + file.getAbsolutePath());
        try {
            JsonNode rootNode = new ObjectMapper().readTree(file);
            assertTrue(rootNode.has("date"));
            assertTrue(rootNode.has("data"));
            assertTrue(rootNode.has("checksum"));
            String data = rootNode.get("data").asText();
            String checksum = rootNode.get("checksum").asText();
            assertEquals(checksum, Encryptor.encrypt(data));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void exportEventTest() throws JsonProcessingException {
        initEvent();
        Exporter exporter = new Exporter(context, eventRepository.getById("d441cf9630834df8a6798eb51f2a1bbe").get());
        File file = exporter.export();
        Log.d(TAG, "exportTest: file path :  " + file.getAbsolutePath());
        try {
            JsonNode rootNode = new ObjectMapper().readTree(file);
            assertTrue(rootNode.has("date"));
            assertTrue(rootNode.has("data"));
            assertTrue(rootNode.has("checksum"));
            String data = rootNode.get("data").asText();
            String checksum = rootNode.get("checksum").asText();
            assertEquals(checksum, Encryptor.encrypt(data));
        } catch (IOException e) {
            fail();
        }
    }

    public void initEvent() {
        Event event = new Event("d441cf9630834df8a6798eb51f2a1bbe", "export test Event", Event.Status.PAUSED);
        eventRepository.add(event);
        //
        User user = new User("1c0a06d1dd516146df9358c2e77ebfc8", "export test user", "pass", false);
        userRepository.add(user);
        //
        Puzzle puzzle = new Puzzle("cfe0760d5f93c395832b9a44b792fea6", "export test Puzzle", "export test Puzzle description", new ArrayList<PuzzleViewElement>());
        puzzleRepository.add(puzzle);
        //
        ArrayList<String> puzzle_ids = new ArrayList<String>();
        puzzle_ids.add("cfe0760d5f93c395832b9a44b792fea6");
        PuzzleList puzzleList = new PuzzleList("e6e28334de5e21badf47e92fb88b6523", "export test PuzzleList", "d441cf9630834df8a6798eb51f2a1bbe", puzzle_ids);
        puzzleListRepository.add(puzzleList);
        //
        EventUserPuzzleListJoin join = new EventUserPuzzleListJoin("733fda40cd3680456351788e39fbee4d", "d441cf9630834df8a6798eb51f2a1bbe", "e6e28334de5e21badf47e92fb88b6523", "1c0a06d1dd516146df9358c2e77ebfc8", "[]");
        eventUserPuzzleListJoinRepository.add(join);
    }
}