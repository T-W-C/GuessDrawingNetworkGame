package registration2;

import database.dao.PlayerDAO;
import database.manager.PasswordManager;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Handler2 implements Runnable {
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Handler2(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            os = socket.getOutputStream();
            out = new ObjectOutputStream(os); // create a writer to write to the socket
            is = socket.getInputStream();
            in = new ObjectInputStream(is); // create a scanner to read in from the socket

            Message m = (Message)in.readObject();
            if (m.getType().equals(Server2.QueryType.REGISTRATION)) { // checks that the action is a registration action
                registrationCode(); // run the registration code since the action is to do with registration
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
                if (socket.isClosed()) {
                    System.out.println("The socket " + socket + " is now closed!");
                }
            } catch (Exception e) {
                System.out.println("Error in socket: " + socket);
                e.printStackTrace();
            }
        }
    }

    public void registrationCode() throws Exception {
        boolean usernamePhase = false; // boolean to tell us whether we are reading in the email or username
        boolean passwordPhase = false; // boolean to tell us whether we are reading in the username or password
        int passwordInputCount = 0; // counter to see which input of the password we are on, should be max of 1

        String email = " ";
        String username = "";
        String hashedPassword = "";

        System.out.println("Connected to socket: " + socket); // connection message

        StringBuffer sb = new StringBuffer();
        Message m;
        out.writeObject(new Message(Server2.QueryType.REGISTRATION, "Please enter an email address!"));
        while (true) { // while a line is coming in from the socket (keeps reading in)
            sb.setLength(0); // clear buffer before appending new input
            m = (Message)in.readObject();
            sb.append(m.getMessage()); // adds the read in message to the StringBuffer
            if (!usernamePhase) { // reading in email
                if (!isValidEmail(sb.toString())) {
                    out.writeObject(new Message(Server2.QueryType.REGISTRATION, "The email address provided is not valid! Please enter a new email address."));
                } else {
                    email = sb.toString(); // update email field
                    out.writeObject(new Message(Server2.QueryType.REGISTRATION, "The email provided is of valid format! Now please enter a username that is one word in length and contains only numbers and letters."));
                    usernamePhase = true;
                }
            } else if (!passwordPhase) { // in username phase
                if (!isValidUsername(sb.toString())) {
                    out.writeObject(new Message(Server2.QueryType.REGISTRATION, "The username provided is not valid or is already in use! Please enter a new username" +
                            " that is only one word long and contains only numbers and letters."));
                } else {
                    username = sb.toString(); // update username variable
                    out.writeObject(new Message(Server2.QueryType.REGISTRATION, "Congratulations! Your username is now: " + sb.toString() + ". Now please enter a password!")); // print out that line to the client
                    passwordPhase = true; // set passwordPhase to true since we will be entering it next
                }
            } else { // in password phase
                if (passwordInputCount == 1) { // already entered password before so this time we want to verify it
                    if (confirmHashOfPassword(sb.toString(), hashedPassword)) {
                        PlayerDAO.createPlayer(username, hashedPassword, email);
                        out.writeObject(new Message(Server2.QueryType.REGISTRATION, "You have successfully verified your password, your account has now been created!"));
                        break;
                    } else {
                        System.out.println(confirmHashOfPassword(sb.toString(), hashedPassword));
                        out.writeObject(new Message(Server2.QueryType.REGISTRATION, "The password you have entered does not match the one previously entered! Please input " +
                                "a new password and then make sure that you input the same password to verify."));
                        passwordInputCount = 0;
                    }
                }
                hashedPassword = computeHashOfPassword(sb.toString()); // update password field
                passwordInputCount++; // update the password counter to 1 so we can then verify it
                out.writeObject(new Message(Server2.QueryType.REGISTRATION, "Now please verify your password!"));
            }
        }
    }

    public boolean isValidEmail(String email) {
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

    public boolean isValidUsername(String username) {
        // check if not existing in database already and that it is only one word and that it is not blank
        return username.matches("^[a-zA-Z0-9]+") && !(PlayerDAO.isPlayerExisting(username));
    }

    public String computeHashOfPassword(String password) {
        return PasswordManager.HashPassword(password);
    }

    public boolean confirmHashOfPassword(String passwordToVerify, String hashOfPassword) {
        // check if the second password input matches the first given one
        String secondHash = PasswordManager.HashPassword(passwordToVerify);
        return secondHash.equals(hashOfPassword);
    }
}
