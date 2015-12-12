package cz.muni.fi.pa165.exceptions;

/**
 * @author Karel Vaculik
 */
public class InvalidRequestFormatException extends RuntimeException {

    public InvalidRequestFormatException(String msg) {
        super(msg);
    }
}
