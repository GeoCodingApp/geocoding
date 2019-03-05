package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

/**
 * @author Dominik Dec
 */
public class AdaptiveListFragment extends Fragment {

    ListView listview;
    private AdaptiveListAdapter adapter;
    private boolean adding = true;


    public AdaptiveListFragment() {
        // Required empty public constructor
    }

    public static AdaptiveListFragment newInstance(boolean adding, AdaptiveListAdapter adapter) {
        AdaptiveListFragment fragment = new AdaptiveListFragment();
        fragment.setAdapter(adapter);
        Bundle args = new Bundle();
        args.putBoolean("add", adding);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Sets the AdaptiveListAdapter to use.
     *
     * @param adaptiveAdapter what adapter to use
     */
    public void setAdapter(AdaptiveListAdapter adaptiveAdapter) {
        adapter = adaptiveAdapter;
    }

    public void enableAdding(boolean adding) {
        this.adding = adding;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        listview = view.findViewById(R.id.listview);

        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = adapter.createOnClickIntent(i, view.getContext());

                if (intent != null) {
                    startActivity(intent);
                }

            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create new element
                //show element inside edit element activity
                adapter.onClickAdd();

                //Snackbar.make(view, "Replace with adding a new element", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        //this.adding = getIntent().getBooleanExtra("adding",true);
        this.adding = getArguments().getBoolean("add", true);
        if (!this.adding) {
            fab.hide();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adaptive_list, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
