package dev.sergevas.tool.katya.gluco.bot.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ApiSecretAuthentication extends AbstractAuthenticationToken {

    private final String apiSecret;

    public ApiSecretAuthentication(String apiSecret,
                                   @Nullable Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiSecret = apiSecret;
        setAuthenticated(true);
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return apiSecret;
    }
}
