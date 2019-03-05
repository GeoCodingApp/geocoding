package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event;

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
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListAdapter;

/**
 * Used for displaying the Event-List
 *
 * @author Dominik Dec
 */
public class EventListAdapter extends AdaptiveListAdapter<EventRowEntry> {

    private EventRepository repository;

    /**
     * Constructor
     *
     * @param context context for the inflator
     */
    public EventListAdapter(Context context) {
        super(context);

        repository = new EventRepository(context);
        fetchDB();
    }

    private void fetchDB() {
        //get events from db
        ArrayList<EventRowEntry> eventArrayList = new ArrayList<>();
        for (Event p : repository.getAll()) {
            eventArrayList.add(new EventRowEntry(p.getName()));
        }
        data = eventArrayList;
    }


    @Override
    public Intent createOnClickIntent(int index, Context context) {
        Intent intent = new Intent(context, EditEventActivity.class);
        intent.putExtra("name", data.get(index).getName());
        intent.putExtra("eventid", repository.getByName(data.get(index).getName()).get().getId());
        return intent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //inflate if necessary
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.event_row, null);

        //populate the entry
        TextView group = vi.findViewById(R.id.eventrow_name);
        group.setText(data.get(position).getName());

        //set remove action
        Button deletebutton = vi.findViewById(R.id.eventrow_delete_button);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.delete(repository.getAll().get(position));
                fetchDB();
                notifyDataSetChanged();
            }
        });

        return vi;
    }

    @Override
    public void onClickAdd() {
        repository.add(new Event("UntitledEvent"));
        fetchDB();
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataChanged() {
        fetchDB();
        notifyDataSetChanged();
    }

}