package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.group;


import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListEntry;

/**
 * Represents data for a Group List Entry
 *
 * @author Dominik Dec
 */
public class GroupRowEntry extends AdaptiveListEntry {
    private String name;
    private String pw;
    private String id;

    /**
     * Constructor
     *
     * @param user
     */
    public GroupRowEntry(User user) {
        this.name = user.getName();
        this.pw = user.getDecryptedPassword();
        this.id = user.getId();
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
    public String getPw() {
        return pw;
    }

    /**
     * getter id
     *
     * @return the password for the Group
     */
    public String getId() {
        return id;
    }
}