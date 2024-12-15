package ru.averkiev.gateway.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Utils {
    private Utils() {
    }

    public static String encodeToBase64(final String decodedString) {
        return new String(Base64.getEncoder().encode(decodedString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String decodeFromBase64(final String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }
}
