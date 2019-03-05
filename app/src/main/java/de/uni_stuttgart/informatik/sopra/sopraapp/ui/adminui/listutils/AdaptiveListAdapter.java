package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.listutils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @param <T> what type of Data (AdaptiveList-Entry) to hold
 * @author Dominik Dec
 * <p>
 * A AdaptiveList-Adapter is used by AdaptiveList-Activity
 * It is responsible to populate the list, which is later displayed by the AdaptiveList-Activity
 * override getView and createOnClickIntent
 */
public abstract class AdaptiveListAdapter<T extends AdaptiveListEntry> extends BaseAdapter {

    protected static LayoutInflater inflater = null;
    /**
     * this is the list which contains all data to be displayed inside the list
     */
    protected List<T> data;
    protected AppCompatActivity activityRef;

    /**
     * Constructor
     */
    public AdaptiveListAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Will be called form the AdaptiveList-Activity if a Entry has been clicked
     * Here you should define the intent which should be called if the entry has been clicked
     *
     * @param index the index of the element which has been clicked
     * @return the intent you wish to start
     */
    public abstract Intent createOnClickIntent(int index, Context context);

    public void onClickAdd() {

    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    public void setActivityRef(AppCompatActivity ref) {
        activityRef = ref;
    }
}
