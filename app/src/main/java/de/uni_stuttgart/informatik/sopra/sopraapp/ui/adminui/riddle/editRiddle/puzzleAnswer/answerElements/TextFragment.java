package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.answerElements;

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
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerActivity;

/**
 * @author MikeAshi
 */
public class TextFragment extends DialogFragment {
    private static final String TAG = "TextFragment";

    private String mText;
    private EditText mEditText;
    private boolean mUpdate = false;
    private int pos;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_answer_text, container, false);

        (rootView.findViewById(R.id.button_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        (rootView.findViewById(R.id.button_answer_text_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                dismiss();
            }
        });
        mEditText = (EditText) rootView.findViewById(R.id.answer_text);
        mEditText.requestFocus();
        if (mText != null) {
            mEditText.setText(mText);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView.clearFocus();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void save() {
        if (mUpdate) {
            // mUpdate
            ((AnswerActivity) getActivity()).updateText(mEditText.getText().toString(), this.pos);
        } else {
            ((AnswerActivity) getActivity()).addText(mEditText.getText().toString());
        }
    }

    public void setText(String text) {
        this.mText = text;
    }

    public void setUpdate(boolean update) {
        this.mUpdate = update;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
