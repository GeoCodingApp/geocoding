package de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

/**
 * Puzzle Entity class,
 * this class is used by room to create puzzle table
 * to access or modify saved puzzles entities please use
 * the {@link de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository}
 * class or your modifications will not be persisted
 *
 * @author Stefan Scheffel, Mike Ashi
 */
@Entity(tableName = "puzzles")
public class Puzzle {
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String description;
    private List<PuzzleViewElement> elements;

    /**
     * Create new puzzle instance.
     *
     * @param name        puzzle name
     * @param description puzzle description
     * @param elements    puzzle elements
     */
    @Ignore
    public Puzzle(String name, String description, List<PuzzleViewElement> elements) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.elements = elements;
    }

    /**
     * Create new puzzle instance.
     */
    @Ignore
    public Puzzle() {
        this.id = UUID.randomUUID().toString();
    }

    public Puzzle(@NonNull String id, String name, String description, List<PuzzleViewElement> elements) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.elements = elements;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PuzzleViewElement> getElements() {
        return elements;
    }

    public void setElements(List<PuzzleViewElement> elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Puzzle) obj).getId().equals(this.getId());
    }
}
