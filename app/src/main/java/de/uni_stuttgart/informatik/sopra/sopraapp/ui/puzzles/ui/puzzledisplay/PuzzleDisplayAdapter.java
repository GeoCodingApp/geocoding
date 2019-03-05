package de.uni_stuttgart.informatik.sopra.sopraapp.ui.puzzles.ui.puzzledisplay;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.Image;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.ImagesRepository;
import de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.PuzzleViewElement;
import de.uni_stuttgart.informatik.sopra.sopraapp.util.BitmapHelper;
import io.github.kbiakov.codeview.CodeView;

/**
 * This Adapter can be used to display any puzzle in an Activity using a RecyclerView.
 *
 * @author Stefan
 */
public class PuzzleDisplayAdapter extends RecyclerView.Adapter {

    private List<PuzzleViewElement> elements;

    //remove this.
    private Context context;

    /**
     * @param elements The elements to display
     */
    public PuzzleDisplayAdapter(Context context, List<PuzzleViewElement> elements) {
        this.context = context;
        this.elements = elements;
    }

    private Bitmap getBitmapById(String imgId) {
        //remove this method ASAP.
        ImagesRepository imagesRepository = new ImagesRepository(context);
        Image img = imagesRepository.getById(imgId);
        byte[] image = img.getImage();

        Bitmap bitmap = BitmapHelper.decode(image);
        return bitmap;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        RecyclerView.ViewHolder viewHolder = null;
        View view;

        switch (viewType) {

            case 1:
                view = layoutInflater.inflate(R.layout.recycler_view_code_element_layout, viewGroup, false);
                viewHolder = new CodeViewHolder(view);
                break;
            case 2:
                view = layoutInflater.inflate(R.layout.recycler_view_text_element_layout, viewGroup, false);
                viewHolder = new TextViewHolder(view);
                break;
            case 3:
                view = layoutInflater.inflate(R.layout.recycler_view_image_element_layout, viewGroup, false);
                viewHolder = new ImageViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        PuzzleViewElement element = elements.get(i);
        // fill ViewHolder with data.

        switch (viewHolder.getItemViewType()) {

            case 1:
                CodeViewHolder vh = (CodeViewHolder) viewHolder;
                CodeView codeView = vh.getCodeView();
                String elementCode = element.getCode();
                codeView.setCode(elementCode);
                break;

            case 2:
                TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
                TextView textView = textViewHolder.getTextView();
                String elementText = element.getText();
                textView.setText(elementText);
                break;

            case 3:
                ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
                ImageView imageView = imageViewHolder.getImageView();
                Bitmap bitmap = getBitmapById(element.getImgId());
                imageView.setImageBitmap(bitmap);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        PuzzleViewElement element = elements.get(position);
        PuzzleViewElement.Type elementType = element.getType();
        switch (elementType) {

            case CODE:
                return 1;
            case TEXT:
                return 2;
            case IMG:
                return 3;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public void setElements(List<PuzzleViewElement> elements) {
        this.elements = elements;
    }

    // Create a ViewHolder class for each element type.
    // Thanks: https://stackoverflow.com/a/26245463

    public class CodeViewHolder extends RecyclerView.ViewHolder {

        private CodeView codeView;

        public CodeViewHolder(@NonNull View itemView) {
            super(itemView);
            codeView = itemView.findViewById(R.id.code_view);
        }

        public CodeView getCodeView() {
            return codeView;
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
