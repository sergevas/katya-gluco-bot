package dev.sergevas.tool.katya.gluco.bot.security;

import com.google.common.hash.Hashing;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ApiSecretEncoder {

    public static String encode(String apiSecret) {
        return Hashing.sha1().hashBytes(apiSecret.getBytes(UTF_8)).toString();
    }
}
