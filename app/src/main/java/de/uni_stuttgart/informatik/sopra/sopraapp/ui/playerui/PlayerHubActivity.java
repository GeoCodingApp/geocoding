package de.uni_stuttgart.informatik.sopra.sopraapp.ui.playerui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.controllers.LoginController;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Answer;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.EventUserPuzzleListJoin;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Puzzle;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.PuzzleList;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.AnswerRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventUserPuzzleListJoinRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleListRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.PuzzleRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.LoginActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer.AnswerViewElement;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.ie.importActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.playerui.answer.LocationAnswerActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.playerui.answer.TextAnswerActivity;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.puzzles.ui.puzzledisplay.PuzzleDisplayFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.puzzles.ui.puzzledisplay.SelectSolutionTypeFragment;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.puzzles.ui.puzzledisplay.SolutionType;

/**
 * main activity for hosting player's fragments
 *
 * @author Dominik Dec
 */
public class PlayerHubActivity extends AppCompatActivity implements PlayerFinishedFragment.PlayerFinishedFragementInteractionListener,
        PlayerWaitingRoomFragment.PlayerWaitingRoomFragmentInteractionListener,
        SelectPlayerEventFragment.SelectPlayerEventInteractionListener,
        PuzzleDisplayFragment.PuzzleDisplayFragmentInteractionListener,
        PlayersEventsListAdapter.PlayerEventsListAdapterInteractionListener, SelectSolutionTypeFragment.SelectSolutionTypeFragmentInteractionListener {


    private static final int CODE_TEXT = 345;
    private static final int CODE_GPS = 346;
    private static Event mCurrentEvent;
    private static User mCurrentUser;
    private static PuzzleList mCurrentPuzzleList;
    private static Puzzle mCurrentPuzzle;
    private boolean mShouldAdvancePuzzleFlag = false;
    private DrawerLayout mDrawerLayout;
    //repository
    private EventUserPuzzleListJoinRepository joinRepository;
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private PuzzleRepository puzzleRepository;
    private PuzzleListRepository puzzleListRepository;
    private AnswerRepository answerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_hub);

        //init Repositories
        joinRepository = new EventUserPuzzleListJoinRepository(getApplicationContext());
        eventRepository = new EventRepository(getApplicationContext());
        userRepository = new UserRepository(getApplicationContext());
        puzzleListRepository = new PuzzleListRepository(getApplicationContext());
        puzzleRepository = new PuzzleRepository(getApplicationContext());
        answerRepository = new AnswerRepository(getApplicationContext());

        mCurrentUser = LoginController.getInstance(getApplicationContext()).getCurrentUser();

        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar_playerhub);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        menuItem.setCheckable(false);
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();
                        switch (id) {

                            case R.id.nav_eventselection:

                                setFragment(SelectPlayerEventFragment.newInstance());

                                break;

                            case R.id.nav_import:

                                Intent intent = new Intent(getApplicationContext(), importActivity.class);
                                startActivity(intent);

                                break;

                            case R.id.nav_logout:


                                //dialog if user is sure to logout

                                AlertDialog alertDialog = new AlertDialog.Builder(PlayerHubActivity.this).create();
                                alertDialog.setTitle(getString(R.string.logout_title));
                                alertDialog.setMessage(getString(R.string.logout_message));
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.logout_positive),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //logout
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                finish();
                                                startActivity(intent);
                                            }
                                        });
                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.logout_cancel),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Do nothing
                                            }
                                        });


                                alertDialog.show();

                                break;
                        }

                        return true;
                    }
                });


        setFragment(SelectPlayerEventFragment.newInstance());
    }

    private void setFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private boolean hasNextPuzzle() {
        EventUserPuzzleListJoin join = joinRepository.getJoinByEventUser(mCurrentEvent, mCurrentUser);
        List<Puzzle> solvedPuzzles = joinRepository.getSolvedPuzzles(join);
        List<Puzzle> puzzles = joinRepository.getPuzzlesForPuzzleList(mCurrentPuzzleList);
        return puzzles.retainAll(solvedPuzzles);
    }

    private Puzzle getNextPuzzle() {
        if (hasNextPuzzle()) {
            EventUserPuzzleListJoin join = joinRepository.getJoinByEventUser(mCurrentEvent, mCurrentUser);
            List<Puzzle> solvedPuzzles = joinRepository.getSolvedPuzzles(join);
            List<Puzzle> puzzles = joinRepository.getPuzzlesForPuzzleList(mCurrentPuzzleList);
            mCurrentPuzzle = puzzles.stream().filter(x -> !solvedPuzzles.contains(x)).collect(Collectors.toList()).get(0);
            return mCurrentPuzzle;
        }

        return null;
    }

    //fragment callbacks

    private void advance() {
        if (hasNextPuzzle()) {
            setFragment(PuzzleDisplayFragment.newInstance(getNextPuzzle()));
        } else {
            setFragment(PlayerFinishedFragment.newInstance());
        }
    }

    private void setCurrentPuzzleAsSolved() {
        EventUserPuzzleListJoin join = joinRepository.getJoinByEventUser(mCurrentEvent, mCurrentUser);
        joinRepository.addSolvedPuzzle(join, mCurrentPuzzle.getId());
    }

    private void startActivityForSolutionType(SolutionType type) {
        Intent intent;
        switch (type) {
            case GPS:
                intent = new Intent(getApplicationContext(), LocationAnswerActivity.class);
                startActivityForResult(intent, CODE_GPS);
                break;
            case TEXT:
                intent = new Intent(getApplicationContext(), TextAnswerActivity.class);

                startActivityForResult(intent, CODE_TEXT);
                break;
            case QR_CODE:
                new IntentIntegrator(this)
                        .setPrompt(getString(R.string.qr_prompt))
                        .initiateScan();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CODE_TEXT:

                    List<String> answers = new LinkedList<>();
                    //data mining answers >:C
                    for (AnswerViewElement element : answerRepository.getPuzzleSolution(mCurrentPuzzle.getId()).get().getElements()) {
                        if (element.getType() == AnswerViewElement.Type.TEXT) {
                            answers.add(element.getText());
                        }
                    }

                    String useranswer = data.getStringExtra("text");

                    boolean solved = false;
                    for (String s : answers) {
                        if (s.equals(useranswer)) {
                            solved = true;
                        }
                    }

                    if (solved) {

                        //fragment transaction should not be done here will cause state loss
                        //rather it needs to be executed in on post resume to prevent it
                        mShouldAdvancePuzzleFlag = true;
                        Toast.makeText(getApplicationContext(), "Correct Answer!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Answer!", Toast.LENGTH_LONG).show();
                    }

                    break;
                case CODE_GPS:

                    final double allowedoffset = 50;//in meters
                    Location location = data.getParcelableExtra("location");

                    List<Location> gpsanswers = new LinkedList<>();
                    // >:C x2
                    for (AnswerViewElement element : answerRepository.getPuzzleSolution(mCurrentPuzzle.getId()).get().getElements()) {
                        if (element.getType() == AnswerViewElement.Type.LOCATION) {
                            Location l = new Location("");
                            l.setLongitude(Double.parseDouble(element.getLongitude()));
                            l.setLatitude(Double.parseDouble(element.getLatitude()));
                            gpsanswers.add(l);
                        }
                    }

                    boolean gpssolved = false;
                    Log.d("MY", location.getLongitude() + ", " + location.getLatitude());
                    for (Location a : gpsanswers) {
                        Log.d("DB", a.getLongitude() + ", " + a.getLatitude());

                        if (location.distanceTo(a) <= allowedoffset) {
                            gpssolved = true;
                        }
                    }

                    if (gpssolved) {
                        mShouldAdvancePuzzleFlag = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect Answer!", Toast.LENGTH_LONG).show();
                    }

                    break;
            }
        }
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                for (AnswerViewElement element : answerRepository.getPuzzleSolution(mCurrentPuzzle.getId()).get().getElements()) {
                    if (element.getType() == AnswerViewElement.Type.QR) {
                        if (element.getQR().equals(result.getContents())) {
                            mShouldAdvancePuzzleFlag = true;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mShouldAdvancePuzzleFlag) {
            setCurrentPuzzleAsSolved();
            advance();
        }
        //reset flag
        mShouldAdvancePuzzleFlag = false;
    }

    @Override
    public void playerEntersGame() {

        //get next puzzle

        mCurrentPuzzleList = joinRepository.getPuzzleListForEventUser(mCurrentEvent, mCurrentUser).get();
        //if there is a new puzzle
        advance();


        //else -> player finished fragment

    }

    @Override
    public void solutionButtonPressed() {

        // find different solution types for current puzzle:
        Answer puzzleSolution = answerRepository.getPuzzleSolution(mCurrentPuzzle.getId()).get();
        Set<AnswerViewElement.Type> types = new HashSet<>();
        for (AnswerViewElement elem : puzzleSolution.getElements()) {
            types.add(elem.getType());
        }
        SolutionType[] solutionTypes = convertEnum(types.toArray(types.stream().toArray(AnswerViewElement.Type[]::new)));

        if (solutionTypes.length > 1) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SelectSolutionTypeFragment newFragment = SelectSolutionTypeFragment.newInstance(solutionTypes);
            newFragment.show(transaction, "dialog");
        } else if (solutionTypes.length == 1) {
            // start the corresponding activity.
            startActivityForSolutionType(solutionTypes[0]);
        } else {
            // No solution types available for this puzzle!!!
        }
    }

    private SolutionType[] convertEnum(AnswerViewElement.Type[] type) {
        EnumMap<AnswerViewElement.Type, SolutionType> enumMap = new EnumMap<>(AnswerViewElement.Type.class);
        enumMap.put(AnswerViewElement.Type.QR, SolutionType.QR_CODE);
        enumMap.put(AnswerViewElement.Type.LOCATION, SolutionType.GPS);
        enumMap.put(AnswerViewElement.Type.TEXT, SolutionType.TEXT);
        SolutionType[] solutionTypes = Arrays.stream(type).map(enumMap::get).toArray(SolutionType[]::new);
        return solutionTypes;
    }

    @Override
    public void skipButtonPressed() {
        setCurrentPuzzleAsSolved();
        advance();
    }

    @Override
    public void solutionTypeSelected(SolutionType type) {
        startActivityForSolutionType(type);
    }

    @Override
    public void playerChooseEvent(Event event) {
        mCurrentEvent = event;

        //check if event has started
        boolean started = mCurrentEvent.getStatus() == Event.Status.STARTED;

        Fragment newFragment = PlayerWaitingRoomFragment.newInstance(started);
        setFragment(newFragment);
    }


}
