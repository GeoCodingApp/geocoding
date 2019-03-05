package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.LoginActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event.EventListAdapter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.eventexecution.EventSelectionAdapter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.group.GroupListAdapter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.RiddleListAdapter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist.RiddleListListAdapter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.ie.ExportActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.ie.importActivity;

/**
 * A hub where the admin can navigate to access different tools
 *
 * @author Dominik Dec
 */
public class AdminHubActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_hub);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.app_name);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white);

        Button groupbutton = findViewById(R.id.buttongroup);
        groupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGroupActivity();
            }
        });

        Button eventbutton = findViewById(R.id.buttonevent);
        eventbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEventActivity();
            }
        });

        Button listbutton = findViewById(R.id.buttonlist);
        listbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRidleListActivity();
            }
        });

        Button riddlebutton = findViewById(R.id.buttonriddle);
        riddlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRiddleActivity();
            }
        });

        Button startbutton = findViewById(R.id.buttonbeginevent);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startBeginEventActivity();

            }
        });

        Button exportButton = findViewById(R.id.button_export);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startExportActivity();
            }
        });

        Button importButton = findViewById(R.id.button_import);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImportActivity();
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        menuItem.setCheckable(false);

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.nav_events:
                                startEventActivity();
                                break;
                            case R.id.nav_groups:
                                startGroupActivity();
                                break;
                            case R.id.nav_lists:
                                startRidleListActivity();
                                break;
                            case R.id.nav_rid:
                                startRiddleActivity();
                                break;
                            case R.id.nav_import:
                                startImportActivity();
                                break;
                            case R.id.nav_export:
                                startExportActivity();
                                break;

                            case R.id.nav_logout:

                                //dialog if user is sure to logout

                                AlertDialog alertDialog = new AlertDialog.Builder(AdminHubActivity.this).create();
                                alertDialog.setTitle(getString(R.string.logout_title));
                                alertDialog.setMessage(getString(R.string.logout_message));
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.logout_positive),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //logout
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                finish();
                                                startActivity(intent);
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.logout_cancel),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Do nothing
                                            }
                                        });


                                alertDialog.show();

                                break;
                        }

                        return true;
                    }
                });

    }

    private void startImportActivity() {
        Intent intent = new Intent(getApplicationContext(), importActivity.class);
        startActivity(intent);
    }

    private void startExportActivity() {
        Intent intent = new Intent(getApplicationContext(), ExportActivity.class);
        startActivity(intent);
    }

    private void startBeginEventActivity() {
        Intent intent = new Intent(getApplicationContext(), AdaptiveListActivity.class);
        AdaptiveListActivity.setAdapter(new EventSelectionAdapter(getApplicationContext()));
        AdaptiveListActivity.setActionbarTitle(getString(R.string.eventcontrol_selection));
        intent.putExtra("adding", false);
        startActivity(intent);
    }

    private void startRiddleActivity() {
        Intent intent = new Intent(getApplicationContext(), AdaptiveListActivity.class);
        AdaptiveListActivity.setAdapter(new RiddleListAdapter(getApplicationContext()));
        AdaptiveListActivity.setActionbarTitle(getString(R.string.riddles));
        startActivity(intent);
    }

    private void startRidleListActivity() {
        Intent intent = new Intent(getApplicationContext(), AdaptiveListActivity.class);
        AdaptiveListActivity.setAdapter(new RiddleListListAdapter(getApplicationContext()));
        AdaptiveListActivity.setActionbarTitle(getString(R.string.riddlelist));
        startActivity(intent);
    }

    private void startEventActivity() {
        Intent intent = new Intent(getApplicationContext(), AdaptiveListActivity.class);
        AdaptiveListActivity.setAdapter(new EventListAdapter(getApplicationContext()));
        AdaptiveListActivity.setActionbarTitle(getString(R.string.events));
        startActivity(intent);
    }

    private void startGroupActivity() {
        Intent intent = new Intent(getApplicationContext(), AdaptiveListActivity.class);
        AdaptiveListActivity.setAdapter(new GroupListAdapter(getApplicationContext()));
        AdaptiveListActivity.setActionbarTitle(getString(R.string.groups));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
