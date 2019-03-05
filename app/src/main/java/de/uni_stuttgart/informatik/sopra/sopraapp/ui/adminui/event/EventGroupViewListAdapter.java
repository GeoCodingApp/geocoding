package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;

/**
 * @author Dominik Dec
 */
public class EventGroupViewListAdapter extends RecyclerView.Adapter<EventGroupViewListAdapter.ViewHolder> {

    private EventUserPuzzleListJoinRepository repository;
    private UserRepository userRepository;
    private PuzzleListRepository puzzleListRepository;
    private String eventId;
    private ArrayList<EventGroupEntry> data = new ArrayList<>();

    public EventGroupViewListAdapter(Context context, String eventid) {
        repository = new EventUserPuzzleListJoinRepository(context);
        userRepository = new UserRepository(context);
        puzzleListRepository = new PuzzleListRepository(context);
        eventId = eventid;
        fetchDB();
    }

    public void fetchDB() {
        //get events from db
        ArrayList<EventGroupEntry> eventArrayList = new ArrayList<>();
        for (EventUserPuzzleListJoin p : repository.getByEvent(eventId)) {
            eventArrayList.add(new EventGroupEntry(userRepository.getById(p.getUserId()).get().getName(), puzzleListRepository.getById(p.getPuzzleListId()).get().getName()));
        }
        data = eventArrayList;

    }

    public void remove(int pos) {

        repository.delete(repository.getAll().get(pos));

        data.remove(pos);

        this.notifyItemRemoved(pos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventGroupViewListAdapter.ViewHolder viewHolder, int position) {
        viewHolder.group.setText(data.get(position).getGroupname());
        viewHolder.list.setText(data.get(position).getListname());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView group;
        TextView list;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            group = itemView.findViewById(R.id.grouplistrow_group);
            list = itemView.findViewById(R.id.grouplistrow_list);
            //
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //activity.startEditRiddleActivity(id.getText().toString());
                }
            });
        }
    }

}
