package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist.editRiddleList.Fragments;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist.editRiddleList.EditRiddleListActivity;

/**
 * @author MikeAshi
 */
public class AddRiddleFragment extends DialogFragment {
    private static final String TAG = "AddRiddleFragment";

    private List<Puzzle> currentPuzzles;
    private PuzzleRepository mPuzzleRepository;
    private List<Puzzle> puzzles;
    private List<Puzzle> addedPuzzles = new ArrayList<>();
    private RecyclerView view;
    private AddRiddleListFragmentAdapter adapter;
    private EditRiddleListActivity activity;
    private View rootView;
    private CardView errorTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_riddles, container, false);

        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        (rootView.findViewById(R.id.fragment_edit_riddle_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                dismiss();
            }
        });
        errorTextView = rootView.findViewById(R.id.no_riddle_error_view);
        mPuzzleRepository = new PuzzleRepository(rootView.getContext());
        puzzles = mPuzzleRepository.getAll();
        ArrayList<Puzzle> arrayList = new ArrayList<>(puzzles);
        if (currentPuzzles != null) arrayList.removeAll(currentPuzzles);
        view = rootView.findViewById(R.id.add_riddles_view);
        adapter = new AddRiddleListFragmentAdapter(this, arrayList);
        if (arrayList.size() == 0) {
            errorTextView.setVisibility(View.VISIBLE);
        }
        view.setAdapter(adapter);
        LinearLayoutManager manger = new LinearLayoutManager(rootView.getContext());
        view.setLayoutManager(manger);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setCurrentPuzzles(List<Puzzle> currentPuzzles) {
        this.currentPuzzles = currentPuzzles;
    }

    public void add(String id) {
        Puzzle puzzle = mPuzzleRepository.getById(id).get();
        if (!addedPuzzles.contains(puzzle)) addedPuzzles.add(puzzle);
    }

    public void remove(String id) {
        addedPuzzles.remove(mPuzzleRepository.getById(id).get());
    }

    public void setActivity(EditRiddleListActivity activity) {
        this.activity = activity;
    }

    private void save() {
        Toast.makeText(rootView.getContext(), R.string.saved, Toast.LENGTH_LONG).show();
        activity.addRiddles(addedPuzzles);
    }
}
