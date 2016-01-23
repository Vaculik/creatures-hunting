/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author zbora
 */
public class PasswordHash {

    private static final int workload = 12;
    private static final Logger logger = LoggerFactory.getLogger(PasswordHash.class);

    public static String hashPassword(String password) {
        if (password == null) {
            String msg = "Password parameter cannot be null.";
            logger.error(msg);
            throw new NullPointerException(msg);
        }

        String salt = BCrypt.gensalt(workload);

        return BCrypt.hashpw(password, salt);
    }
}
