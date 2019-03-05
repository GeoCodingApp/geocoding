package de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.UUID;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerViewElement;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Answer Entity class,
 * this class is used by room to create Answer table
 * to access or modify saved Answers entities please use
 * the {@link de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.AnswerRepository}
 * class or your modifications will not be persisted
 *
 * @author Stefan Scheffel, Mike Ashi
 */
@SuppressWarnings(RoomWarnings.MISSING_INDEX_ON_FOREIGN_KEY_CHILD)
@Entity(tableName = "answers",
        /*
         * the foreignKeys represent a one to many relationship
         * between the Puzzles table and the Answers table
         * every Puzzle can have one or many Answers.
         *
         */
        foreignKeys = @ForeignKey(entity = Puzzle.class,
                parentColumns = "id",
                childColumns = "puzzleId",
                onDelete = CASCADE))
public class Answer {

    @NonNull
    @PrimaryKey
    private String id;
    private ArrayList<AnswerViewElement> elements;
    private String puzzleId;


    public Answer(String id, ArrayList<AnswerViewElement> elements, String puzzleId) {
        this.id = id;
        this.elements = elements;
        this.puzzleId = puzzleId;
    }

    /**
     * Create new Answer instance.
     *
     * @param elements answer's elements
     * @param puzzleId the id of the puzzle, which this is it's answer
     */
    @Ignore
    public Answer(ArrayList<AnswerViewElement> elements, String puzzleId) {
        this.id = UUID.randomUUID().toString();
        this.elements = elements;
        this.puzzleId = puzzleId;
    }

    /**
     * Create new Answer instance.
     */
    @Ignore
    public Answer() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public ArrayList<AnswerViewElement> getElements() {
        return elements;
    }

    public void setElements(ArrayList<AnswerViewElement> elements) {
        this.elements = elements;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(String puzzleId) {
        this.puzzleId = puzzleId;
    }
}
