package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.ImagesRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleElements.CodeFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleElements.TextFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.util.BitmapHelper;

/**
 * @author MikeAshi
 */
public class EditRiddleActivity extends AppCompatActivity {
    private static final String TAG = "EditRiddleActivity";
    // Image request code
    private static final int REQUEST_IMG_CODE = 71;
    // answer request code
    private static final int REQUEST_ANSWER_CODE = 2;
    // Puzzle view Adapter, used to control the puzzle view elements
    private PuzzleViewAdapter mAdapter;
    // Recycler view to hold all the puzzle element
    private RecyclerView mPuzzleView;
    // puzzle elements array
    private ArrayList<PuzzleViewElement> mElements = new ArrayList<>();
    // input manger used to show or hide the soft keyboard
    private InputMethodManager mInputMethodManager;
    // fragment manger used to show fragment
    private FragmentManager mFragmentManager;
    // images repo
    private ImagesRepository mImagesRepository;
    // puzzle repo
    private PuzzleRepository mPuzzleRepository;
    private EditText mName;
    private EditText mDescription;
    private Puzzle mPuzzle;
    private TextView mActionbar;
    private String mPuzzleId;
    private boolean mUpdate;
    private boolean mSaveEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_riddle);
        hideActionBar(null);
        // init global variables
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mFragmentManager = getSupportFragmentManager();
        mPuzzleView = findViewById(R.id.puzzle_view);
        mAdapter = new PuzzleViewAdapter(this, mElements);
        mImagesRepository = new ImagesRepository(this);
        mPuzzleRepository = new PuzzleRepository(this);
        mName = findViewById(R.id.puzzle_name);
        mDescription = findViewById(R.id.puzzle_description);
        mActionbar = findViewById(R.id.actionbar_edit_riddle);
        initActionbarButtons();
        // init recycler view
        mPuzzleId = getIntent().getStringExtra("id");
        initPuzzleView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // get image
        if (requestCode == REQUEST_IMG_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            saveImage(data.getData());
        }
        // get answer activity result
        if (requestCode == REQUEST_ANSWER_CODE) {
            if (resultCode == RESULT_CANCELED) {
                mPuzzleRepository.delete(mPuzzle);
                mUpdate = false;
                mSaveEnabled = false;
            } else {
                mSaveEnabled = true;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: called");
        super.onSaveInstanceState(outState);
        // save puzzle elements
        outState.putParcelableArrayList("elements", mElements);
        // save name
        outState.putString("name", mName.getText().toString());
        outState.putString("des", mDescription.getText().toString());
        // save btn states
        outState.putBoolean("save", mSaveEnabled);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: called");
        super.onRestoreInstanceState(savedInstanceState);
        // restore puzzle elements
        ArrayList<PuzzleViewElement> arrayList = savedInstanceState.getParcelableArrayList("elements");
        mElements.clear();
        if (arrayList != null) mElements.addAll(arrayList);
        // restore name
        mName.setText(savedInstanceState.getString("name"));
        // restore description
        mDescription.setText(savedInstanceState.getString("des"));
        mSaveEnabled = savedInstanceState.getBoolean("save");
    }

    @Override
    public void onBackPressed() {
        if (mSaveEnabled) {
            savePuzzleAndFinish();
        } else {
            Toast.makeText(getApplicationContext(), R.string.puzzle_on_back_press_no_answer, Toast.LENGTH_LONG).show();
        }
        super.onBackPressed();
    }

    /**
     * initializes Recycler view for the puzzle elements
     */
    private void initPuzzleView() {
        Log.d(TAG, "initRecyclerView: init view.");
        mPuzzleView.setAdapter(mAdapter);

        LinearLayoutManager manger = new LinearLayoutManager(getApplicationContext());
        mPuzzleView.setLayoutManager(manger);

        // Touch helper to handle long click and drag/swipes
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mPuzzleView);
        // get puzzle body
        if (mPuzzleId == null) {
            mPuzzle = new Puzzle();
            mPuzzleId = mPuzzle.getId();
            mActionbar.setText(getString(R.string.add_new_riddle));
            mUpdate = false;
        } else {
            Optional<Puzzle> puzzle = mPuzzleRepository.getById(mPuzzleId);
            if (puzzle.isPresent()) {
                mPuzzle = puzzle.get();
                mName.setText(mPuzzle.getName());
                mDescription.setText(mPuzzle.getDescription());
                mElements.addAll(mPuzzle.getElements());
                mActionbar.setText(getString(R.string.edit_riddle));
                mUpdate = true;
                mSaveEnabled = true;
            }
        }
    }

    /**
     * init actionbar exit/save button
     */
    private void initActionbarButtons() {
        // exit button
        findViewById(R.id.button_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSaveEnabled) {
                    savePuzzleAndFinish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.puzzle_save_no_answer, Toast.LENGTH_LONG).show();
                }
            }
        });
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
        PuzzleViewElement item = mElements.get(oldPos);
        mElements.remove(oldPos);
        mElements.add(newPos, item);
        mAdapter.notifyItemMoved(oldPos, newPos);
        notifyChange();
    }

    /**
     * deletes puzzle element at the given position
     *
     * @param position
     */
    private void deleteItem(int position) {
        // remove the image from db
        if (mElements.get(position).getType() == PuzzleViewElement.Type.IMG) {
            String img_id = mElements.get(position).getImgId();
            mImagesRepository.delete(mImagesRepository.getById(img_id));
        }
        mElements.remove(position);
        mAdapter.notifyItemRemoved(position);
        notifyChange();
    }

    /***************************[ show Fragments ]******************************/

    private void showTextFragment() {
        TextFragment newFragment = new TextFragment();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    private void startAnswerActivity() {
        Intent intent = new Intent(this, AnswerActivity.class);
        intent.putExtra("puzzle_id", mPuzzleId);
        startActivityForResult(intent, REQUEST_ANSWER_CODE);
    }

    private void showCodeFragment() {
        CodeFragment newFragment = new CodeFragment();
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

    public void showUpdateCodeFragment(String code, int i) {
        CodeFragment newFragment = new CodeFragment();
        newFragment.setCode(code);
        newFragment.setUpdate(true);
        newFragment.setPos(i);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

    /***************************[ save / mUpdate methods]******************************/

    public void addCode(String code) {
        Log.d(TAG, "addCode: code added");
        mElements.add(new PuzzleViewElement(PuzzleViewElement.Type.CODE, code));
        notifyChange();
    }

    public void addText(String text) {
        Log.d(TAG, "addText: text added");
        mElements.add(new PuzzleViewElement(PuzzleViewElement.Type.TEXT, text));
        notifyChange();
    }

    /**
     * updates the text for the puzzle element at the given position
     *
     * @param text     the new text
     * @param position element position
     */
    public void updateText(String text, int position) {
        mElements.set(position, new PuzzleViewElement(PuzzleViewElement.Type.TEXT, text));
        notifyChange();
    }

    /**
     * updates the code for the puzzle element at the given position
     *
     * @param code     the new code
     * @param position element position
     */
    public void updateCode(String code, int position) {
        mElements.set(position, new PuzzleViewElement(PuzzleViewElement.Type.CODE, code));
        notifyChange();
    }

    /**
     * saves image and add it to the view
     *
     * @param filepath file path
     */
    private void saveImage(Uri filepath) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
            String imgId = saveImageToDB(bitmap);
            //
            PuzzleViewElement e = new PuzzleViewElement(PuzzleViewElement.Type.IMG, imgId);
            mElements.add(e);
            //
            notifyChange();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * saves an image to the db
     *
     * @param bitmap image
     * @return image id
     */
    private String saveImageToDB(Bitmap bitmap) {
        Image image = new Image(BitmapHelper.encode(bitmap));
        mImagesRepository.add(image);
        return image.getId();
    }

    /**
     * saves the puzzle to db
     */
    private void savePuzzle() {
        // save puzzle to db
        mPuzzle.setName(mName.getText().toString());
        mPuzzle.setDescription(mDescription.getText().toString());
        mPuzzle.setElements(mElements);
        if (mUpdate) {
            mPuzzleRepository.update(mPuzzle);
        } else {
            mPuzzleRepository.add((mPuzzle));
        }

        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        finish();
    }

    /***************************[ onClick methods]******************************/
    public void onClickAnswer(View view) {
        Log.d(TAG, "onClickAnswer: Answer clicked");
        if (!mUpdate) {
            mPuzzleRepository.add(mPuzzle);
            mUpdate = true;
        }
        startAnswerActivity();
    }

    public void onClickText(View view) {
        Log.d(TAG, "onClickText: add text clicked");
        showTextFragment();
    }

    public void onClickCode(View view) {
        Log.d(TAG, "onClickCode: add code clicked");
        showCodeFragment();
    }

    public void onClickImg(View view) {
        Log.d(TAG, "onClickImg: add image clicked");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMG_CODE);
    }


    /**
     * hides the action bar and show's the soft keyboard setting the focus on editText
     *
     * @param editText EditText
     */
    public void hideActionBar(EditText editText) {
        Log.d(TAG, "hideActionBar: called");
        getSupportActionBar().hide();
        if (editText != null)
            mInputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * shows he action bar and clear's focus
     */
    public void showActionBar() {
        Log.d(TAG, "showActionBar: called");
        //getSupportActionBar().show();
        View view = findViewById(android.R.id.content).getRootView();
        view.clearFocus();
    }

    /**
     * updates the view.
     * this method should be called after making any
     * changes to the Elements array.
     */
    public void notifyChange() {
        Log.d(TAG, "notifyChange: called " + mElements.size());
        mAdapter.notifyDataSetChanged();
        mPuzzleView.scrollToPosition(mElements.size() - 1);
    }

    /**
     * finish the activity
     */
    private void finishActivity() {
        if (mPuzzle != null) {
            Optional<Puzzle> puzzle = mPuzzleRepository.getById(mPuzzle.getId());
            if (puzzle.isPresent()) {
                if (puzzle.get().getElements() == null) {
                    mPuzzleRepository.delete(puzzle.get());
                } else {
                    if (puzzle.get().getElements().size() < 1) {
                        mPuzzleRepository.delete(puzzle.get());
                    }
                }
            }
        }
        finish();
    }

    /**
     * save and finish the activity
     */
    private void savePuzzleAndFinish() {
        if (!mElements.isEmpty()) {
            if (mName.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), R.string.no_name_puzzle_erro, Toast.LENGTH_LONG).show();
            } else {
                savePuzzle();
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_puzzle_erro, Toast.LENGTH_LONG).show();
        }
    }

}
