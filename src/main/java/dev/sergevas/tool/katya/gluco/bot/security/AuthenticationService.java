package dev.sergevas.tool.katya.gluco.bot.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Objects;

public class AuthenticationService {

    public static final String API_SECRET_HEADER = "api-secret";

    private final String encodedApiSecret;

    public AuthenticationService(String apiSecret) {
        this.encodedApiSecret = ApiSecretEncoder.encode(apiSecret);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        var apiSecret = request.getHeader(API_SECRET_HEADER);
        if (Objects.isNull(apiSecret) || !encodedApiSecret.equals(apiSecret)) {
            throw new BadCredentialsException("Invalid API Secret. Check " + API_SECRET_HEADER + " header");
        }
        return new ApiSecretAuthentication(apiSecret, AuthorityUtils.NO_AUTHORITIES);
    }
}
