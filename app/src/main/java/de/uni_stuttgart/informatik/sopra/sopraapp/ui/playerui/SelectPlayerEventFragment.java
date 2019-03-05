package de.uni_stuttgart.informatik.sopra.sopraapp.ui.playerui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

/**
 * @author Dominik Dec
 */
public class SelectPlayerEventFragment extends Fragment {

    private RecyclerView recyclerView;
    private SelectPlayerEventInteractionListener mListener;
    private PlayersEventsListAdapter.PlayerEventsListAdapterInteractionListener mAdapterListener;

    public SelectPlayerEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectPlayerEventFragment.
     */
    public static SelectPlayerEventFragment newInstance() {
        SelectPlayerEventFragment fragment = new SelectPlayerEventFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_player_event, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.view);
        recyclerView.setAdapter(new PlayersEventsListAdapter(getContext(), mAdapterListener));
        LinearLayoutManager manger = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manger);
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectPlayerEventInteractionListener && context instanceof PlayersEventsListAdapter.PlayerEventsListAdapterInteractionListener) {
            mListener = (SelectPlayerEventInteractionListener) context;
            mAdapterListener = (PlayersEventsListAdapter.PlayerEventsListAdapterInteractionListener) context;
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
    public interface SelectPlayerEventInteractionListener {
        //void interaction();
    }
}
