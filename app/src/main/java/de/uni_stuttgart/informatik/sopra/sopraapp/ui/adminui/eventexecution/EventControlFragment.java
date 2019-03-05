package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.eventexecution;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;


/**
 * @author Dominik Dec
 */
public class EventControlFragment extends Fragment {
    private static final String TAG = "EventControlFragment";
    private String name;
    private TextView eventStatus;
    private Event event;
    private EventRepository eventRepository;
    private EventUserPuzzleListJoinRepository userPuzzleListJoinRepository;
    private Button primBtn;
    private Button secBtn;
    private ExeEventActivity activity;

    public EventControlFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventControlFragment.
     */
    public static EventControlFragment newInstance(String param1, String param2) {
        EventControlFragment fragment = new EventControlFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        eventRepository = new EventRepository(getContext());
        userPuzzleListJoinRepository = new EventUserPuzzleListJoinRepository(getContext());
        event = eventRepository.getByName(name).get();
    }

    private void setEventStatus() {
        String status = "";
        if (event.getStatus() == Event.Status.STARTED) {
            status = getString(R.string.eventcontrol_state_started);
        } else if (event.getStatus() == Event.Status.PAUSED) {
            status = getString(R.string.eventcontrol_state_paused);
        } else if (event.getStatus() == Event.Status.WARM_UP) {
            status = getString(R.string.eventcontrol_state_warmup);
        } else {
            status = getString(R.string.eventcontrol_state_stopped);
        }
        eventStatus.setText(status);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_control, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventStatus = getView().findViewById(R.id.event_status_text);
        primBtn = getView().findViewById(R.id.primary_event_btn);
        secBtn = getView().findViewById(R.id.secondary_event_btn);
        setEventStatus();

        primBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (event.getStatus()) {
                    case WARM_UP:
                        start();
                        break;
                    case STARTED:
                        pause();
                        break;
                    case PAUSED:
                        start();
                        break;
                    case STOPPED:
                        deleteProgress();
                        warmup();
                        break;

                    default:
                }
            }
        });
        secBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (event.getStatus()) {
                    case STARTED:
                        stop();
                        break;
                    case PAUSED:
                        stop();
                        break;

                    default:
                }
            }
        });

        setup();

    }

    private void deleteProgress() {
        Log.d(TAG, "deleteProgress: delete progress");
        List<EventUserPuzzleListJoin> joins = userPuzzleListJoinRepository.getByEvent(event.getId());
        for (EventUserPuzzleListJoin join : joins) {
            join.setSolvedPuzzles("[]");
            userPuzzleListJoinRepository.update(join);
        }
        activity.update();
    }

    private void setup() {
        Log.d("tag", "status " + event.getStatus());
        switch (event.getStatus()) {
            case WARM_UP:
                warmup();
                break;
            case STARTED:
                start();

                break;
            case PAUSED:
                pause();

                break;
            case STOPPED:
                stop();
                break;
        }
    }

    private void start() {
        event.setStatus(Event.Status.STARTED);

        primBtn.setVisibility(View.VISIBLE);
        secBtn.setVisibility(View.VISIBLE);
        primBtn.setText(R.string.eventcontrol_pausebtn);
        secBtn.setText(R.string.eventcontrol_stopbtn);

        eventRepository.update(event);
        setEventStatus();
    }

    private void pause() {
        event.setStatus(Event.Status.PAUSED);

        primBtn.setVisibility(View.VISIBLE);
        secBtn.setVisibility(View.VISIBLE);

        primBtn.setText(getString(R.string.eventcontrol_resumebtn));
        secBtn.setText(getString(R.string.eventcontrol_stopbtn));

        eventRepository.update(event);
        setEventStatus();
    }

    private void stop() {
        event.setStatus(Event.Status.STOPPED);

        primBtn.setVisibility(View.VISIBLE);
        secBtn.setVisibility(View.GONE);

        primBtn.setText(getString(R.string.eventcontrol_restartbtn));

        eventRepository.update(event);
        setEventStatus();
    }

    private void warmup() {
        event.setStatus(Event.Status.WARM_UP);
        secBtn.setVisibility(View.GONE);
        primBtn.setVisibility(View.VISIBLE);
        primBtn.setText(getString(R.string.eventcontrol_startbtn));

        eventRepository.update(event);
        setEventStatus();
    }

    public void setActivity(ExeEventActivity activity) {
        this.activity = activity;
    }
}
