package de.uni_stuttgart.informatik.sopra.sopraapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * This class provides the encode/decode methods which are needed
 * to store/retrieve images from db
 */
public class BitmapHelper {
    static public byte[] encode(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
        return outputStream.toByteArray();
    }

    static public Bitmap decode(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }
}
