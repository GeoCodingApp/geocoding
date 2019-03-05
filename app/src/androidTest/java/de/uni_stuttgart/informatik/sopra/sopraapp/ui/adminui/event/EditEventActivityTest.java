package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.event;

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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotEquals;

public class EditEventActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
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
    public void EditEventTest() throws InterruptedException {
        Event event = mEventRepository.getAll().get(1);
        Espresso.onView(withId(R.id.skipbutton_admin)).perform(click());
        Espresso.onView(withId(R.id.buttonevent)).perform(click());
        Espresso.onView(withText(event.getName())).perform(click());
        Espresso.onView(withId(R.id.editevent_nameedit)).perform(replaceText("new Name"));
        closeSoftKeyboard();
        Thread.sleep(3000);
        Espresso.onView(withId(R.id.fab)).perform(click());
        Espresso.onView(withId(R.id.button_select_group)).perform(click());
        Espresso.onView(withText("gruppe1")).perform(click());
        Espresso.onView(withId(R.id.button_select_list)).perform(click());
        Espresso.onView(withText("List_Stuttgart_A")).perform(click());
        Espresso.onView(withId(R.id.button_add)).perform(click());
        Espresso.pressBack();
        assertNotEquals(mEventRepository.getById(event.getId()).get().getName(), event.getName());
    }
}