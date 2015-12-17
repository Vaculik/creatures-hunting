package cz.muni.fi.pa165.exceptions;

/**
 * @author Karel Vaculik
 */
public class InvalidRequestParameterException extends RuntimeException {
    public InvalidRequestParameterException(String msg) {
        super(msg);
    }
}
