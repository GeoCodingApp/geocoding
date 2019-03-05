package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListAdapter;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist.editRiddleList.EditRiddleListActivity;

/**
 * Used for displaying the Group-List
 *
 * @author Dominik Dec
 */
public class RiddleListListAdapter extends AdaptiveListAdapter<RiddleListRowEntry> {

    private PuzzleListRepository repository;
    private View vi;
    private Context context;

    /**
     * Constructor
     *
     * @param context context for the inflator
     */
    public RiddleListListAdapter(Context context) {
        super(context);

        this.context = context;

        repository = new PuzzleListRepository(context);

        fetchDB();
    }

    private void fetchDB() {
        //get riddlelists from db

        ArrayList<RiddleListRowEntry> puzzleArrayList = new ArrayList<>();
        for (PuzzleList p : repository.getAll()) {
            puzzleArrayList.add(new RiddleListRowEntry(p.getName()));
        }
        data = puzzleArrayList;
    }

    @Override
    public Intent createOnClickIntent(int index, Context context) {
        Intent intent = new Intent(context, EditRiddleListActivity.class);
        intent.putExtra("listid", repository.getAll().get(index).getId());
        return intent;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //inflate if necessary
        vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.riddlelist_row, null);

        //populate the entry
        TextView group = vi.findViewById(R.id.riddlelistrow_name);
        group.setText(data.get(position).getName());


        //set remove action
        Button deletebutton = vi.findViewById(R.id.riddlelistrow_delete_button);
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
        Intent intent = new Intent(context, EditRiddleListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void notifyDataChanged() {
        super.notifyDataChanged();
        fetchDB();
        notifyDataSetChanged();
    }
}
