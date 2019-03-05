package de.uni_stuttgart.informatik.sopra.sopraapp.ui.playerui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

/**
 * @author Dominik Dec
 */
public class PlayerWaitingRoomFragment extends Fragment {

    private PlayerWaitingRoomFragmentInteractionListener mListener;
    private Button joinbutton;
    private TextView messagetext;
    private boolean eventHasStarted;

    public PlayerWaitingRoomFragment() {
        // Required empty public constructor
    }

    public static PlayerWaitingRoomFragment newInstance(boolean eventHasStarted) {
        PlayerWaitingRoomFragment fragment = new PlayerWaitingRoomFragment();
        fragment.setEventStarted(eventHasStarted);
        return fragment;
    }

    private void setEventStarted(boolean started) {
        eventHasStarted = started;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //joinRepository = new EventUserPuzzleListJoinRepository(getContext());
        //eventRepository = new EventRepository(getContext());
        messagetext = view.findViewById(R.id.text_message);
        joinbutton = view.findViewById(R.id.join_button);

        messagetext.setText(getString(R.string.playerwait_message_not_started));
        joinbutton.setEnabled(false);
        //check if event started
        //eventRepository.getById(eventid).get().getStatus() == Event.Status.STARTED
        if (eventHasStarted) {
            joinbutton.setEnabled(true);
            messagetext.setText(getString(R.string.playerwait_message_started));
        }


        joinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.playerEntersGame();

                //change to puzzle display
//                Fragment newFragment = PuzzleDisplayFragment.newInstance(eventid);
//                FragmentTransaction transaction = ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, newFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_waiting_room, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayerWaitingRoomFragmentInteractionListener) {
            mListener = (PlayerWaitingRoomFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interaction Listener
     */
    public interface PlayerWaitingRoomFragmentInteractionListener {
        void playerEntersGame();
    }

}
