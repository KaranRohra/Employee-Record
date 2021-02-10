package database;

import java.math.BigInteger;
import java.security.*;

public class HashPassword {
    public static String getSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1,messageDigest);

            String hashText = no.toString(16);
            while (hashText.length() < 32) {
                hashText = "0".concat(hashText);
            }
            return hashText;
        }

        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
