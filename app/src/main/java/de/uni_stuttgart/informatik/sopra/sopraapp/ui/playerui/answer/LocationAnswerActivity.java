package de.uni_stuttgart.informatik.sopra.sopraapp.ui.playerui.answer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

public class LocationAnswerActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 7425;
    private LocationManager manager;
    private String provider;
    private Criteria criteria = new Criteria();
    private LocationListener listener;
    private TextView gpstext;
    private Button submitbutton;
    private double longitude, latitude;
    private Location location;

    @Override
    protected void onDestroy() {

        super.onDestroy();

        manager.removeUpdates(listener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_answer);

        gpstext = findViewById(R.id.gpstext);
        gpstext.setText("no");

        submitbutton = findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("location", location);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        //listener setup
        listener = new LocationListener() {
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }

            @Override
            public void onLocationChanged(Location plocation) {

                if (plocation != null) {
                    gpstext.setText(getString(R.string.gpsinfo, plocation.getLongitude(), plocation.getLatitude()));
                    //location.getLongitude(),location.getLatitude()
                    latitude = plocation.getLatitude();
                    longitude = plocation.getLongitude();
                    location = plocation;

                } else {
                }
            }
        };

    }


    @Override
    protected void onStart() {
        super.onStart();
        setup();
    }

    private void setup() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //permission granted
            provider = manager.getBestProvider(criteria, false);

            //check for valid provider
            if (provider == null) {
                //show alert dialog if location provider is inaccessible
                gpstext.setText(getString(R.string.noprovider));

                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                alertDialog.setTitle(getString(R.string.noprovider));
                alertDialog.setMessage(getString(R.string.noprovidertext));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.noproviderpos),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                alertDialog.show();

            } else {
                gpstext.setText(getString(R.string.gpswait));
                //valid provider -> get location
                manager.requestLocationUpdates(provider, 0, 0, listener);
            }

        } else {
            //request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission given.
                    setup();
                } else {
                    // permission denied
                    finish();
                }
                return;
            }
        }
    }

}
