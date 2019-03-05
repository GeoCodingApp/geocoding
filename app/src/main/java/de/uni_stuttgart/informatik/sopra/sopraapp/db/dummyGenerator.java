package de.uni_stuttgart.informatik.sopra.sopraapp.db;

import android.util.Base64;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.AnswerDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.EventDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.PuzzleDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.PuzzleListDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.UserDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.AnswerConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.PuzzleConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.ImagesRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.formatters.CodeFormatter;
import de.uni_stuttgart.informatik.sopra.sopraapp.formatters.CodeFormatters;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

/**
 * this class generates dummy data.
 *
 * @author MikeAshi
 */
public class dummyGenerator {
    AppDatabase mAppDatabase;
    UserDao mUserDao;
    PuzzleDao mPuzzleDao;
    PuzzleListDao mPuzzleListDao;
    EventDao mEventDao;
    AnswerDao mAnswerDao;
    EventUserPuzzleListJoinRepository mEventUserPuzzleListJoinRepository;
    ImagesRepository mImagesRepository;

    public dummyGenerator(AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
        mUserDao = appDatabase.UserDao();
        mPuzzleDao = appDatabase.PuzzleDao();
        mPuzzleListDao = appDatabase.PuzzleListDao();
        mEventDao = appDatabase.EventDao();
        mAnswerDao = appDatabase.AnswerDao();
        mEventUserPuzzleListJoinRepository = new EventUserPuzzleListJoinRepository(appDatabase);
        mImagesRepository = new ImagesRepository(appDatabase);
        userGenerator();
        //puzzleGenerator();
        //puzzleListGenerator();
        //eventGenerator();
        joinGenerator();
    }

    private void joinGenerator() {
        // users
        List<User> users = mUserDao.getAll();
        // Events
        Event[] events = {
                new Event("StuttgartCampusEvent"),
                new Event("MünchenStadtEvent")
        };
        events[0].setStatus(Event.Status.STARTED);

        if (mEventDao.getAll().size() != events.length) {
            mEventDao.add(events);
        }

        Image image = new Image(Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg==", Base64.DEFAULT));
        mImagesRepository.add(image);

        PuzzleViewElement CODE_ELEMENT = new PuzzleViewElement(PuzzleViewElement.Type.CODE, "public class Main { public static void main(String[] args) { System.out.println(\"Hello World\"); } }");
        PuzzleViewElement PICTURE_ELEMENT = new PuzzleViewElement(PuzzleViewElement.Type.TEXT, "This is just a dummy text to use for testing purposes.");

        CodeFormatter formatter = CodeFormatters.JAVA_FORMATTER;
        String formattedCode = formatter.format(CODE_ELEMENT.getCode());
        CODE_ELEMENT.setText(formattedCode);
        Puzzle PUZZLE = new Puzzle("Dummy Puzzle", "This puzzle is used for testing purposes only.", Arrays.asList(CODE_ELEMENT, PICTURE_ELEMENT));

        //riddles
        Puzzle[] puzzles = {
                PUZZLE,
                new Puzzle("Bit-Schubsen", "", PuzzleConverter.toList("[{\"type\":\"TEXT\",\"text\":\"In dieser Aufgabe sollen die Bits ein wenig hin und her geschubst werden, um an die Koordinaten zu gelangen\"}]")),
                new Puzzle("HashMap", "", PuzzleConverter.toList("[{\"type\":\"TEXT\",\"text\":\"Der Behälter, dessen kompletter Inhalt den längsten, sinnvollen Begriff darstellt, ist die Lösung und der Startpunkt der nächsten Aufgabe.\"}]"))
        };


        if (mPuzzleDao.getAll().size() != puzzles.length) {
            mPuzzleDao.add(puzzles);
        }

        // Answers
        Answer[] answers = {
                new Answer(AnswerConverter.toList("[{\"type\":\"LOCATION\",\"location\":\"99.999999|9.999999\"},{\"type\":\"TEXT\",\"text\":\"theanswer\"},{\"type\":\"QR\",\"qr\":\"First Puzzle QR Answer\"}]"), puzzles[0].getId()),//LOC
                new Answer(AnswerConverter.toList("[{\"type\":\"QR\",\"qr\":\"Second Puzzle QR Answer\"}]"), puzzles[1].getId())
        };

        mAnswerDao.add(answers);

        //Lists

        PuzzleList[] lists = {
                new PuzzleList("List_Stuttgart_A"), new PuzzleList("List_Stuttgart_B")
        };

        if (mPuzzleListDao.getAll().size() != lists.length) {
            mPuzzleListDao.add(lists);
        }

        // join puzzles to lists
        ArrayList<String> puzzleIds = new ArrayList<>();
        puzzleIds.add(puzzles[0].getId());
        puzzleIds.add(puzzles[1].getId());

        lists[0].setPuzzlesIds(puzzleIds);
        lists[1].setPuzzlesIds(puzzleIds);
        mPuzzleListDao.update(lists);
        // add the first list to the event and assign it to the first user
        EventUserPuzzleListJoin join1 = new EventUserPuzzleListJoin(events[0].getId(), lists[0].getId(), users.get(0).getId());
        mEventUserPuzzleListJoinRepository.add(join1);
        // solve one puzzle
        //mEventUserPuzzleListJoinRepository.addSolvedPuzzle(join1, puzzles[0].getId());
        // add the second list to the event and assign it to the second user
        EventUserPuzzleListJoin join2 = new EventUserPuzzleListJoin(events[0].getId(), lists[1].getId(), users.get(1).getId());
        mEventUserPuzzleListJoinRepository.add(join2);
        // solve two puzzle
        //mEventUserPuzzleListJoinRepository.addSolvedPuzzle(join2, puzzles[0].getId());
        //mEventUserPuzzleListJoinRepository.addSolvedPuzzle(join2, puzzles[1].getId());

    }

    private void userGenerator() {
        User[] users = {
                new User("Gruppe1", "d7h8ic", false),
                new User("Gruppe2", "x9fbyf", false),
                new User("Gruppe3", "wni56k", false),
                new User("Gruppe4", "dwk3d9", false),
                new User("Gruppe5", "xpm77y", false),
                new User("Gruppe6", "2gxlxj", false),
                new User("Gruppe7", "zjqp1y", false),
                new User("admin", "pass", true),
                new User("mike", "12345", true),
                new User("stefan", "12345", true),
                new User("dominik", "12345", true),
        };

        if (mUserDao.getAll().size() != users.length) {
            mUserDao.add(users);
        }
    }
}
