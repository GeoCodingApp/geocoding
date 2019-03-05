package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event.select;

/**
 * @author Dominik Dec
 */
public class GroupEntry {

    private String name;
    private String id;

    public GroupEntry(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
