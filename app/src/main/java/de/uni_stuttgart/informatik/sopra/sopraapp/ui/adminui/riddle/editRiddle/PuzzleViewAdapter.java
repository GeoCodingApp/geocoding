package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle;

import android.graphics.Bitmap;
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

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.ImagesRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.util.BitmapHelper;
import io.github.kbiakov.codeview.CodeView;

/**
 * @author MikeAshi
 */
public class PuzzleViewAdapter extends RecyclerView.Adapter<PuzzleViewAdapter.CustomViewHolder> {
    private static final String TAG = "PuzzleViewAdapter";
    private List<PuzzleViewElement> mElements;
    private EditRiddleActivity mActivity;
    private ImagesRepository mImagesRepository;

    public PuzzleViewAdapter(EditRiddleActivity activity, List<PuzzleViewElement> elements) {
        this.mActivity = activity;
        this.mElements = elements;
        this.mImagesRepository = new ImagesRepository(activity);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_puzzle_element, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: Called");
        final PuzzleViewElement elem = mElements.get(i);
        switch (elem.getType()) {
            case CODE:
                viewHolder.text.setText("Code Segment");
                viewHolder.text.setVisibility(View.VISIBLE);
                viewHolder.code.setCode(mElements.get(i).getText());
                viewHolder.code.setVisibility(View.VISIBLE);
                viewHolder.img.setVisibility(View.GONE);
                break;
            case IMG:
                viewHolder.img.setVisibility(View.VISIBLE);
                Image image = mImagesRepository.getById(mElements.get(i).getImgId());
                byte[] bytes = image.getImage();
                Bitmap bitmap = BitmapHelper.decode(bytes);
                viewHolder.img.setImageBitmap(bitmap);
                viewHolder.text.setVisibility(View.GONE);
                viewHolder.code.setVisibility(View.GONE);
                break;
            case TEXT:
                viewHolder.text.setText(mElements.get(i).getText());
                viewHolder.text.setVisibility(View.VISIBLE);
                viewHolder.code.setVisibility(View.GONE);
                viewHolder.img.setVisibility(View.GONE);
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

    private void onCodeClick(String code, int i) {
        Log.d(TAG, "onCodeClick: called");
        mActivity.showUpdateCodeFragment(code, i);
    }

    private void onImgClick() {
        Log.d(TAG, "onImgClick: called");
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView text;
        CodeView code;
        ImageView img;
        LinearLayout parentLayout;

        public CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            text = itemView.findViewById(R.id.puzzle_element_text);
            code = itemView.findViewById(R.id.textView_code);
            img = itemView.findViewById(R.id.imageView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            //
            cardView.setOnClickListener(v -> {
                if (img.getVisibility() == View.VISIBLE) {
                    onImgClick();
                } else {
                    if (text.getText() == "Code Segment") {
                        onCodeClick(code.getOptions().getCode(), getAdapterPosition());
                    } else {
                        onTextClick(text.getText().toString(), getAdapterPosition());
                    }
                }
            });

        }
    }

}
