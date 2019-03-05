package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event;

import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListEntry;

/**
 * @author Dominik Dec
 */
public class EventGroupEntry extends AdaptiveListEntry {

    private String groupname;
    private String listname;

    public EventGroupEntry(String groupname, String listname) {
        this.groupname = groupname;
        this.listname = listname;
    }

    public String getListname() {
        return listname;
    }

    public String getGroupname() {
        return groupname;
    }
}
