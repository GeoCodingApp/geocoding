package de.uni_stuttgart.informatik.sopra.sopraapp.db.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;

/**
 * Data Access Object for the EventUserPuzzleListJoinDAO table.
 *
 * @author Mike Ashi
 */
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
@Dao
public interface EventUserPuzzleListJoinDAO {
    /**
     * Gets a EventUserPuzzleListJoin from the table using its id.
     *
     * @param id EventUserPuzzleListJoin's id
     * @return EventUserPuzzleListJoin with given id
     */
    @Query("select * from event_user_puzzlelist_join where id=:id")
    Optional<EventUserPuzzleListJoin> getById(String id);

    /**
     * Gets a EventUserPuzzleListJoin from the table using event id.
     *
     * @param eventId event's id
     * @return EventUserPuzzleListJoin for a given event
     */
    @Query("select * from event_user_puzzlelist_join where eventId=:eventId")
    List<EventUserPuzzleListJoin> getByEvent(String eventId);

    /**
     * Gets a EventUserPuzzleListJoin from the table using user id.
     *
     * @param userId users's id
     * @return EventUserPuzzleListJoin for a given event
     */
    @Query("select * from event_user_puzzlelist_join where userId=:userId")
    List<EventUserPuzzleListJoin> getByUserId(String userId);

    /**
     * Gets all EventUserPuzzleListJoin from the table .
     *
     * @return all EventUserPuzzleListJoins.
     */
    @Query("select * from event_user_puzzlelist_join")
    List<EventUserPuzzleListJoin> getAll();

    /**
     * Inserts one or more EventUserPuzzleListJoin to the table.
     *
     * @param eventUserPuzzleListJoin EventUserPuzzleListJoin/s to be saved
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(EventUserPuzzleListJoin... eventUserPuzzleListJoin);

    /**
     * Updates a given EventUserPuzzleListJoin.
     *
     * @param eventUserPuzzleListJoin EventUserPuzzleListJoin to be updated
     */
    @Update
    void update(EventUserPuzzleListJoin eventUserPuzzleListJoin);

    /**
     * Deletes a given EventUserPuzzleListJoin.
     *
     * @param eventUserPuzzleListJoin EventUserPuzzleListJoin to be deleted
     */
    @Delete
    void delete(EventUserPuzzleListJoin eventUserPuzzleListJoin);

    /**
     * Returns a list of all PuzzleLists for a given Event.
     *
     * @param eventId event id
     * @return list of all PuzzleLists for a given Event
     */
    @Query("select * from puzzlelists inner join event_user_puzzlelist_join on puzzlelists.id=event_user_puzzlelist_join.puzzleListId where event_user_puzzlelist_join.eventId=:eventId")
    List<PuzzleList> getAllPuzzleListForEvent(String eventId);

    /**
     * Returns a list of all PuzzleLists for a given Event and user.
     *
     * @param eventId event id
     * @param userId  user id
     * @return ist of all PuzzleLists for a given Event and user
     */
    @Query("select * from puzzlelists inner join event_user_puzzlelist_join on puzzlelists.id=event_user_puzzlelist_join.puzzleListId where event_user_puzzlelist_join.eventId=:eventId and event_user_puzzlelist_join.userId=:userId")
    Optional<PuzzleList> getPuzzleListForEventUser(String eventId, String userId);
}
