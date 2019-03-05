package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.group;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListAdapter;

/**
 * Used for displaying the Group-List
 *
 * @author Dominik Dec
 */
public class GroupListAdapter extends AdaptiveListAdapter<GroupRowEntry> {
    UserRepository repository;

    /**
     * Constructor
     *
     * @param context context for the inflator
     */
    public GroupListAdapter(Context context) {

        super(context);
        repository = new UserRepository(context);
        fetchDB();
    }

    private void fetchDB() {
        ArrayList<GroupRowEntry> puzzleArrayList = new ArrayList<>();
        for (User user : repository.getAll()) {
            if (!user.isAdmin()) {
                puzzleArrayList.add(new GroupRowEntry(user));
            }
        }
        data = puzzleArrayList;
    }

    @Override
    public Intent createOnClickIntent(int index, Context context) {
        Intent intent = new Intent(context, EditGroupActivity.class);

        intent.putExtra("name", data.get(index).getName());
        intent.putExtra("pw", data.get(index).getPw());
        intent.putExtra("id", data.get(index).getId());

        return intent;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflate if necessary
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.group_row, null);

        //populate the entry
        TextView group = vi.findViewById(R.id.grouprow_groupname);
        group.setText(data.get(position).getName());

        TextView pw = vi.findViewById(R.id.grouprow_password);
        pw.setText(data.get(position).getPw());

        //set remove action
        final String id = data.get(position).getId();
        Button deleteButton = vi.findViewById(R.id.grouprow_delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
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
        User newUser = new User("change name", "change password", false);
        repository.add(newUser);
        data.add(new GroupRowEntry(newUser));
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataChanged() {
        super.notifyDataChanged();
        fetchDB();
        notifyDataSetChanged();
    }
}
