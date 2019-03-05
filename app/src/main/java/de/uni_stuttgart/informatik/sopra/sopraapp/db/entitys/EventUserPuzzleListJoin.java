package de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.RoomWarnings;
import android.support.annotation.NonNull;

import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * EventUserPuzzleListJoin Entity class,
 * this class is used by room to create EventUserPuzzleListJoin table
 * which represent the many to many relationship (Event:PuzzleList:User)
 * to access or modify saved EventUserPuzzleListJoin entities please use
 * the {@link de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository}
 * class or your modifications will not be persisted
 *
 * @author Mike Ashi
 */
@SuppressWarnings(RoomWarnings.MISSING_INDEX_ON_FOREIGN_KEY_CHILD)

@Entity(tableName = "event_user_puzzlelist_join",
        primaryKeys = {"id"},
        /*
         * the foreignKeys represent a many to many relationship
         * (Event:PuzzleList:User)
         */
        foreignKeys = {
                @ForeignKey(entity = Event.class,
                        parentColumns = "id",
                        childColumns = "eventId", onDelete = CASCADE),
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId", onDelete = CASCADE),
                @ForeignKey(entity = PuzzleList.class,
                        parentColumns = "id",
                        childColumns = "puzzleListId", onDelete = CASCADE)
        })
public class EventUserPuzzleListJoin {
    @NonNull
    private String id;
    private String eventId;
    private String puzzleListId;
    private String userId;
    private String solvedPuzzles = "[]";

    /**
     * Create new EventUserPuzzleListJoin instance.
     *
     * @param eventId      event id
     * @param puzzleListId puzzleList id
     */
    @Ignore
    public EventUserPuzzleListJoin(String eventId, String puzzleListId) {
        this.eventId = eventId;
        this.puzzleListId = puzzleListId;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Create new EventUserPuzzleListJoin instance.
     *
     * @param eventId      event id
     * @param puzzleListId puzzleList id
     * @param userId       user id
     */
    @Ignore
    public EventUserPuzzleListJoin(String eventId, String puzzleListId, String userId) {
        this.eventId = eventId;
        this.puzzleListId = puzzleListId;
        this.userId = userId;
        this.id = UUID.randomUUID().toString();
    }

    public EventUserPuzzleListJoin(String id, String eventId, String puzzleListId, String userId, String solvedPuzzles) {
        this.id = id;
        this.eventId = eventId;
        this.puzzleListId = puzzleListId;
        this.userId = userId;
        this.solvedPuzzles = solvedPuzzles;
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }


    public String getPuzzleListId() {
        return puzzleListId;
    }

    public void setPuzzleListId(String puzzleListId) {
        this.puzzleListId = puzzleListId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSolvedPuzzles() {
        return solvedPuzzles;
    }

    public void setSolvedPuzzles(String solvedPuzzles) {
        this.solvedPuzzles = solvedPuzzles;
    }


}
