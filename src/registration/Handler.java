package registration;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Handler implements Runnable {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    static String message_type = "REGISTRATION";

    public Handler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new Scanner(socket.getInputStream()); // create a scanner to read in from the socket
            out = new PrintWriter(socket.getOutputStream(), true); // create a writer to write to the socket

            if(message_type.equals("REGISTRATION")) { // checks that the action is a registration action
                synchronized (Server.getAllWriters()) { // synchronized so threads have to wait for this to add in the writer before they can
                    Server.addWriter("REGISTRATION", out); // add the writer with its type to the writer hashMap
                }
                registrationCode(); // run the registration code since the action is to do with registration
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                System.out.println("size before closing socket is: " + Server.getTypeOfWriters("REGISTRATION").size());
                socket.close();
                if(socket.isClosed()) {
                    System.out.println("The socket " + socket + " is now closed!");
                    Server.getTypeOfWriters("REGISTRATION").remove(out); // the client has closed so remove the writer from the registration set of writers
                    System.out.println("size after closing socket is: " + Server.getTypeOfWriters("REGISTRATION").size());
                }
            }
            catch(Exception e) {
                System.out.println("Error in socket: " + socket);
                e.printStackTrace();
            }
        }
    }

    public static String getMessage_type() {
        return message_type;
    }

    public static void setMessage_type(String message_type) {
        Handler.message_type = message_type;
    }

    public void registrationCode() {
        boolean usernamePhase = false; // boolean to tell us whether we are reading in the email or username
        boolean passwordPhase = false; // boolean to tell us whether we are reading in the username or password
        int passwordInputCount = 0; // counter to see which input of the password we are on, should be max of 1

        String email = "";
        String username = "";
        String hashedPassword = "";

        System.out.println("Connected to socket: " + socket); // connection message

        StringBuffer sb = new StringBuffer();
        out.println("Please enter an email address!");
        while(in.hasNextLine()) { // while a line is coming in from the socket (keeps reading in)
            sb.append(in.nextLine()); // adds the read in word to the StringBuffer
            if(!usernamePhase) { // reading in email
                if(!isValidEmailFormat(sb.toString())) {
                    out.println("The email address provided is not valid! Please enter a new email address.");
                }
                else {
                    email = sb.toString(); // update email field
                    setEmailInDatabase(email);
                    out.println("The email provided is of valid format! Now please enter a username that is one word in length and contains only numbers and letters.");
                    sb.setLength(0);
                    usernamePhase = true;
                }
            }
            else if (!passwordPhase) { // in username phase
                if (!isValidUsername(sb.toString())) {
                    out.println("The username provided is not valid or is already in use! Please enter a new username" +
                            " that is only one word long and contains only numbers and letters.");
                }
                else {
                    username = sb.toString(); // update username variable
                    setUsernameInDatabase(username);
                    out.println("Congratulations! Your username is now: " + sb.toString() + ". Now please enter a password!"); // print out that line to the client
                    sb.setLength(0); // clears the word in the StringBuffer
                    passwordPhase = true; // set passwordPhase to true since we will be entering it next
                }
            }
            else { // in password phase
                if (passwordInputCount == 1) { // already entered password before so this time we want to verify it
                    if (confirmHashOfPassword(sb.toString())) {
                        setPasswordInDatabase(hashedPassword);
                        out.println("You have successfully verified your password, your account has now been created!");
                        break;
                    }
                    else {
                        out.println("The password you have entered does not match the one previously entered! Please input " +
                                "a new password and then make sure that you input the same password to verify.");
                        passwordInputCount = 0;
                    }
                }
                hashedPassword = computeHashOfPassword(sb.toString()); // update password field
                passwordInputCount++; // update the password counter to 1 so we can then verify it
                out.println("Now please verify your password!");
            }
        }
    }

    public boolean isValidEmailFormat(String email) {
        if(email.equals("")) { return false; }
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
        return username.matches("^[a-zA-Z0-9]+");
    }

    public void setEmailInDatabase(String email) {
        // set username in database
    }

    public void setUsernameInDatabase(String username) {
        // set username in database
    }

    public void setPasswordInDatabase(String password) {
        // set hashed password in database
    }

    public String computeHashOfPassword(String password) {
        return password;
    }

    public boolean confirmHashOfPassword(String passwordToVerify) {
        // check if the second password input matches the first given one
        return true;
    }
}
