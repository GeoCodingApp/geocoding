package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.AnswerConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerViewElement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class AnswerRepositoryTest {
    private AppDatabase db;
    private AnswerRepository answerRepository;
    private PuzzleRepository puzzleRepository;


    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        answerRepository = new AnswerRepository(db);
        puzzleRepository = new PuzzleRepository(db);
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }


    @Test
    public void add() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Answer answer = new Answer(
                AnswerConverter.toList("[{\"type\":\"TEXT\",\"text\":\"test\"},{\"type\":\"LOCATION\",\"location\":\"1|2\"}]")
                , puzzle.getId());
        answerRepository.add(answer);

        Optional<Answer> returnedAnswer = answerRepository.getById(answer.getId());

        assertEquals("test", returnedAnswer.get().getElements().get(0).getText());
        assertEquals("2", returnedAnswer.get().getElements().get(1).getLongitude());
        assertEquals("1", returnedAnswer.get().getElements().get(1).getLatitude());
    }

    @Test
    public void addAll() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Answer[] answers = {
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
        };
        answerRepository.addAll(answers);

        List<Answer> returnedAnswers = answerRepository.getAll();

        assertEquals(10, returnedAnswers.size());
    }

    @Test
    public void update() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Answer answer = new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId());
        answerRepository.add(answer);

        Optional<Answer> returnedAnswer = answerRepository.getById(answer.getId());

        Answer dbAnswer = returnedAnswer.get();
        ArrayList<AnswerViewElement> elements = dbAnswer.getElements();
        elements.add(new AnswerViewElement(AnswerViewElement.Type.TEXT, "updated text"));
        dbAnswer.setElements(elements);

        answerRepository.update(dbAnswer);
        returnedAnswer = answerRepository.getById(answer.getId());

        assertEquals("updated text", returnedAnswer.get().getElements().get(0).getText());
    }

    @Test
    public void delete() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Answer answer = new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId());
        answerRepository.add(answer);

        Optional<Answer> returnedAnswer = answerRepository.getById(answer.getId());

        if (!returnedAnswer.isPresent()) {
            fail("Answer not present");
        }

        answerRepository.delete(answer);
        returnedAnswer = answerRepository.getById(answer.getId());

        if (returnedAnswer.isPresent()) {
            fail("Answer was not deleted !!!");
        }

    }

    @Test
    public void getSolutions() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);

        Answer[] answers = {
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
                new Answer(new ArrayList<AnswerViewElement>(), puzzle.getId()),
        };
        answerRepository.addAll(answers);

        List<Answer> returnedAnswers = answerRepository.getSolutions();

        assertEquals(5, returnedAnswers.size());
    }

    @Test
    public void getPuzzleSolutions() {
        Puzzle puzzle = new Puzzle("puzzleName", "puzzleDescription", null);
        puzzleRepository.add(puzzle);
        ArrayList<AnswerViewElement> answerElements = new ArrayList<AnswerViewElement>();
        answerElements.add(new AnswerViewElement(AnswerViewElement.Type.TEXT, "test"));
        Answer answer = new Answer(answerElements, puzzle.getId());
        answerRepository.add(answer);
        Optional<Answer> returndAnswer = answerRepository.getPuzzleSolution(puzzle.getId());
        assertEquals("test", returndAnswer.get().getElements().get(0).getText());
    }
}