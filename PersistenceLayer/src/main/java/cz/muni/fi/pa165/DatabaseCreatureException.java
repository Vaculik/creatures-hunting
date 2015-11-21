/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import org.springframework.dao.DataAccessException;

/**
 * Exception for error during retrieving data from database
 * @author Martin Zboril
 */
public class DatabaseCreatureException extends DataAccessException{    	
	public DatabaseCreatureException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseCreatureException(String message) {
		super(message);
	}
	
}
