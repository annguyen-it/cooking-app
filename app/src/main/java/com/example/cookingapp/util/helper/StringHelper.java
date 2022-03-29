package com.example.cookingapp.util.helper;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringHelper {
    public static String GetMd5Hash(String string) throws NoSuchAlgorithmException {
        return String.format(
            "%032x",
            new BigInteger(1, MessageDigest.getInstance("MD5").digest(string.getBytes(StandardCharsets.UTF_8)))
        );
    }
}
