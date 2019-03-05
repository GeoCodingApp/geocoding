package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListAdapter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.EditRiddleActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Used for displaying the Group-List
 *
 * @author Dominik Dec
 */
public class RiddleListAdapter extends AdaptiveListAdapter<RiddleRowEntry> {

    private PuzzleRepository repository;
    private View vi;
    private Context mContext;

    /**
     * Constructor
     *
     * @param context context for the inflator
     */
    public RiddleListAdapter(Context context) {

        super(context);
        repository = new PuzzleRepository(context);
        mContext = context;
        fetchDB();
    }

    private void fetchDB() {
        //get riddlelists from db
        ArrayList<RiddleRowEntry> puzzleArrayList = new ArrayList<>();
        for (Puzzle p : repository.getAll()) {
            puzzleArrayList.add(new RiddleRowEntry(p.getName(), p.getId()));
        }
        data = puzzleArrayList;
    }

    @Override
    public Intent createOnClickIntent(int index, Context context) {
        Intent intent = new Intent(context, EditRiddleActivity.class);

        intent.putExtra("name", data.get(index).getName());
        intent.putExtra("id", data.get(index).getId());

        return intent;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //inflate if necessary
        vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.riddle_row, null);

        //populate the entry
        TextView group = vi.findViewById(R.id.riddlerow_riddlename);
        group.setText(data.get(position).getName());

        //set remove action
        final String id = data.get(position).getId();

        Button deletebutton = vi.findViewById(R.id.riddlerow_delete_button);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.delete(repository.getById(id).get());
                notifyDataChanged();
            }
        });
        return vi;
    }

    @Override
    public void onClickAdd() {
        Intent intent = new Intent(mContext, EditRiddleActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Override
    public void notifyDataChanged() {
        super.notifyDataChanged();
        fetchDB();
        notifyDataSetChanged();
    }
}
