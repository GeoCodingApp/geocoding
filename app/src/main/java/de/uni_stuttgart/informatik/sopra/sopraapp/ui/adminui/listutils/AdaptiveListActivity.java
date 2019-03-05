package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

/**
 * this activity is used to display a List view and provide a static way to set adapters
 * and custom actionbar titles.
 *
 * @author Dominik Dec
 */
public class AdaptiveListActivity extends AppCompatActivity {
    // Actionbar title
    private static String actionbarTitle;
    // List Adapter
    private static AdaptiveListAdapter adapter;
    ListView listview;
    FloatingActionButton fab;
    // used to hide the fab while adding activity is started
    private boolean adding = true;
    // used to prevent the activity from starting twice
    private boolean clicked = false;

    /**
     * Sets the AdaptiveListAdapter to use.
     *
     * @param adaptiveAdapter what adapter to use
     */
    public static void setAdapter(AdaptiveListAdapter adaptiveAdapter) {
        adapter = adaptiveAdapter;
    }

    public static void setActionbarTitle(String title) {
        actionbarTitle = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaptive_list);
        // init list
        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);
        setListViewOnItemClicked();
        // init fab
        fab = findViewById(R.id.floatingActionButton);
        setFabOnClick();
        //
        this.adding = getIntent().getBooleanExtra("adding", true);
        if (!this.adding) fab.hide();
        //
        adapter.setActivityRef(this);
        // init action bar
        initActionbar();
    }

    @Override
    protected void onResume() {
        // updates the list
        adapter.notifyDataChanged();
        super.onResume();
        // activates onclick activity start
        clicked = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // updates the list
        adapter.notifyDataChanged();
    }

    private void initActionbar() {
        TextView actionbar = findViewById(R.id.actionbar_title);
        actionbar.setText(actionbarTitle);

        // back button
        ((ImageButton) findViewById(R.id.button_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setFabOnClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create new element
                adapter.onClickAdd();
                //show element inside edit element activity

                /*Snackbar.make(view, "Added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    private void setListViewOnItemClicked() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = adapter.createOnClickIntent(i, getApplicationContext());

                if (intent != null) {
                    // prevents the activity from loading twice
                    if (!clicked) {
                        clicked = true;
                        // this is important to call notifyDataSetChanged after update or delete
                        startActivityForResult(intent, 1);

                    }
                }

            }
        });
    }
}
