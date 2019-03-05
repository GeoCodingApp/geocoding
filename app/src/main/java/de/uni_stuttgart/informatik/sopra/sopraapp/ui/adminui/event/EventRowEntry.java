package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListEntry;

/**
 * Represents data for a Event List Entry
 *
 * @author Dominik Dec
 */
public class EventRowEntry extends AdaptiveListEntry {

    private String eventname;

    /**
     * Constructor
     *
     * @param name the name of the event
     */
    public EventRowEntry(String name) {
        this.eventname = name;
    }

    /**
     * getter name
     *
     * @return the name of the event
     */
    public String getName() {
        return eventname;
    }

}
