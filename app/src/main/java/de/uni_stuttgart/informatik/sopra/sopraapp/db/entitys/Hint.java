package de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;
import android.support.annotation.NonNull;

import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Hint Entity class,
 * this class is used by room to create Hint table
 * to access or modify saved Hint entities please use
 * the {@link de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.HintsRepository}
 * class or your modifications will not be persisted
 *
 * @author Stefan Scheffel, Mike Ashi
 */
@SuppressWarnings(RoomWarnings.MISSING_INDEX_ON_FOREIGN_KEY_CHILD)
@Entity(tableName = "hints",
        /*
         * the foreignKeys represent a one to many relationship
         * between the Puzzles table and the Hints table
         * every Puzzle can have one or many Hints.
         *
         */
        foreignKeys = @ForeignKey(entity = Puzzle.class,
                parentColumns = "id",
                childColumns = "puzzleId",
                onDelete = CASCADE))
public class Hint {

    @NonNull
    @PrimaryKey
    private String id;
    private String text;
    private String puzzleId;

    public Hint(@NonNull String id, String text, String puzzleId) {
        this.id = id;
        this.text = text;
        this.puzzleId = puzzleId;
    }

    /**
     * Create new Hint instance.
     *
     * @param text     hint's text
     * @param puzzleId the id of the puzzle
     */
    @Ignore
    public Hint(String text, String puzzleId) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.puzzleId = puzzleId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(String puzzleId) {
        this.puzzleId = puzzleId;
    }
}
