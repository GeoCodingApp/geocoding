package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;

/**
 * Activity for Editing an Event
 *
 * @author Dominik Dec
 */
public class EditEventActivity extends AppCompatActivity {

    private static final int ADD_NEW_REQUEST = 34;
    private RecyclerView view;
    private EventGroupViewListAdapter adapter;
    private FloatingActionButton fab;
    private EditText nameedit;
    private String eventid;
    private EventRepository eventRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        nameedit = findViewById(R.id.editevent_nameedit);
        nameedit.setText(getIntent().getStringExtra("name"));

        view = findViewById(R.id.editevent_recyclerview);

        eventid = getIntent().getStringExtra("eventid");

        adapter = new EventGroupViewListAdapter(this, eventid);
        view.setAdapter(adapter);
        LinearLayoutManager manger = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager(manger);


        // Touch helper to handle long click and drag/swipes
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(view);

        //setup button for adding new groups/lists
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EventAddGroupActivity.class);
                intent.putExtra("eventid", getIntent().getStringExtra("eventid"));
                startActivityForResult(intent, ADD_NEW_REQUEST);
            }
        });

        //eventrep
        eventRepository = new EventRepository(getApplicationContext());

        //backbutton
        ImageButton backbutton = findViewById(R.id.button_close);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void savename() {
        Event e = eventRepository.getById(eventid).get();
        e.setName(nameedit.getText().toString());
        eventRepository.update(e);
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        adapter.remove(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;
    }

    @Override
    public void onBackPressed() {
        savename();
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_NEW_REQUEST) {
            Log.d("bbb", "SET CHANGED");
            adapter.fetchDB();
            adapter.notifyDataSetChanged();
        }
    }


}
