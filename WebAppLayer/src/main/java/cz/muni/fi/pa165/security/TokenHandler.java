package cz.muni.fi.pa165.security;

import cz.muni.fi.pa165.dto.UserSystemDTO;
import cz.muni.fi.pa165.facade.UserSystemFacade;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Karel Vaculik
 */
public class TokenHandler {

    private final String key;
    @Autowired
    private UserSystemFacade userFacade;

    public TokenHandler(String key) {
        this.key = key;
    }

    public UserSystemDTO parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userFacade.getUserByUserName(username);
    }

    public String createToken(UserSystemDTO user) {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
