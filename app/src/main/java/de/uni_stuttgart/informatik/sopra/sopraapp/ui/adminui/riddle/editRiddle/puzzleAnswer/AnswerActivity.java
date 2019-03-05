package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.AnswerRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.answerElements.LocationFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.answerElements.TextFragment;


/**
 * @author MikeAshi
 */
public class AnswerActivity extends AppCompatActivity {
    private static final String TAG = "AnswerActivity";

    private RecyclerView mAnswerView;
    private AnswerViewAdapter mAdapter;
    private FragmentManager mFragmentManager;
    private String mPuzzleId;
    private ArrayList<AnswerViewElement> mElements = new ArrayList<>();
    private AnswerRepository mAnswerRepository;
    private Answer mAnswer;
    private TextView mActionbar;
    private boolean mUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_answer);
        mAnswerRepository = new AnswerRepository(this);
        mAnswerView = findViewById(R.id.answer_view);
        mActionbar = findViewById(R.id.actionbar_answer);
        mAdapter = new AnswerViewAdapter(this, mElements);
        mFragmentManager = getSupportFragmentManager();
        mPuzzleId = getIntent().getStringExtra("puzzle_id");
        Log.d(TAG, "onCreate: puzzle_id: " + mPuzzleId);
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: called");
        super.onSaveInstanceState(outState);
        // save answer mElements
        outState.putParcelableArrayList("mElements", mElements);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: called");
        super.onRestoreInstanceState(savedInstanceState);
        // restore puzzle mElements
        ArrayList<AnswerViewElement> arrayList = savedInstanceState.getParcelableArrayList("mElements");
        mElements.clear();
        mElements.addAll(arrayList);
    }

    @Override
    public void onBackPressed() {
        saveAnswerAndFinish(null);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, R.string.cancelled, Toast.LENGTH_LONG).show();
            } else {
                addQR(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void initView() {
        mAnswerView.setAdapter(mAdapter);
        LinearLayoutManager manger = new LinearLayoutManager(this);
        mAnswerView.setLayoutManager(manger);
        // Touch helper to handle long click and drag/swipes
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mAnswerView);
        // get Answer mElements
        Optional<Answer> answer = mAnswerRepository.getPuzzleSolution(mPuzzleId);
        if (answer.isPresent()) {
            mAnswer = answer.get();
            mElements.addAll(mAnswer.getElements());
            mActionbar.setText(R.string.update_answer);
            mUpdate = true;
        } else {
            mAnswer = new Answer();
            mAnswer.setId(UUID.randomUUID().toString());
            mActionbar.setText(R.string.add_answer);
            mUpdate = false;
        }
    }

    public void saveAnswerAndFinish(View v) {
        if (!mElements.isEmpty()) {
            mAnswer.setPuzzleId(mPuzzleId);
            mAnswer.setElements(mElements);
            if (mUpdate) {
                mAnswerRepository.update(mAnswer);
            } else {
                mAnswerRepository.add(mAnswer);
            }
            Toast.makeText(this, R.string.saved, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.save_answer_error, Toast.LENGTH_LONG).show();
        }
        setResultAndFinish(null);
    }

    public void setResultAndFinish(View view) {
        Intent result = new Intent();
        if (!mElements.isEmpty()) {
            setResult(RESULT_OK, result);
        } else {
            if (mUpdate) {
                setResult(RESULT_OK, result);
            } else {
                setResult(RESULT_CANCELED, result);
            }
        }
        finish();
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
     * deletes puzzle element at the given position
     *
     * @param position
     */
    private void deleteItem(int position) {
        mElements.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    /**
     * Moves answer element from oldPos to newPos
     *
     * @param oldPos old position
     * @param newPos new position
     */
    private void moveItem(int oldPos, int newPos) {
        AnswerViewElement item = mElements.get(oldPos);
        mElements.remove(oldPos);
        mElements.add(newPos, item);
        mAdapter.notifyItemMoved(oldPos, newPos);
    }

    /***************************[ onClick methods]******************************/
    public void onClickText(View view) {
        Log.d(TAG, "onClickText: add text clicked");
        showTextFragment();
    }

    public void onClickQR(View view) {
        Log.d(TAG, "onClickQR: add QR clicked");
        showQRFragment();
    }

    public void onClickLocation(View view) {
        Log.d(TAG, "onClickLocation: add Location clicked");
        showLocationFragment();
    }

    /***************************[ show Fragments ]******************************/

    private void showTextFragment() {
        TextFragment newFragment = new TextFragment();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    public void showUpdateTextFragment(String text, int i) {
        TextFragment newFragment = new TextFragment();
        newFragment.setText(text);
        newFragment.setUpdate(true);
        newFragment.setPos(i);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private void showQRFragment() {
        new IntentIntegrator(this)
                .setPrompt(getString(R.string.qr_prompt))
                .initiateScan();
    }

    private void showLocationFragment() {
        LocationFragment newFragment = new LocationFragment();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    public void showUpdateLocationFragment(int position) {
        LocationFragment newFragment = new LocationFragment();
        newFragment.setUpdate(true);
        newFragment.setPos(position);
        newFragment.setLat(mElements.get(position).getLatitude());
        newFragment.setLon(mElements.get(position).getLongitude());
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    /***************************[ save / mUpdate methods]******************************/

    public void updateText(String string, int position) {
        mElements.set(position, new AnswerViewElement(AnswerViewElement.Type.TEXT, string));
        notifyChange();
    }

    public void addText(String string) {
        mElements.add(new AnswerViewElement(AnswerViewElement.Type.TEXT, string));
        notifyChange();
    }

    public void addQR(String string) {
        mElements.add(new AnswerViewElement(AnswerViewElement.Type.QR, string));
        notifyChange();
    }

    public void addLocation(String latitude, String longitude) {
        mElements.add(new AnswerViewElement(AnswerViewElement.Type.LOCATION, latitude, longitude));
        notifyChange();
    }

    public void updateLocation(String latitude, String longitude, int position) {
        mElements.set(position, new AnswerViewElement(AnswerViewElement.Type.LOCATION, latitude, longitude));
        notifyChange();
    }

    private void notifyChange() {
        mAdapter.notifyDataSetChanged();
    }

}
