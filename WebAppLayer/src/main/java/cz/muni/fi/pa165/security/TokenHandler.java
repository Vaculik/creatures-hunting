package cz.muni.fi.pa165.security;

import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.service.UserSystemService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Karel Vaculik
 */
public class TokenHandler {

    private final String key;
    @Autowired
    private UserSystemService userService;

    public TokenHandler(String key) {
        this.key = key;
    }

    public UserSystem parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.getUserByUserName(username);
    }

    public String createToken(UserSystem user) {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}
