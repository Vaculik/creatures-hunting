package cz.muni.fi.pa165.security;

import cz.muni.fi.pa165.entity.UserSystem;
import cz.muni.fi.pa165.enums.UserType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Karel Vaculik
 */
public class UserAuthentication implements Authentication {

    private final UserSystem user;
    private Set<GrantedAuthority> authorities = new HashSet<>();
    private boolean authenticated = true;

    public UserAuthentication(UserSystem user) {
        this.user = user;
        authorities.add(new SimpleGrantedAuthority("USER"));
        if (user.getType() == UserType.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public UserSystem getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUserName();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return user.getUserName();
    }
}
