package cz.muni.fi.pa165.security;

import cz.muni.fi.pa165.dto.UserSystemDTO;
import cz.muni.fi.pa165.facade.UserSystemFacade;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Karel Vaculik
 */

@Component
public class TokenHandler {

    private static final String KEY = "tokenKey";

    @Autowired
    private UserSystemFacade userFacade;


    public UserSystemDTO parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userFacade.getUserByUserName(username);
    }

    public String createToken(UserSystemDTO user) {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }
}
