package de.uni_stuttgart.informatik.sopra.sopraapp.db.converters;

import android.arch.persistence.room.TypeConverter;

import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;

/**
 * EventStatusConverter used by room to store/restore Event.Status in the database.
 *
 * @author MikeAshi
 */
public class EventStatusConverter {
    private static final String TAG = "EventStatusConverter";

    @TypeConverter
    public static int toInt(Event.Status status) {
        switch (status) {
            case WARM_UP:
                return 2;
            case STOPPED:
                return -1;
            case STARTED:
                return 0;
            case PAUSED:
                return 1;
            default:
                return 2;
        }
    }

    @TypeConverter
    public static Event.Status toEnum(int status) {
        switch (status) {
            case 2:
                return Event.Status.WARM_UP;
            case -1:
                return Event.Status.STOPPED;
            case 0:
                return Event.Status.STARTED;
            case 1:
                return Event.Status.PAUSED;
            default:
                return Event.Status.WARM_UP;
        }
    }
}
