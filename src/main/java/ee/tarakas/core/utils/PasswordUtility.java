package ee.tarakas.core.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtility {

    private static final int LOG_ROUNDS = 12; // valid range is 4 to 31

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    public static boolean checkPassword(String plainTextPassword, String storedPasswordHash) {
        return BCrypt.checkpw(plainTextPassword, storedPasswordHash);
    }
}
