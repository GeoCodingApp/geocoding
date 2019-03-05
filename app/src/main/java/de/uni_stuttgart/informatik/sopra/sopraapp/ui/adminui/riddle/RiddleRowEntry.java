package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListEntry;

/**
 * Represents data for a Riddle List Entry
 *
 * @author Dominik Dec
 */
public class RiddleRowEntry extends AdaptiveListEntry {
    private String name;
    private String id;

    /**
     * Constructor
     *
     * @param name the name of the Group
     */
    public RiddleRowEntry(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * getter name
     *
     * @return the name of the Group
     */
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}