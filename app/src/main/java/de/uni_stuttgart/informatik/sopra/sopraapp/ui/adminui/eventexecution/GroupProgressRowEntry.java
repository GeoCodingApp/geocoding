package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.eventexecution;


import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListEntry;

/**
 * Represents data for a Group List Entry
 *
 * @author Dominik Dec
 */
public class GroupProgressRowEntry extends AdaptiveListEntry {
    private String name;
    private int progress;

    /**
     * Constructor
     *
     * @param name     the name of the Group
     * @param progress the password for the Group
     */
    public GroupProgressRowEntry(String name, int progress) {
        this.name = name;
        this.progress = progress;
    }

    /**
     * getter name
     *
     * @return the name of the Group
     */
    public String getName() {
        return name;
    }

    /**
     * getter password
     *
     * @return the password for the Group
     */
    public int getProgress() {
        return progress;
    }
}