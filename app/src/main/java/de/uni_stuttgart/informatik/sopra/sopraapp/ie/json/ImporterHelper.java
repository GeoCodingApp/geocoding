package de.uni_stuttgart.informatik.sopra.sopraapp.ie.json;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import de.uni_stuttgart.informatik.sopra.sopraapp.util.Encryptor;

public class ImporterHelper {
    private static final String TAG = "ImporterHelper";
    private static ObjectMapper mapper = new ObjectMapper();

    public static JsonNode getJsonFromFile(InputStream file) {
        java.util.Scanner scanner = new java.util.Scanner(file).useDelimiter("\\A");
        StringBuilder fileContent = new StringBuilder();
        while (scanner.hasNext()) {
            fileContent.append(scanner.next());
        }
        try {
            return mapper.readTree(fileContent.toString());

        } catch (IOException e) {
            Log.d(TAG, "getJsonFromFile: " + e.getMessage());
        }
        return null;
    }

    public static boolean checkJson(JsonNode node) {
        if (node == null) return false;
        String checksum = node.get("checksum").asText();
        String data = node.get("data").asText();
        return checksum.equals(Encryptor.encrypt(data));
    }

    public static JsonNode decryptJson(JsonNode node) throws IOException {
        String date = node.get("date").asText();
        String data = node.get("data").asText();
        String decrypted = Encryptor.decrypt(data, date);
        return mapper.readTree(decrypted);
    }
}
