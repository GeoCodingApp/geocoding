package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author MikeAshi
 */
public class PuzzleViewElement implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PuzzleViewElement> CREATOR = new Parcelable.Creator<PuzzleViewElement>() {
        @Override
        public PuzzleViewElement createFromParcel(Parcel in) {
            return new PuzzleViewElement(in);
        }

        @Override
        public PuzzleViewElement[] newArray(int size) {
            return new PuzzleViewElement[size];
        }
    };
    private Type type;
    private String text;
    private String imgId;

    public PuzzleViewElement() {
        //
    }

    public PuzzleViewElement(Type type, String string) {
        this.type = type;
        if (type == Type.IMG) {
            this.imgId = string;
        } else {
            this.text = string;
        }
    }


    protected PuzzleViewElement(Parcel in) {
        type = (Type) in.readValue(Type.class.getClassLoader());
        text = in.readString();
        imgId = in.readString();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return text;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeValue(type);
        dest.writeString(text);
        dest.writeValue(imgId);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        CODE, TEXT, IMG
    }
}