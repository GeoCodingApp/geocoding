package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleElements;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.EditRiddleActivity;

/**
 * @author MikeAshi
 */
public class TextFragment extends DialogFragment {
    private static final String TAG = "TextFragment";

    private String mText;
    private EditText mEditText;
    private boolean update = false;
    private int pos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_puzzle_text, container, false);

        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        (rootView.findViewById(R.id.puzzle_text_button_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                dismiss();
            }
        });
        mEditText = (EditText) rootView.findViewById(R.id.puzzle_text);
        mEditText.requestFocus();
        if (mText != null) {
            mEditText.setText(mText);
        }
        return rootView;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void onResume() {
        super.onResume();
        // hide activity action bar
        ((EditRiddleActivity) getActivity()).hideActionBar(mEditText);


    }

    @Override
    public void onPause() {
        super.onPause();
        // show activity action bar
        ((EditRiddleActivity) getActivity()).showActionBar();

    }

    private void save() {
        if (update) {
            // update
            ((EditRiddleActivity) getActivity()).updateText(mEditText.getText().toString(), this.pos);
        } else {
            ((EditRiddleActivity) getActivity()).addText(mEditText.getText().toString());
        }
    }

    public void setText(String text) {
        this.mText = text;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
