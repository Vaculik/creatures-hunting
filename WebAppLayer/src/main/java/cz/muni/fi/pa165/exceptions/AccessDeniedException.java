package cz.muni.fi.pa165.exceptions;

/**
 * @author Karel Vaculik
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String msg) {
        super(msg);
    }
}
