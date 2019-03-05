package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.eventexecution;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event.EventRowEntry;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListAdapter;

/**
 * Used for displaying the Event-List
 *
 * @author Dominik Dec
 */
public class EventSelectionAdapter extends AdaptiveListAdapter<EventRowEntry> {

    /**
     * Constructor
     *
     * @param context context for the inflator
     */
    public EventSelectionAdapter(Context context) {
        super(context);

        //get events from db
        EventRepository repository = new EventRepository(context);
        ArrayList<EventRowEntry> eventArrayList = new ArrayList<>();
        for (Event p : repository.getAll()) {
            eventArrayList.add(new EventRowEntry(p.getName()));
        }
        data = eventArrayList;

    }

    @Override
    public Intent createOnClickIntent(int index, Context context) {
        Intent intent = new Intent(context, ExeEventActivity.class);
        intent.putExtra("name", data.get(index).getName());

/*
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Start Event?");
        alertDialog.setMessage("Do you want to start Event2 ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Don't start the event
                        intent = null;
                    }
                });


        alertDialog.show();
*/

        return intent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflate if necessary
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.event_row, null);

        //populate the entry
        TextView group = vi.findViewById(R.id.eventrow_name);
        group.setText(data.get(position).getName());

        Button button = vi.findViewById(R.id.eventrow_delete_button);
        button.setVisibility(View.INVISIBLE);

        return vi;
    }

}