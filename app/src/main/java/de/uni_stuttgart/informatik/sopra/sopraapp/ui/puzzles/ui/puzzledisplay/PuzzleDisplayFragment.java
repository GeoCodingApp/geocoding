package de.uni_stuttgart.informatik.sopra.sopraapp.ui.puzzles.ui.puzzledisplay;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;

/**
 * Displays the puzzles to the user
 *
 * @author Stefan
 */
public class PuzzleDisplayFragment extends Fragment {

    private static Puzzle puzzle;
    private TextView puzzleNameView;
    private RecyclerView puzzleView;
    private PuzzleDisplayAdapter adapter;
    private PuzzleDisplayFragmentInteractionListener mListener;
    private Button submitSolutionButton, skipButton;


    public static PuzzleDisplayFragment newInstance(Puzzle puzzle) {
        PuzzleDisplayFragment fragment = new PuzzleDisplayFragment();
        fragment.setPuzzle(puzzle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_puzzle_display, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        List<PuzzleViewElement> puzzleElements = puzzle.getElements();
        puzzleNameView = view.findViewById(R.id.puzzle_name);
        puzzleView = view.findViewById(R.id.puzzle_view);
        submitSolutionButton = view.findViewById(R.id.solve_button);

        submitSolutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.solutionButtonPressed();
            }
        });

        skipButton = view.findViewById(R.id.skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.skipButtonPressed();
            }
        });

        adapter = new PuzzleDisplayAdapter(view.getContext(), puzzleElements);
        puzzleView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        puzzleView.setLayoutManager(manager);

        displayPuzzle(puzzle);
    }

    private void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PuzzleDisplayFragmentInteractionListener) {
            mListener = (PuzzleDisplayFragmentInteractionListener) context;
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


    private void displayPuzzle(Puzzle puzzle) {
        List<PuzzleViewElement> puzzleElements = puzzle.getElements();
        String puzzleName = puzzle.getName();
        adapter.setElements(puzzleElements);
        adapter.notifyDataSetChanged();

        this.puzzleNameView.setText(puzzleName);
    }

    /**
     * Interaction Listener
     */
    public interface PuzzleDisplayFragmentInteractionListener {
        void solutionButtonPressed();

        void skipButtonPressed();
    }

}
