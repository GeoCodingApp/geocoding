package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListEntry;

/**
 * Represents data for a Riddle-List List Entry
 *
 * @author Dominik Dec
 */
public class RiddleListRowEntry extends AdaptiveListEntry {
    private String name;

    /**
     * Constructor
     *
     * @param name the name of the Group
     */
    public RiddleListRowEntry(String name) {
        this.name = name;
    }

    /**
     * getter name
     *
     * @return the name of the Group
     */
    public String getName() {
        return name;
    }

}