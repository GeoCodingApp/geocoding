package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist.editRiddleList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.converters.PuzzleListConverter;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.EditRiddleActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddlelist.editRiddleList.Fragments.AddRiddleFragment;

/**
 * Activity for Editing A Group
 *
 * @author MikeAshi
 */
public class EditRiddleListActivity extends AppCompatActivity {
    private static final String TAG = "EditRiddleListActivity";

    private String mPuzzleListId;
    private PuzzleListRepository mPuzzleListRepository;
    private PuzzleRepository mPuzzleRepository;
    private PuzzleList mPuzzleList;
    private ArrayList<Puzzle> puzzles = new ArrayList<>();
    private boolean update = false;
    private EditText name;
    private RecyclerView view;
    private CurrentRiddleListAdapter adapter;
    private String updateId = "";
    private FragmentManager mFragmentManager;
    private TextView mActionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_riddlelist);
        mPuzzleListId = getIntent().getStringExtra("listid");
        mPuzzleListRepository = new PuzzleListRepository(this);
        mPuzzleRepository = new PuzzleRepository(this);
        mFragmentManager = getSupportFragmentManager();
        name = findViewById(R.id.editriddlelist_name);
        view = findViewById(R.id.edit_puzzlelist_view);
        mActionbar = findViewById(R.id.actionbar_edit_riddlelist);
        initView();
        initActionbarButtons();
    }

    @Override
    protected void onResume() {
        if (!updateId.equals("")) {
            updatePuzzles();
        }
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        save();
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name.getText().toString());
        ArrayList<String> ids = new ArrayList<>();
        for (Puzzle p : puzzles) {
            ids.add(p.getId());
        }
        outState.putString("ids", PuzzleListConverter.toString(ids));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        name.setText(savedInstanceState.getString("name"));
        ArrayList<String> ids = PuzzleListConverter.toList(savedInstanceState.getString("ids"));
        puzzles.clear();
        for (String id : ids) {
            puzzles.add(mPuzzleRepository.getById(id).get());
        }
    }

    private void initView() {
        getPuzzleList();
        adapter = new CurrentRiddleListAdapter(this, puzzles);
        view.setAdapter(adapter);
        LinearLayoutManager manger = new LinearLayoutManager(getApplicationContext());
        view.setLayoutManager(manger);
        // Touch helper to handle long click and drag/swipes
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(view);
    }

    /**
     * Returns touch helper callback to handle long click and drag/swipes
     *
     * @return touch helper callback
     */
    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;
    }

    /**
     * Moves puzzle element from oldPos to newPos
     *
     * @param oldPos old position
     * @param newPos new position
     */
    private void moveItem(int oldPos, int newPos) {
        Puzzle item = puzzles.get(oldPos);
        puzzles.remove(oldPos);
        puzzles.add(newPos, item);
        adapter.notifyItemMoved(oldPos, newPos);
    }

    /**
     * deletes puzzle element at the given position
     *
     * @param position
     */
    private void deleteItem(int position) {
        puzzles.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void getPuzzleList() {
        Optional<PuzzleList> list = mPuzzleListRepository.getById(mPuzzleListId);
        if (list.isPresent()) {
            mPuzzleList = list.get();
            update = true;
            name.setText(mPuzzleList.getName());
            mActionbar.setText(getString(R.string.update_riddle_list));
            getPuzzles();
        } else {
            puzzles = new ArrayList<>();
            mPuzzleList = new PuzzleList();
        }
    }

    private void getPuzzles() {
        List<Puzzle> newPuzzles = mPuzzleRepository.getById(mPuzzleList.getPuzzlesIds());
        puzzles.clear();
        puzzles.addAll(newPuzzles);
    }

    private void updatePuzzles() {
        List<Puzzle> newPuzzles = mPuzzleRepository.getById(mPuzzleList.getPuzzlesIds());
        int pos = 0;
        for (int i = 0; i < puzzles.size(); i++) {
            if (puzzles.get(i).getId().equals(updateId)) {
                pos = i;
            }
        }
        for (int i = 0; i < newPuzzles.size(); i++) {
            if (newPuzzles.get(i).getId().equals(updateId)) {
                if (!newPuzzles.get(i).getName().equals(puzzles.get(pos).getName())) {
                    puzzles.set(pos, newPuzzles.get(i));
                    Log.d(TAG, "updatePuzzles: puzzles updated");
                }
            }
        }
        updateId = "";
    }

    private void initActionbarButtons() {
        findViewById(R.id.button_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.button_edit_riddle_list_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                finish();
            }
        });
    }


    private void save() {
        mPuzzleList.setName(name.getText().toString());
        ArrayList<String> ids = new ArrayList<>();
        for (Puzzle p : puzzles) {
            ids.add(p.getId());
        }
        mPuzzleList.setPuzzlesIds(ids);
        if (update) {
            mPuzzleListRepository.update(mPuzzleList);
        } else {
            mPuzzleListRepository.add(mPuzzleList);
        }
        Toast.makeText(this, R.string.saved, Toast.LENGTH_LONG).show();
    }

    public void startEditRiddleActivity(String id) {
        Intent intent = new Intent(this, EditRiddleActivity.class);
        intent.putExtra("id", id);
        updateId = id;
        startActivity(intent);
    }

    private void showAddRiddleFragment() {
        AddRiddleFragment newFragment = new AddRiddleFragment();
        newFragment.setCurrentPuzzles(puzzles);
        newFragment.setActivity(this);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    public void onClickAdd(View view) {
        showAddRiddleFragment();
    }

    public void addRiddles(List<Puzzle> puzzles) {
        this.puzzles.addAll(puzzles);
        adapter.notifyDataSetChanged();
    }
}
