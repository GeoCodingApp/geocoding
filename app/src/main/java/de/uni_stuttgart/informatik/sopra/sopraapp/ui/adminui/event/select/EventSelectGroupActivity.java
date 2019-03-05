package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event.select;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

/**
 * @author Dominik Dec
 */
public class EventSelectGroupActivity extends AppCompatActivity {

    public final static int TYPE_GROUP = 123;
    public final static int TYPE_LIST = 234;

    private RecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_select_group);

        view = findViewById(R.id.selectgroup_recyclerview);

        int adaptertype = getIntent().getIntExtra("adapter", TYPE_GROUP);

        switch (adaptertype) {
            case TYPE_GROUP:
                view.setAdapter(new EventSelectGroupListAdapter(getApplicationContext(), getIntent().getStringExtra("eventid"), this));
                break;
            case TYPE_LIST:
                view.setAdapter(new EventSelectListListAdapter(getApplicationContext(), getIntent().getStringExtra("eventid"), this));
                break;
        }

        LinearLayoutManager manger = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager(manger);
    }

    protected void finishWithResult(String name, String id) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("name", name);
        returnIntent.putExtra("id", id);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


}
