package cz.muni.fi.pa165.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Static utility class providing methods for encrypting and checking passwords.
 *
 * @author Karel Vaculik.
 */

public class PasswordUtil {
    private static final int workload = 12;
    private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);


    public static String hashPassword(String password) {
        if (password == null) {
            String msg = "Password parameter cannot be null.";
            logger.error(msg);
            throw new NullPointerException(msg);
        }

        String salt = BCrypt.gensalt(workload);

        return BCrypt.hashpw(password, salt);
    }


    public static Boolean checkPassword(String passwordToTest, String originalHash) {
        if (originalHash == null || !originalHash.startsWith("$2a$")) {
            String msg = "Invalid originalHash parameter.";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (passwordToTest == null) {
            logger.debug("Parameter passwordToTest is null.");
            return false;
        }

        return BCrypt.checkpw(passwordToTest, originalHash);
    }
}
