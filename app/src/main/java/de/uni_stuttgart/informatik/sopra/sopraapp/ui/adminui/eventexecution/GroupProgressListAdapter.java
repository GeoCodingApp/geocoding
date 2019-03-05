package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.eventexecution;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListAdapter;

/**
 * Used for displaying the Group-List
 *
 * @author Dominik Dec
 */
public class GroupProgressListAdapter extends AdaptiveListAdapter<GroupProgressRowEntry> {

    private String eventName;
    private Event event;
    private EventRepository eventRepository;
    private EventUserPuzzleListJoinRepository eventUserPuzzleListJoinRepository;
    private PuzzleListRepository puzzleListRepository;
    private PuzzleRepository puzzleRepository;
    private UserRepository userRepository;
    private List<EventUserPuzzleListJoin> joins;

    /**
     * Constructor
     *
     * @param context context for the inflator
     */
    public GroupProgressListAdapter(Context context, String name) {

        super(context);
        eventName = name;
        eventRepository = new EventRepository(context);
        eventUserPuzzleListJoinRepository = new EventUserPuzzleListJoinRepository(context);
        puzzleListRepository = new PuzzleListRepository(context);
        userRepository = new UserRepository(context);
        puzzleRepository = new PuzzleRepository(context);
        //
        getProgress();
    }

    private void getProgress() {
        event = eventRepository.getByName(eventName).get();
        List arr = new ArrayList();
        int progress = 0;
        joins = eventUserPuzzleListJoinRepository.getByEvent(event.getId());
        for (EventUserPuzzleListJoin join : joins) {
            List<Puzzle> solved = eventUserPuzzleListJoinRepository.getSolvedPuzzles(join);
            ArrayList<String> PuzzleIds = puzzleListRepository.getById(join.getPuzzleListId()).get().getPuzzlesIds();
            List<Puzzle> puzzles = puzzleRepository.getById(PuzzleIds);
            Optional<User> user = userRepository.getById(join.getUserId());
            String username = "";
            if (!user.isPresent()) {
                username = "not set yet";
            } else {
                username = user.get().getName();
            }
            progress = ((solved.size() * 100) / puzzles.size());
            arr.add(new GroupProgressRowEntry(username, progress));
        }
        data = arr;
    }

    @Override
    public Intent createOnClickIntent(int index, Context context) {
        /*Intent intent = new Intent(context,EditGroupActivity.class);

        intent.putExtra("eventName",data[index].getName());
        intent.putExtrae("progress",data[index].getProgress());

        return intent;*/
        return null;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflate if necessary
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.progress_row, null);

        //populate the entry
        TextView group = vi.findViewById(R.id.progressrow_groupname);
        group.setText(data.get(position).getName());

        ProgressBar pw = vi.findViewById(R.id.progressrow_progressbar);
        pw.setProgress(data.get(position).getProgress(), true);

        return vi;
    }

    @Override
    public void notifyDataChanged() {
        getProgress();
        super.notifyDataChanged();
    }
}
