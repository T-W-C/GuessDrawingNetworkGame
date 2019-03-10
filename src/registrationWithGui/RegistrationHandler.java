package registrationWithGui;

import database.dao.PlayerDAO;
import database.manager.PasswordManager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationHandler {

    public static void run(ObjectInputStream in, ObjectOutputStream out, Socket socket) throws Exception {
        boolean usernamePhase = false; // boolean to tell us whether we are reading in the email or username
        boolean passwordPhase = false; // boolean to tell us whether we are reading in the username or password
        int passwordInputCount = 0; // counter to see which input of the password we are on, should be max of 1

        String email = "";
        String username = "";
        String hashedPassword = "";

        System.out.println("Connected to socket: " + socket); // connection message

        StringBuffer sb = new StringBuffer();
        Message m;
        out.writeObject(new Message(Server.QueryType.REGISTRATION, "Please enter an email address!"));
        while (true) { // while a line is coming in from the socket (keeps reading in)
            sb.setLength(0); // clear buffer before appending new input
            m = (Message)in.readObject();
            sb.append(m.getMessage()); // adds the read in message to the StringBuffer
            if (!usernamePhase) { // reading in email
                if (!isValidEmail(sb.toString())) { // if email invalid or already exists
                    out.writeObject(new Message(Server.QueryType.REGISTRATION, "The email address provided is not valid or already exists! Please enter a new email address."));
                } else {
                    email = sb.toString(); // update email field
                    out.writeObject(new Message(Server.QueryType.REGISTRATION, "The email provided is of valid format! Now please enter a username that is one word in length and contains only numbers and letters."));
                    usernamePhase = true;
                }
            } else if (!passwordPhase) { // in username phase
                if (!isValidUsername(sb.toString())) {
                    out.writeObject(new Message(Server.QueryType.REGISTRATION, "The username provided is not valid or is already in use! Please enter a new username" +
                            " that is only one word long and contains only numbers and letters."));
                } else {
                    username = sb.toString(); // update username variable
                    out.writeObject(new Message(Server.QueryType.REGISTRATION, "Congratulations! Your username is now: " + sb.toString() + ". Now please enter a password!")); // print out that line to the client
                    passwordPhase = true; // set passwordPhase to true since we will be entering it next
                }
            } else { // in password phase
                if (passwordInputCount == 1) { // already entered password before so this time we want to verify it
                    if (confirmHashOfPassword(sb.toString(), hashedPassword)) {
                        PlayerDAO.createPlayer(username.toLowerCase(), hashedPassword, email.toLowerCase());
                        out.writeObject(new Message(Server.QueryType.REGISTRATION, "You have successfully verified your password, your account has now been created!"));
                        break;
                    } else {
                        System.out.println(confirmHashOfPassword(sb.toString(), hashedPassword));
                        out.writeObject(new Message(Server.QueryType.REGISTRATION, "The password you have entered does not match the one previously entered! Please input " +
                                "a new password and then make sure that you input the same password to verify."));
                        passwordInputCount = 0;
                    }
                }
                else { // have not entered password before so update local password variable
                    hashedPassword = computeHashOfPassword(sb.toString()); // update password field
                    passwordInputCount++; // update the password counter to 1 so we can then verify it
                    out.writeObject(new Message(Server.QueryType.REGISTRATION, "Now please verify your password!"));
                }
            }
        }
    }

    public static boolean isValidEmailFormat(String email) {
        if (email.equals("")) {
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

    public static boolean isValidEmail(String email) {
        return isValidEmailFormat(email) && !(PlayerDAO.isEmailExisting(email));
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
