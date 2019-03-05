package de.uni_stuttgart.informatik.sopra.sopraapp.ie;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author MikeAshi
 */
public class ImporterTest {

    private AppDatabase db;
    private UserRepository userRepository;
    private String json;
    private File file;
    private InputStream inputStream;
    private Importer importer;
    private ObjectMapper mapper = new ObjectMapper();
    private EventRepository eventRepository;
    private PuzzleRepository puzzleRepository;
    private PuzzleListRepository puzzleListRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userRepository = new UserRepository(context);
        initFile(context);
        initStream();
        importer = new Importer(context);
        importer.setFile(inputStream);
        eventRepository = new EventRepository(context);
        puzzleRepository = new PuzzleRepository(context);
        puzzleListRepository = new PuzzleListRepository(context);
    }

    @After
    public void end() {
        AppDatabase.reset();
    }


    @Test
    public void importTest() throws JsonProcessingException, StreamCorruptedException {
        importer.doImport();
        // test event
        Optional<Event> dbEvent = eventRepository.getById("51e7ef6d-e1e3-4173-b699-5be9226c4155");
        if (!dbEvent.isPresent()) fail("Error getting the imported event");
        assertEquals("StuttgartCampusEvent", dbEvent.get().getName());
        // test users
        Optional<User> dbUser1 = userRepository.getById("6ff7815a-3f56-4cf5-be6b-08c8601072a7");
        if (!dbUser1.isPresent()) fail("Error getting the imported user");
        assertEquals("gruppe1", dbUser1.get().getName());
        Optional<User> dbUser2 = userRepository.getById("d989f1d1-0a0f-4d61-ae0b-bd2b2092745d");
        if (!dbUser2.isPresent()) fail("Error getting the imported user");
        assertEquals("gruppe2", dbUser2.get().getName());
        // test puzzles
        Optional<Puzzle> dbPuzzle = puzzleRepository.getById("7cc5face-6b65-4524-9d1d-a389a7b6072c");
        if (!dbPuzzle.isPresent()) fail("Error getting the imported puzzle");
        assertEquals("Bit-Schubsen", dbPuzzle.get().getName());
        // test puzzleList
        Optional<PuzzleList> dbList = puzzleListRepository.getById("465e0b1e-a570-488e-beb2-b283addc4877");
        if (!dbList.isPresent()) fail("Error getting the imported lists");
        assertEquals("List_Stuttgart_A", dbList.get().getName());
    }

    private void initFile(Context context) {
        ObjectNode json = mapper.createObjectNode();
        json.put("date", "Sun Jan 13 00:53:31 GMT 2019");
        json.put("data", "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");
        json.put("checksum", "891f28babc2c2a9dc58bb10ac5a097fa");
        file = new File(context.getFilesDir(), "test.zip");
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(file, json);
        } catch (IOException e) {
            fail();
        }
    }

    private void initStream() {
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            fail();
        }
    }
}