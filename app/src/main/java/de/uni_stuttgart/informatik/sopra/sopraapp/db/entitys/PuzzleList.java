package de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.UUID;

/**
 * PuzzleList Entity class,
 * this class is used by room to create PuzzleList table
 * to access or modify saved PuzzleList entities please use
 * the {@link de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository}
 * class or your modifications will not be persisted
 *
 * @author Stefan Scheffel, Mike Ashi
 */

@SuppressWarnings(RoomWarnings.MISSING_INDEX_ON_FOREIGN_KEY_CHILD)

@Entity(tableName = "puzzlelists")

public class PuzzleList {

    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    //eventId SHOULD BE REMOVED FROM HERE
    private String eventId;
    private ArrayList<String> puzzlesIds;

    /**
     * Create new PuzzleList instance.
     *
     * @param name PuzzleList name
     */
    @Ignore
    public PuzzleList(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    /**
     * Create new PuzzleList instance.
     */
    @Ignore
    public PuzzleList() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Create new PuzzleList instance.
     *
     * @param name    name PuzzleList name
     * @param eventId event's id
     */
    @Ignore
    public PuzzleList(String name, String eventId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.eventId = eventId;
    }

    public PuzzleList(@NonNull String id, String name, String eventId, ArrayList<String> puzzlesIds) {
        this.id = id;
        this.name = name;
        this.eventId = eventId;
        this.puzzlesIds = puzzlesIds;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public ArrayList<String> getPuzzlesIds() {
        return puzzlesIds;
    }

    public void setPuzzlesIds(ArrayList<String> puzzlesIds) {
        this.puzzlesIds = puzzlesIds;
    }

}
