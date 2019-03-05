package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist.editRiddleList;


import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;

/**
 * Used for displaying All assossciated Riddles of a List
 *
 * @author MikeAshi
 */
public class CurrentRiddleListAdapter extends RecyclerView.Adapter<CurrentRiddleListAdapter.ViewHolder> {
    private static final String TAG = "CurrentRiddleListAdapter";

    private List<Puzzle> puzzles;
    private EditRiddleListActivity activity;

    public CurrentRiddleListAdapter(EditRiddleListActivity activity, List<Puzzle> puzzles) {
        this.puzzles = puzzles;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CurrentRiddleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.riddle_riddlelist_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentRiddleListAdapter.ViewHolder holder, int position) {
        holder.name.setText(puzzles.get(position).getName());
        holder.id.setText(puzzles.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return puzzles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name;
        TextView id;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.riddlename_riddlelist);
            id = itemView.findViewById(R.id.riddle_id_riddlelist);
            //
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startEditRiddleActivity(id.getText().toString());
                }
            });
        }
    }

}
