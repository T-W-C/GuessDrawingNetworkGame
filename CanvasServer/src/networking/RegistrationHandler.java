package networking;

import database.dao.PlayerDAO;
import database.domain.Player;
import database.manager.PasswordManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationHandler {
    public static boolean isValidEmail(String email) {
        if (email.equals("")) {
            return false;
        }
        else if(PlayerDAO.isEmailExisting(email)){
            System.out.println("EmailL " + email + " already exists");
            return false;
        }
        else {
            // regex taken from website http://emailregex.com/
            String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }

    public static boolean isValidUsername(String username) {
        // check if not existing in database already and that it is only one word and that it is not blank
        return username.matches("^[a-zA-Z0-9]+") && !(PlayerDAO.isPlayerExisting(username));
    }

    public static String computeHashOfPassword(String password) {
        return PasswordManager.HashPassword(password);
    }

    public static boolean confirmHashOfPassword(String passwordToVerify, String hashOfPassword) {
        // check if the second password input matches the first given one
        String secondHash = PasswordManager.HashPassword(passwordToVerify);
        return secondHash.equals(hashOfPassword);
    }
}
