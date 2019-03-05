package de.uni_stuttgart.informatik.sopra.sopraapp.ui.puzzles.ui.puzzledisplay;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectSolutionTypeFragment extends DialogFragment {

    private SelectSolutionTypeFragmentInteractionListener mListener;

    private SolutionType[] solutionTypes;

    public SelectSolutionTypeFragment() {
        // Required empty public constructor
    }

    public static SelectSolutionTypeFragment newInstance(SolutionType[] types) {

        SelectSolutionTypeFragment fragment = new SelectSolutionTypeFragment();
        fragment.setSolutionTypes(types);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        String[] solutionNames = Arrays.stream(solutionTypes).map(SolutionType::toString).toArray(String[]::new);
        builder.setTitle(R.string.select_solution_type_text);
        builder.setItems(solutionNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.solutionTypeSelected(solutionTypes[which]);
                dialog.dismiss();
            }
        });
        return builder.create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_solution_type, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectSolutionTypeFragmentInteractionListener) {
            mListener = (SelectSolutionTypeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void setSolutionTypes(SolutionType[] types) {
        this.solutionTypes = types;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interaction Listener
     */
    public interface SelectSolutionTypeFragmentInteractionListener {
        void solutionTypeSelected(SolutionType type);
    }

}
