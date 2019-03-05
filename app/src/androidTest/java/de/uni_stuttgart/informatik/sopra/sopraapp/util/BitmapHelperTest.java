package de.uni_stuttgart.informatik.sopra.sopraapp.util;

import android.graphics.Bitmap;
import android.util.Base64;

import org.junit.Test;

/**
 * @author MikeAshi
 */
public class BitmapHelperTest {
    BitmapHelper bitmapHelper = new BitmapHelper();
    byte[] image = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+P+/HgAFhAJ/wlseKgAAAABJRU5ErkJggg==", Base64.DEFAULT);


    @Test
    public void test() {
        Bitmap bitmap = bitmapHelper.decode(image);
        byte[] encoded = bitmapHelper.encode(bitmap);
        //assertEquals(image,encoded);
    }
}