package de.uni_stuttgart.informatik.sopra.sopraapp.ui.playerui;

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
import de.uni_stuttgart.informatik.sopra.sopraapp.controllers.LoginController;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;

/**
 * @author Dominik Dec
 * <p>
 * provides all related Events avaiable to current User
 */
public class PlayersEventsListAdapter extends RecyclerView.Adapter<PlayersEventsListAdapter.ViewHolder> {

    private ArrayList<EventRowEntry> data = new ArrayList<>();
    private PlayerEventsListAdapterInteractionListener mListener;
    private EventUserPuzzleListJoinRepository joinRepository;
    private EventRepository eventRepository;
    private LoginController controller;

    public PlayersEventsListAdapter(Context context, PlayerEventsListAdapterInteractionListener listener) {
        joinRepository = new EventUserPuzzleListJoinRepository(context);
        eventRepository = new EventRepository(context);
        mListener = listener;
        controller = LoginController.getInstance(context);
        fetchdb();
    }

    private void fetchdb() {
        data.clear();
        for (EventUserPuzzleListJoin join : joinRepository.getByUserId(controller.getCurrentUser().getId())) {
            data.add(new EventRowEntry(eventRepository.getById(join.getEventId()).get().getName(), join.getEventId()));
        }

        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_select_event_row, parent, false);
        return new PlayersEventsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.eventid = data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Interaction Listener
     */
    public interface PlayerEventsListAdapterInteractionListener {
        void playerChooseEvent(Event event);
    }

    private class EventRowEntry {
        String id;
        String name;

        public EventRowEntry(String name, String id) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name;
        String eventid;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.eventrow_name);
            //
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mListener.playerChooseEvent(eventRepository.getById(eventid).get());

                    //return eventid;
                    // Create new fragment and transaction
                    //Fragment newFragment = PlayerWaitingRoomFragment.newInstance(eventid);
                    //FragmentTransaction transaction = ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    //transaction.replace(R.id.fragment_container, newFragment);
                    //transaction.addToBackStack(null);

                    // Commit the transaction
                    //transaction.commit();

                }
            });
        }
    }


}
