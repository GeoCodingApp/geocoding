package de.uni_stuttgart.informatik.sopra.sopraapp.ui.ie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.controllers.LoginController;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.Exporter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.util.ProgressDialog;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.util.ViewDialog;

/**
 * @author MikeAshi
 */
public class ExportActivity extends AppCompatActivity {
    private static final String TAG = "ExportActivity";

    private EventRepository mEventRepository;
    private EventUserPuzzleListJoinRepository mEventUserPuzzleListJoinRepository;
    private List<Event> mEvents = new ArrayList<>();
    private List<String> mEventNames = new ArrayList<>();
    private Spinner mEventSpinner;
    private Button mExportButton;
    private TextView mErrorMsg;
    private ViewDialog viewDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        Log.d(TAG, "onCreate: created");
        mEventRepository = new EventRepository(this);
        mExportButton = findViewById(R.id.button_export);
        mErrorMsg = findViewById(R.id.export_no_event_error);
        viewDialog = new ProgressDialog(this);
        LoginController controller = LoginController.getInstance(this);
        if (controller.isAdmin()) {
            initEventsAsAdmin();
        } else {
            initEventsAsUser();
        }
        initEventNames();
        initEventSpinner();
        initTopButtons();
    }

    private void initTopButtons() {
        findViewById(R.id.button_back).setOnClickListener(v -> onBackPressed());
    }

    /**
     * initialize the Spinner
     */
    private void initEventSpinner() {
        mEventSpinner = findViewById(R.id.event_spinier);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mEventNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEventSpinner.setAdapter(spinnerAdapter);
    }

    /**
     * get the event list for admin
     */
    private void initEventsAsAdmin() {
        mEvents = mEventRepository.getAll();
    }

    /**
     * get the event list for the current users
     */
    private void initEventsAsUser() {
        User currentUser = LoginController.getInstance(this).getCurrentUser();
        mEventUserPuzzleListJoinRepository = new EventUserPuzzleListJoinRepository(this);
        List<EventUserPuzzleListJoin> joins = mEventUserPuzzleListJoinRepository.getByUserId(currentUser.getId());
        for (EventUserPuzzleListJoin join : joins) {
            mEvents.add(mEventRepository.getById(join.getEventId()).get());
        }
    }

    /**
     * initialize the event names list.
     * shows error message if there is no events
     */
    private void initEventNames() {
        if (mEvents.isEmpty()) {
            showErrorMsg();
        } else {
            for (Event event : mEvents) {
                mEventNames.add(event.getName());
            }
        }
    }

    /**
     * show error message
     */
    private void showErrorMsg() {
        mErrorMsg.setVisibility(View.VISIBLE);
        mExportButton.setEnabled(false);
    }

    public void onClickExport(View view) {
        Event event = mEvents.get(mEventSpinner.getSelectedItemPosition());
        Exporter exporter = new Exporter(this, event);
        showLoadingDialog();
        File file = null;
        try {
            file = exporter.export();
        } catch (JsonProcessingException e) {
            Log.d(TAG, "onClickExport: " + e.getMessage());
        }
        hideLoadingDialog();
        if (file == null) {
            Toast.makeText(this, "Something went wrong !!", Toast.LENGTH_LONG).show();
        } else {
            startShareIntent(file);
        }
    }

    private void hideLoadingDialog() {
        viewDialog.hide();
    }

    private void showLoadingDialog() {
        viewDialog.show();
    }

    /**
     * creates the share intent to share the given file
     *
     * @param file file to be shared.
     */
    private void startShareIntent(final File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uriToFile = FileProvider.getUriForFile(ExportActivity.this, "de.uni_stuttgart.informatik.sopra.sopraapp.provider", file);
                final Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setDataAndType(uriToFile, "application/zip");
                shareIntent.putExtra("uri", uriToFile);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uriToFile);

                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                }
            }
        }).start();

    }
}
