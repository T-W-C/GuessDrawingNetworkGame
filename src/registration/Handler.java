package registration;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
        boolean passwordPhase = false; // boolean to tell us whether we are reading in the password or username
        int passwordInputCount = 0; // counter to see which input of the password we are on, should be max of 1

        System.out.println("Connected to socket: " + socket); // connection message

        StringBuffer sb = new StringBuffer();
        out.println("Please enter a username of one word length!");
        while(in.hasNextLine()) { // while a line is coming in from the socket (keeps reading in)
            sb.append(in.nextLine()); // adds the read in word to the StringBuffer
            if(!passwordPhase) { // in username phase
                if (!isValidUsername(sb.toString())) {
                    out.println("The username provided is not valid or is already in use! Please enter a new username" +
                            " that is only one word long.");
                } else {
                    out.println("Congratulations! Your username is now: " + sb.toString() + ". Now please enter a password!"); // print out that line to the client
                    sb.setLength(0); // clears the word in the StringBuffer
                    passwordPhase = true; // set passwordPhase to true since we will be entering it next
                }
            }
            else { // in password phase
                if(passwordInputCount == 1) { // already entered password before so this time we want to verify it
                    if(confirmPassword(sb.toString())) {
                        out.println("You have successfully verified your password, your account has now been created!");
                        break;
                    }
                    else {
                        out.println("The password you have entered does not match the one previously entered! Please input " +
                                "a new password and then make sure that you input the same password to verify.");
                        passwordInputCount = 0;
                    }
                }
                setPasswordInDatabase(sb.toString()); // put password in database
                passwordInputCount++; // update the password counter to 1 so we can then verify it
                out.println("Now please verify your password!");
            }
        }
    }

    public boolean isValidUsername(String username) {
        // check if not existing in database already and that it is only one word
        return true;
    }

    public void setPasswordInDatabase(String password) {
        // set hashed password in database
    }

    public boolean confirmPassword(String passwordToVerify) {
        // check if the second password input matches the first given one
        return true;
    }
}
