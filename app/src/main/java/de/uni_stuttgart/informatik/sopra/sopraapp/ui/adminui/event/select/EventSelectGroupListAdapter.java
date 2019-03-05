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
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;

/**
 * @author Dominik Dec
 */
public class EventSelectGroupListAdapter extends RecyclerView.Adapter<EventSelectGroupListAdapter.ViewHolder> {

    private EventUserPuzzleListJoinRepository repository;
    private UserRepository userRepository;
    private ArrayList<GroupEntry> data = new ArrayList<>();
    private String eventid;
    private EventSelectGroupActivity activity;

    public EventSelectGroupListAdapter(Context context, String eventid, EventSelectGroupActivity activity) {
        this.activity = activity;
        repository = new EventUserPuzzleListJoinRepository(context);
        userRepository = new UserRepository(context);
        this.eventid = eventid;
        fetchDB();
    }

    private void fetchDB() {
        //get events from db
        ArrayList<GroupEntry> list = new ArrayList<>();
        for (User p : userRepository.getAll()) {
            boolean isinevent = false;
            //check if user is already inside the event or admin, then it should not be included here

            if (p.isAdmin()) {
                isinevent = true;
            }

            for (EventUserPuzzleListJoin j : repository.getByUserId(p.getId())) {
                if (j.getEventId().equals(eventid)) {
                    isinevent = true;
                }
            }
            //add user
            if (!isinevent) {
                list.add(new GroupEntry(p.getName(), p.getId()));
            }
        }


        data = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_group_row, parent, false);
        return new ViewHolder(view);
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
