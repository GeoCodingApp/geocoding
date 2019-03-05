package de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Event Entity class,
 * this class is used by room to create Event table
 * to access or modify saved Events entities please use
 * the {@link de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository}
 * class or your modifications will not be persisted
 *
 * @author Stefan Scheffel, Mike Ashi
 */
@Entity(tableName = "events")
public class Event {
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private Status status = Status.WARM_UP;

    /**
     * Create new Hint instance.
     *
     * @param id     event id
     * @param name   event name
     * @param status event status
     */
    public Event(@NonNull String id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    /**
     * Create new Hint instance.
     *
     * @param name event name
     */
    @Ignore
    public Event(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        STARTED, PAUSED, STOPPED, WARM_UP
    }
}
