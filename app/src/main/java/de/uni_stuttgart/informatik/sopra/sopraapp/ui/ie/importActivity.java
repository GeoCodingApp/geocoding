package de.uni_stuttgart.informatik.sopra.sopraapp.ui.ie;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StreamCorruptedException;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.ie.Importer;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.util.ProgressDialog;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.util.ViewDialog;

/**
 * @author MikeAshi
 */
public class importActivity extends AppCompatActivity {
    private static final String TAG = "importActivity";
    private static final int READ_REQUEST_CODE = 42;

    private Importer mImporter;
    private ViewDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        initTopButtons();
        mImporter = new Importer(this);
        loadingDialog = new ProgressDialog(this);
        ShareCompat.IntentReader intentReader = ShareCompat.IntentReader.from(importActivity.this);

        if (intentReader.isShareIntent()) {
            importFromShareIntent(intentReader);
        } else {
            String action = getIntent().getAction();
            if (action != null) {
                if (action.equals(Intent.ACTION_VIEW)) {
                    importFromViewIntent(getIntent());
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i(TAG, "Uri: " + uri.toString());
                importFromURI(uri);
            }
        }

    }

    private void initTopButtons() {
        findViewById(R.id.button_back).setOnClickListener(v -> onBackPressed());
    }


    private void importFromShareIntent(ShareCompat.IntentReader intentReader) {
        Uri uri = intentReader.getStream();
        importFromURI(uri);
    }

    private void importFromViewIntent(Intent intentReader) {
        Uri uri = getIntent().getData();
        importFromURI(uri);
    }

    private void importFromURI(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            loadingDialog.show();
            mImporter.setFile(inputStream);
            mImporter.doImport();
            loadingDialog.hide();
            showSuccessMsg();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Could not read the file", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onCreate: " + e.getMessage());
        } catch (StreamCorruptedException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d(TAG, "onCreate: " + e.getMessage());
        }
    }

    private void showSuccessMsg() {
        findViewById(R.id.import_layout).setVisibility(View.GONE);
        findViewById(R.id.s_msg).setVisibility(View.VISIBLE);
    }

    public void onClickChooseFile(View view) {
        performFileSearch();
    }

    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/zip");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}
