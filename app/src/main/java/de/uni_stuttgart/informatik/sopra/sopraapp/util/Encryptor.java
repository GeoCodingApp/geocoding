package de.uni_stuttgart.informatik.sopra.sopraapp.util;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class provides an encrypt function
 * using the MD5 hashing and AES encryption algorithms.
 *
 * @author MikeAshi
 */
public class Encryptor {
    /**
     * Returns the hash of a given string
     * using the MD5 hashing algorithm
     *
     * @param string to be hashed
     * @return the hash
     */
    public static String encrypt(String string) {
        String generatedHash = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest encryptor = MessageDigest.getInstance("MD5");
            // Add password bytes to digest
            encryptor.update(string.getBytes());
            // Get the hash's bytes
            byte[] bytes = encryptor.digest();
            // Convert bytes (in decimal format) to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            // Get complete hashed password in hex format
            generatedHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedHash;
    }

    /**
     * Returns encrypted string using AES algorithm.
     *
     * @param string string to be encrypted
     * @param key    key
     * @return encrypted string
     */
    public static String encrypt(String string, String key) {
        String k = encrypt(key + (key.length() * 1994));
        String initVector = new StringBuilder(k).reverse().toString().substring(16);
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(k.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(string.getBytes());
            return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * decrypts an encrypted string.
     *
     * @param encrypted encrypted string
     * @param key       key
     * @return decrypted string
     */
    public static String decrypt(String encrypted, String key) {
        try {
            String k = encrypt(key + (key.length() * 1994));
            String initVector = new StringBuilder(k).reverse().toString().substring(16);

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(k.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.NO_WRAP));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
