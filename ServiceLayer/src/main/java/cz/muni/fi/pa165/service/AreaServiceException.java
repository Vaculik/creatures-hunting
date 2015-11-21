/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.service;

/**
 *
 * @author zbora
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
