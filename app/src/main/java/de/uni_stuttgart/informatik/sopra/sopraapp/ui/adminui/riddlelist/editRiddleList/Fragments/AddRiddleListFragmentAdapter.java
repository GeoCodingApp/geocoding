package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist.editRiddleList.Fragments;


import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;

/**
 * Used for displaying All assossciated Riddles of a List
 *
 * @author MikeAshi
 */
public class AddRiddleListFragmentAdapter extends RecyclerView.Adapter<AddRiddleListFragmentAdapter.ViewHolder> {
    private static final String TAG = "AddRiddleListFragment";

    private List<Puzzle> puzzles;
    private AddRiddleFragment fragment;

    public AddRiddleListFragmentAdapter(AddRiddleFragment fragment, List<Puzzle> puzzles) {
        this.puzzles = puzzles;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public AddRiddleListFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_add_riddle_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddRiddleListFragmentAdapter.ViewHolder holder, int position) {
        holder.checkBox.setText(puzzles.get(position).getName());
        holder.id.setText(puzzles.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return puzzles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        CheckBox checkBox;
        TextView id;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            checkBox = itemView.findViewById(R.id.checkBox_add_riddle);
            id = itemView.findViewById(R.id.riddle_id_addriddle);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        fragment.add(id.getText().toString());
                    } else {
                        fragment.remove(id.getText().toString());
                    }
                }
            });
        }
    }

}
