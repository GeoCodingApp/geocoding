package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event.select.EventSelectGroupActivity;

/**
 * @author Dominik Dec
 */
public class EventAddGroupActivity extends AppCompatActivity {

    private final int GROUP_REQUEST = 11;
    private final int LIST_REQUEST = 22;
    private EventUserPuzzleListJoinRepository repository;
    private String userid = "";
    private String listid = "";
    private String eventid = "";
    private Button buttonadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add_group);
        setTitle(getString(R.string.eventaddgroup_title));

        eventid = getIntent().getStringExtra("eventid");

        repository = new EventUserPuzzleListJoinRepository(getApplicationContext());

        Button buttonaddgroup = findViewById(R.id.button_select_group);
        Button buttonaddlist = findViewById(R.id.button_select_list);
        Button buttoncancel = findViewById(R.id.button_cancel);
        buttonadd = findViewById(R.id.button_add);

        buttonaddgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventSelectGroupActivity.class);
                intent.putExtra("eventid", eventid);
                intent.putExtra("adapter", EventSelectGroupActivity.TYPE_GROUP);
                startActivityForResult(intent, GROUP_REQUEST);
            }
        });

        buttonaddlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventSelectGroupActivity.class);
                intent.putExtra("eventid", eventid);
                intent.putExtra("adapter", EventSelectGroupActivity.TYPE_LIST);
                startActivityForResult(intent, LIST_REQUEST);
            }
        });

        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonadd.setOnClickListener(v -> {
            repository.add(new EventUserPuzzleListJoin(eventid, listid, userid));
            finish();
        });
        buttonadd.setEnabled(false);


    }

    private boolean checkSelectionComplete() {
        boolean selectioncomplete = !userid.isEmpty() && !listid.isEmpty();
        buttonadd.setEnabled(selectioncomplete);
        return selectioncomplete;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String result;
            TextView name;
            switch (requestCode) {
                case GROUP_REQUEST:
                    result = data.getStringExtra("name");
                    name = findViewById(R.id.text_selectedgroup);
                    name.setText(result);

                    userid = data.getStringExtra("id");

                    break;
                case LIST_REQUEST:

                    result = data.getStringExtra("name");
                    name = findViewById(R.id.text_selectedlist);
                    name.setText(result);

                    listid = data.getStringExtra("id");

                    break;
            }
            checkSelectionComplete();
        }
    }


}
