package de.uni_stuttgart.informatik.sopra.sopraapp.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.AnswerDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.EventDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.EventUserPuzzleListJoinDAO;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.HintDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.ImageDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.PuzzleDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.PuzzleListDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.UserDao;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.AnswerConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.EventStatusConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.PuzzleConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.PuzzleListConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Hint;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

import static de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase.VERSION;

/**
 * The Room database for this app.
 *
 * @author MikeAshi
 */
@Database(entities = {User.class, Answer.class,
        Event.class, Hint.class,
        Puzzle.class, PuzzleList.class,
        EventUserPuzzleListJoin.class,
        Image.class}, version = VERSION, exportSchema = false)
@TypeConverters({PuzzleConverter.class,
        AnswerConverter.class,
        EventStatusConverter.class,
        PuzzleListConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    static final int VERSION = 3;

    static final String NAME = "SoPra-App-Room";
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                mInstance = Room.databaseBuilder(context, AppDatabase.class, NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
                if (mInstance.UserDao().getAll().size() == 0) {
                    reset();
                }
            }
        }
        return mInstance;
    }

    public static void reset() {
        // clear All Tables
        mInstance.clearAllTables();
        // Generate dummy data
        dummyGenerator dG = new dummyGenerator(mInstance);

    }

    public abstract UserDao UserDao();

    public abstract AnswerDao AnswerDao();

    public abstract EventDao EventDao();

    public abstract HintDao HintDao();

    public abstract PuzzleDao PuzzleDao();

    public abstract PuzzleListDao PuzzleListDao();

    public abstract EventUserPuzzleListJoinDAO EventUserPuzzleListJoinDAO();

    public abstract ImageDao ImageDao();

}
