package de.uni_stuttgart.informatik.sopra.sopraapp.util;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * @author MikeAshi
 */
@RunWith(AndroidJUnit4.class)
public class encryptorTest {
    Encryptor encryptor = new Encryptor();

    @Test
    public void md5() {
        assertEquals(
                "098f6bcd4621d373cade4e832627b4f6",
                encryptor.encrypt("test")
        );
        assertEquals(
                "18126e7bd3f84b3f3e4df094def5b7de",
                encryptor.encrypt("mike")
        );
        assertEquals(
                "d41d8cd98f00b204e9800998ecf8427e",
                encryptor.encrypt("")
        );
    }

    @Test
    public void encrypt() {
        assertEquals("JL19LTUsdTSOM6X/pQZe5Q==", encryptor.encrypt("Secret message", "key"));
    }

    @Test
    public void decrypt() {
        assertEquals("Secret message", encryptor.decrypt("JL19LTUsdTSOM6X/pQZe5Q==", "key"));
    }

}
