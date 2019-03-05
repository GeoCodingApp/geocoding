package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.eventexecution;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils.AdaptiveListFragment;

/**
 * @author Dominik Dec
 */
public class ExeEventActivity extends AppCompatActivity {
    private String name;
    private GroupProgressListAdapter groupProgressListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe_event);
        name = getIntent().getStringExtra("name");
        groupProgressListAdapter = new GroupProgressListAdapter(getApplicationContext(), name);
        AdaptiveListFragment frag = AdaptiveListFragment.newInstance(false, groupProgressListAdapter);

        // send Event name to the fragment
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        EventControlFragment eventControlFragment = new EventControlFragment();
        eventControlFragment.setArguments(bundle);
        eventControlFragment.setActivity(this);
        //
        PagerAdapter pa = new PagerAdapter(getSupportFragmentManager());
        pa.addFragment(eventControlFragment, "Control");
        pa.addFragment(frag, "Progress");

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pa);

        TabLayout layout = findViewById(R.id.tablayout);
        layout.setupWithViewPager(viewPager);

        //backbutton
        ImageButton backbutton = findViewById(R.id.button_close);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void update() {
        groupProgressListAdapter.notifyDataChanged();
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private final ArrayList<Fragment> fragments = new ArrayList<>();
        private final ArrayList<String> titles = new ArrayList<>();


        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
