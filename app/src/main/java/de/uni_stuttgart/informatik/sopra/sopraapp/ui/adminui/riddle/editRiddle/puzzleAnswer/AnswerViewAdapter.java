package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

/**
 * @author MikeAshi
 */
public class AnswerViewAdapter extends RecyclerView.Adapter<AnswerViewAdapter.ViewHolder> {
    private static final String TAG = "AnswerViewAdapter";
    ArrayList<AnswerViewElement> mElements;
    private AnswerActivity mActivity;

    public AnswerViewAdapter(AnswerActivity activity, ArrayList<AnswerViewElement> elements) {
        this.mElements = elements;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public AnswerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answer_element, viewGroup, false);
        AnswerViewAdapter.ViewHolder viewHolder = new AnswerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: Called");
        switch (mElements.get(i).getType()) {
            case LOCATION:
                viewHolder.qr.setVisibility(View.GONE);
                viewHolder.text.setText("Latitude = " + mElements.get(i).getLatitude() + " \nLongitude = " + mElements.get(i).getLongitude());
                break;
            case QR:
                viewHolder.text.setText(mElements.get(i).getText());
                viewHolder.text.setVisibility(View.GONE);
                viewHolder.qr.setVisibility(View.VISIBLE);
                viewHolder.qr.setImageBitmap(mElements.get(i).getBitmap());
                break;
            case TEXT:
                viewHolder.qr.setVisibility(View.GONE);
                viewHolder.text.setText(mElements.get(i).getText());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mElements.size();
    }

    private void onTextClick(String text, int i) {
        Log.d(TAG, "onTextClick: called");
        mActivity.showUpdateTextFragment(text, i);
    }

    private void onLocationClick(int position) {
        Log.d(TAG, "onLocationClick: called");
        mActivity.showUpdateLocationFragment(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView text;
        ImageView qr;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            text = itemView.findViewById(R.id.answer_element_text);
            qr = itemView.findViewById(R.id.answer_qr);

            parentLayout = itemView.findViewById(R.id.answer_parent_layout);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (text.getVisibility() == View.VISIBLE &&
                            (!text.getText().toString().contains("Longitude"))) {
                        onTextClick(text.getText().toString(), getAdapterPosition());
                    } else {
                        if (text.getVisibility() == View.VISIBLE) {
                            onLocationClick(getAdapterPosition());
                        }
                    }
                }
            });
        }
    }
}
