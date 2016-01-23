package cz.muni.fi.pa165.exceptions;

/**
 * @author Karel Vaculik
 */

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

//    public ResourceNotFoundException(String msg, Exception ex) {
//        super(msg, ex);
//    }
}
