package cz.muni.fi.pa165.security;

import cz.muni.fi.pa165.dto.UserSystemDTO;
import cz.muni.fi.pa165.facade.UserSystemFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Karel Vaculik
 */

@Component
public class TokenAuthenticationService {

    @Autowired
    private TokenHandler tokenHandler;

    public static final String AUTH_NAME_HEADER = "X-AUTH-TOKEN";


    public void addAuthentication(HttpHeaders httpHeaders, UserAuthentication userAuthentication) {
        UserSystemDTO user = userAuthentication.getDetails();
        httpHeaders.add(AUTH_NAME_HEADER, tokenHandler.createToken(user));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_NAME_HEADER);
        if (token != null) {
            UserSystemDTO user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }
}
