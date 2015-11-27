/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service.exception;

/**
 * This exception is thrown when there is task to move a creature from specific
 * area but the creature is not in this area.
 *
 * @author Martin Zboril
 */
public class AreaServiceException extends RuntimeException {

    public AreaServiceException() {
        super();
    }

    public AreaServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AreaServiceException(String message) {
        super(message);
    }

    public AreaServiceException(Throwable cause) {
        super(cause);
    }
}
