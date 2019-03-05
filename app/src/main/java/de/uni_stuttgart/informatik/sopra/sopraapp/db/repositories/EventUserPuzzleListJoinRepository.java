package de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs.EventUserPuzzleListJoinDAO;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;

/**
 * Acts as a data holder to store all EventUserPuzzleListJoins.
 *
 * @author MikeAshi
 */
@SuppressWarnings("unused")
public class EventUserPuzzleListJoinRepository {
    private EventUserPuzzleListJoinDAO mEventUserPuzzleListJoinDAO;
    private ObjectMapper objectMapper = new ObjectMapper();
    private Context mContext;
    private AppDatabase mDb;
    private PuzzleRepository mPuzzleRepository;
    private PuzzleListRepository mPuzzleListRepository;
    private EventRepository mEventRepository;
    private UserRepository mUserRepository;

    /**
     * Create new EventUserPuzzleListJoinRepository instance.
     *
     * @param context application context
     */
    public EventUserPuzzleListJoinRepository(Context context) {
        mEventUserPuzzleListJoinDAO = AppDatabase.getInstance(context).EventUserPuzzleListJoinDAO();
        mPuzzleRepository = new PuzzleRepository(context);
        mEventRepository = new EventRepository(context);
        mUserRepository = new UserRepository(context);
        mPuzzleListRepository = new PuzzleListRepository(context);
        mContext = context;
    }

    /**
     * Create new EventUserPuzzleListJoinRepository instance.
     *
     * @param db AppDatabase
     */
    public EventUserPuzzleListJoinRepository(AppDatabase db) {
        mEventUserPuzzleListJoinDAO = db.EventUserPuzzleListJoinDAO();
        mPuzzleRepository = new PuzzleRepository(db);
        mEventRepository = new EventRepository(db);
        mUserRepository = new UserRepository(db);
        mPuzzleListRepository = new PuzzleListRepository(db);
        mDb = db;
    }

    /**
     * Gets a EventUserPuzzleListJoin from the database using its id.
     *
     * @param id EventUserPuzzleListJoin's id
     * @return EventUserPuzzleListJoin with given id
     */
    public Optional<EventUserPuzzleListJoin> getById(String id) {
        return mEventUserPuzzleListJoinDAO.getById(id);
    }

    /**
     * Gets all EventUserPuzzleListJoin from the table .
     *
     * @return all EventUserPuzzleListJoins.
     */
    public List<EventUserPuzzleListJoin> getAll() {
        return mEventUserPuzzleListJoinDAO.getAll();
    }

    /**
     * Gets a EventUserPuzzleListJoin from the table using event id.
     *
     * @param eventId event's id
     * @return EventUserPuzzleListJoin for a given event
     */
    public List<EventUserPuzzleListJoin> getByEvent(String eventId) {
        return mEventUserPuzzleListJoinDAO.getByEvent(eventId);
    }

    public List<EventUserPuzzleListJoin> getByEvent(Event event) {
        return mEventUserPuzzleListJoinDAO.getByEvent(event.getId());
    }

    /**
     * Returns all events this user is participating
     *
     * @param user The user to search for
     * @return A List of events for this user
     */
    public List<Event> getEventsForUser(User user) {
        List<EventUserPuzzleListJoin> joins = mEventUserPuzzleListJoinDAO.getByUserId(user.getId());
        List<Event> events = joins.stream().map(x -> mEventRepository.getById(x.getEventId()).get()).collect(Collectors.toList());
        return events;
    }

    /**
     * Returns a list of all users that are currently participating in a given event.
     *
     * @param event The event to search
     * @return The users participating in the event
     */
    public List<User> getUsersForEvent(Event event) {
        List<EventUserPuzzleListJoin> joins = mEventUserPuzzleListJoinDAO.getByEvent(event.getId());
        List<User> users = joins.stream().map(x -> mUserRepository.getById(x.getUserId()).get()).collect(Collectors.toList());
        return users;
    }

    /**
     * Gets a EventUserPuzzleListJoin from the table using user id.
     *
     * @param userId user's id
     * @return EventUserPuzzleListJoin for a given user
     */
    public List<EventUserPuzzleListJoin> getByUserId(String userId) {
        return mEventUserPuzzleListJoinDAO.getByUserId(userId);
    }

    public List<EventUserPuzzleListJoin> getByUser(User user) {
        return mEventUserPuzzleListJoinDAO.getByUserId(user.getId());
    }

    /**
     * Returns all Puzzles for a given PuzzleList
     *
     * @param puzzleList the PuzzleList to to search for
     * @return The puzzles of the PuzzleList
     */
    public List<Puzzle> getPuzzlesForPuzzleList(PuzzleList puzzleList) {
        List<String> puzzlesIds = puzzleList.getPuzzlesIds();
        List<Puzzle> puzzles = puzzlesIds.stream().map(x -> mPuzzleRepository.getById(x).get()).collect(Collectors.toList());
        return puzzles;
    }

    /**
     * Inserts one or more EventUserPuzzleListJoin to the database.
     *
     * @param eventUserPuzzleListJoin EventUserPuzzleListJoin/s to be saved
     */
    public void add(EventUserPuzzleListJoin eventUserPuzzleListJoin) {
        mEventUserPuzzleListJoinDAO.add(eventUserPuzzleListJoin);
    }

    /**
     * Inserts one or more EventUserPuzzleListJoin to the database.
     *
     * @param eventUserPuzzleListJoin EventUserPuzzleListJoin/s to be saved
     */
    public void addAll(EventUserPuzzleListJoin... eventUserPuzzleListJoin) {
        mEventUserPuzzleListJoinDAO.add(eventUserPuzzleListJoin);
    }

    /**
     * Updates a given EventUserPuzzleListJoin.
     *
     * @param eventUserPuzzleListJoin EventUserPuzzleListJoin to be updated
     */
    public void update(EventUserPuzzleListJoin eventUserPuzzleListJoin) {
        mEventUserPuzzleListJoinDAO.update(eventUserPuzzleListJoin);
    }

    /**
     * Deletes a given EventUserPuzzleListJoin.
     *
     * @param eventUserPuzzleListJoin EventUserPuzzleListJoin to be deleted
     */
    public void delete(EventUserPuzzleListJoin eventUserPuzzleListJoin) {
        mEventUserPuzzleListJoinDAO.delete(eventUserPuzzleListJoin);
    }

    /**
     * Returns a list of all PuzzleLists for a given Event.
     *
     * @param eventId event id
     * @return list of all PuzzleLists for a given Event
     */
    public List<PuzzleList> getAllPuzzleListForEvent(String eventId) {
        return mEventUserPuzzleListJoinDAO.getAllPuzzleListForEvent(eventId);
    }

    public List<PuzzleList> getPuzzleListsForEvent(Event event) {
        return mEventUserPuzzleListJoinDAO.getAllPuzzleListForEvent(event.getId());
    }

    /**
     * Returns a list of all PuzzleLists for a given Event and user.
     *
     * @param eventId event id
     * @param userId  user id
     * @return ist of all PuzzleLists for a given Event and user
     */
    public Optional<PuzzleList> getPuzzleListForEventUser(String eventId, String userId) {
        return mEventUserPuzzleListJoinDAO.getPuzzleListForEventUser(eventId, userId);
    }

    public Optional<PuzzleList> getPuzzleListForEventUser(Event event, User user) {
        return mEventUserPuzzleListJoinDAO.getPuzzleListForEventUser(event.getId(), user.getId());
    }

    public EventUserPuzzleListJoin getJoinByEventUser(Event event, User user) {
        List<EventUserPuzzleListJoin> eventJoins = mEventUserPuzzleListJoinDAO.getByEvent(event.getId());
        List<EventUserPuzzleListJoin> eventUserJoins = eventJoins.stream().filter(x -> (x.getUserId().equals(user.getId()))).collect(Collectors.toList());
        return eventUserJoins.get(0);
    }

    /**
     * Return a list of all solved puzzles for a given EventUserPuzzleListJoin.
     *
     * @param eventUserPuzzleListJoin EventUserPuzzleListJoin
     * @return list of solved puzzles
     */
    public List<Puzzle> getSolvedPuzzles(EventUserPuzzleListJoin eventUserPuzzleListJoin) {
        List<Puzzle> puzzles = new ArrayList<>();

        if (mPuzzleRepository == null) {
            mPuzzleRepository = getPuzzleRepository();
        }

        try {
            List<String> puzzleIds;
            if (eventUserPuzzleListJoin.getSolvedPuzzles() != null) {
                puzzleIds = objectMapper.readValue(eventUserPuzzleListJoin.getSolvedPuzzles(), new TypeReference<List<String>>() {
                });
            } else {
                puzzleIds = new ArrayList<>();
            }
            for (String id : puzzleIds) {
                puzzles.add(mPuzzleRepository.getById(id).get());
            }
        } catch (IOException e) {
            // do something
        }
        return puzzles;
    }

    /**
     * add puzzle to solved puzzles for a given EventUserPuzzleListJoin.
     *
     * @param eventUserPuzzleListJoin EventUserPuzzleListJoin
     * @param puzzleId                puzzle id
     */
    public void addSolvedPuzzle(EventUserPuzzleListJoin eventUserPuzzleListJoin, String puzzleId) {
        try {
            List<String> puzzleIds;
            if (!eventUserPuzzleListJoin.getSolvedPuzzles().equals("[]")) {
                puzzleIds = objectMapper.readValue(eventUserPuzzleListJoin.getSolvedPuzzles(), new TypeReference<List<String>>() {
                });
            } else {
                puzzleIds = new ArrayList<>();
            }
            puzzleIds.add(puzzleId);
            eventUserPuzzleListJoin.setSolvedPuzzles(objectMapper.writeValueAsString(puzzleIds));
            update(eventUserPuzzleListJoin);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns PuzzleRepository.
     *
     * @return PuzzleRepository
     */
    private PuzzleRepository getPuzzleRepository() {

        if (mPuzzleRepository != null) {
            return mPuzzleRepository;
        }

        if (mContext != null) {
            mPuzzleRepository = new PuzzleRepository(mContext);
            return mPuzzleRepository;
        }

        mPuzzleRepository = new PuzzleRepository(mDb);
        return mPuzzleRepository;
    }
}
