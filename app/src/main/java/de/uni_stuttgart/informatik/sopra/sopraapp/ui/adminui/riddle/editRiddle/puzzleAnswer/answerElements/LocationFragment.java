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
import android.widget.Toast;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerActivity;

/**
 * @author MikeAshi
 */
public class LocationFragment extends DialogFragment {
    private static final String TAG = "LocationFragment";

    private String lon, lat;
    private EditText mLatitude;
    private EditText mLongitude;
    private boolean update = false;
    private int pos;
    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_answer_location, container, false);

        (rootView.findViewById(R.id.button_close)).setOnClickListener(v -> dismiss());
        (rootView.findViewById(R.id.button_save)).setOnClickListener(v -> {
            save();
        });
        mLatitude = rootView.findViewById(R.id.answer_latitude);
        mLongitude = rootView.findViewById(R.id.answer_longitude);
        mLatitude.requestFocus();
        //
        if (lon != null) {
            mLatitude.setText(lat);
            mLongitude.setText(lon);
        }
        //
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
        if (mLatitude.getText().toString().equals("") || mLongitude.getText().toString().equals("")) {
            Toast.makeText(getContext(), getString(R.string.answer_location_error), Toast.LENGTH_LONG).show();
            return;
        }
        if (update) {
            // update
            ((AnswerActivity) getActivity()).updateLocation(mLatitude.getText().toString(), mLongitude.getText().toString(), this.pos);
        } else {
            ((AnswerActivity) getActivity()).addLocation(mLatitude.getText().toString(), mLongitude.getText().toString());
        }
        dismiss();
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
