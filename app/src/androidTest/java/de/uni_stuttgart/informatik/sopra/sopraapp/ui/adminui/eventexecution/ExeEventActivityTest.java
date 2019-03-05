package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.eventexecution;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.AppDatabase;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Event;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.EventRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.LoginActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

public class ExeEventActivityTest {
    private final String EVENTNAME = "test event name";
    private final String EVENTID = "test event id";
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private Event event = new Event(EVENTID, EVENTNAME, Event.Status.PAUSED);
    private EventRepository mEventRepository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mEventRepository = new EventRepository(context);
        AppDatabase.reset();
    }

    @After
    public void tearDown() throws Exception {
        AppDatabase.reset();
    }

    @Test
    public void EventStatusTest() {
        mEventRepository.add(event);
        Espresso.onView(withId(R.id.skipbutton_admin)).perform(click());
        Espresso.onView(withId(R.id.buttonbeginevent)).perform(click());
        Espresso.onView(withText(EVENTNAME)).perform(click());
        // check status
        Espresso.onView(withId(R.id.event_status_text)).check(matches(withText("paused")));
        // start event
        Espresso.onView(withId(R.id.primary_event_btn)).perform(click());
        // check db event
        event = mEventRepository.getById(event.getId()).get();
        assertEquals(Event.Status.STARTED, event.getStatus());
        // check ui event status
        Espresso.onView(withId(R.id.event_status_text)).check(matches(withText("running")));
        // pause event
        Espresso.onView(withId(R.id.primary_event_btn)).perform(click());
        // check db event
        event = mEventRepository.getById(event.getId()).get();
        assertEquals(Event.Status.PAUSED, event.getStatus());
        // check ui event status
        Espresso.onView(withId(R.id.event_status_text)).check(matches(withText("paused")));
        // stop event
        Espresso.onView(withId(R.id.secondary_event_btn)).perform(click());
        // check db event
        event = mEventRepository.getById(event.getId()).get();
        assertEquals(Event.Status.STOPPED, event.getStatus());
        // check ui event status
        Espresso.onView(withId(R.id.event_status_text)).check(matches(withText("stopped")));
    }

    @Test
    public void groupsProgressTest() {
        Espresso.onView(withId(R.id.skipbutton_admin)).perform(click());
        Espresso.onView(withId(R.id.buttonbeginevent)).perform(click());
        Espresso.onView(withText(mEventRepository.getAll().get(0).getName())).perform(click());
        Espresso.onView(withText("Progress")).perform(click());
    }
}