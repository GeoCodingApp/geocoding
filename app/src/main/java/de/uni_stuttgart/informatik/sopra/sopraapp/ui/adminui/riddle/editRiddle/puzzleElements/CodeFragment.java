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
import android.widget.Toast;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.formatters.CodeFormatters;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.EditRiddleActivity;

/**
 * @author MikeAshi
 */
public class CodeFragment extends DialogFragment {
    private EditText mEditText;
    private String code;
    private boolean update = false;
    private int pos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_puzzle_code, container, false);

        (rootView.findViewById(R.id.button_close)).setOnClickListener(v -> dismiss());
        (rootView.findViewById(R.id.code_button_save)).setOnClickListener(v -> {
            save();
            dismiss();
        });
        (rootView.findViewById(R.id.code_button_format)).setOnClickListener(v -> {
            formatCode();
        });
        mEditText = rootView.findViewById(R.id.puzzle_code);
        mEditText.requestFocus();
        if (code != null) {
            mEditText.setText(code);
        }
        return rootView;
    }

    private void formatCode() {
        String formattedCode = CodeFormatters.JAVA_FORMATTER.format(mEditText.getText().toString());
        //checks if format succeeded
        if (!formattedCode.equals("")) {
            mEditText.setText(formattedCode);
        } else {
            Toast.makeText(getContext(), getString(R.string.editcode_error), Toast.LENGTH_LONG).show();
        }


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
            ((EditRiddleActivity) getActivity()).updateCode(mEditText.getText().toString(), this.pos);
        } else {
            ((EditRiddleActivity) getActivity()).addCode(mEditText.getText().toString());
        }

    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
