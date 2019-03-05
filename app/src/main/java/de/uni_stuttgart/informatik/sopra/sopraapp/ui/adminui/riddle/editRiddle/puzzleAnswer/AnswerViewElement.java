package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.riddle.editRiddle.puzzleAnswer;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.Serializable;

/**
 * @author MikeAshi
 */
public class AnswerViewElement implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AnswerViewElement> CREATOR = new Parcelable.Creator<AnswerViewElement>() {
        @Override
        public AnswerViewElement createFromParcel(Parcel in) {
            return new AnswerViewElement(in);
        }

        @Override
        public AnswerViewElement[] newArray(int size) {
            return new AnswerViewElement[size];
        }
    };
    private Type type;
    private String text;
    private String mLatitude;
    private String mLongitude;

    public AnswerViewElement() {
    }

    public AnswerViewElement(Type type, String latitude, String longitude) {
        this.type = type;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public AnswerViewElement(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    protected AnswerViewElement(Parcel in) {
        type = (Type) in.readValue(Type.class.getClassLoader());
        text = in.readString();
        mLatitude = in.readString();
        mLongitude = in.readString();
    }

    public String getQR() {
        return this.text;
    }

    public void setQR(String text) {
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        this.mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        this.mLongitude = longitude;
    }

    public Bitmap getBitmap() {
        if (this.type == Type.QR) {
            try {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                return barcodeEncoder.encodeBitmap(this.text, BarcodeFormat.QR_CODE, 400, 400);
            } catch (Exception e) {
                //
            }
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeString(text);
        dest.writeString(mLatitude);
        dest.writeString(mLongitude);
    }

    public enum Type implements Serializable {
        QR, LOCATION, TEXT
    }
}