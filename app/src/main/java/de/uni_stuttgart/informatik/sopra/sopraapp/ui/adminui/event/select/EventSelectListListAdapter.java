package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event.select;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;

/**
 * @author Dominik Dec
 */
public class EventSelectListListAdapter extends RecyclerView.Adapter<EventSelectListListAdapter.ViewHolder> {

    private EventSelectGroupActivity activity;
    private PuzzleListRepository puzzleListRepository;
    private String eventId;
    private ArrayList<ListEntry> data = new ArrayList<>();

    public EventSelectListListAdapter(Context context, String eventid, EventSelectGroupActivity activity) {
        this.activity = activity;
        this.puzzleListRepository = new PuzzleListRepository(context);
        this.eventId = eventid;
        fetchDB();
    }

    private void fetchDB() {
        //get events from db
        ArrayList<ListEntry> list = new ArrayList<>();
        for (PuzzleList p : puzzleListRepository.getAll()) {
            list.add(new ListEntry(p.getName(), p.getId()));
        }


        data = list;

    }

    @NonNull
    @Override
    public EventSelectListListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_group_row, parent, false);
        return new EventSelectListListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.group.setText(data.get(position).getName());
        viewHolder.id = data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView group;
        String id;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            group = itemView.findViewById(R.id.select_group_row_name);
            //
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id.isEmpty()) {
                        Log.d("tag", "ID IS EMPTY");
                        return;
                    }
                    activity.finishWithResult(group.getText().toString(), id);
                }
            });
        }
    }

}
