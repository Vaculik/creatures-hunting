package cz.muni.fi.pa165.security;

import cz.muni.fi.pa165.entity.UserSystem;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Karel Vaculik
 */


public class TokenAuthenticationService {

    private TokenHandler tokenHandler;

    public static final String AUTH_NAME_HEADER = "X-AUTH-TOKEN";

    public TokenAuthenticationService(String key) {
        tokenHandler = new TokenHandler(key);
    }

    public void addAuthentication(HttpServletResponse response, UserAuthentication userAuthentication) {
        UserSystem user = userAuthentication.getDetails();
        response.addHeader(AUTH_NAME_HEADER, tokenHandler.createToken(user));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_NAME_HEADER);
        if (token != null) {
            UserSystem user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }
}
