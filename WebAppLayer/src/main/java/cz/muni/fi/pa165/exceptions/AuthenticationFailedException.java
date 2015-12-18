package cz.muni.fi.pa165.exceptions;

/**
 * @author Karel Vaculik
 */
public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException(String msg) {
        super(msg);
    }
}
