package gui.registration;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.Socket;

public class Controller {
    @FXML
    public TextField emailText;
    @FXML
    public TextField usernameText;
    @FXML
    public TextField passwordText;
    @FXML
    public TextField verifyPasswordText;
    @FXML
    public Label Message1;
    @FXML
    public Label Message2;

    @FXML
    public void createAndSubmitClient() throws Exception {
        // Passed First Check (Email Valid Phase)
        RegistrationHandler registrationHandler = new RegistrationHandler();
        registrationHandler.SendPacket(usernameText.getText());
        if (registrationHandler.Result()) {
            System.out.println("GUI Result is also true");
        } else {
            System.out.println("Not fast enough :/");
        }
        /*
        if(RegistrationHandler.isValidEmail(emailText.toString())){
            // Passed Second Check (Username Valid Check)
            if (RegistrationHandler.isValidUsername(usernameText.toString())){
                // Get hashed password
                String hashedPassword = passwordText.toString();
                // Passed Third Check (Password Matching Check)
                if (RegistrationHandler.confirmHashOfPassword(verifyPasswordText.toString(), hashedPassword)){
                    
                }
                // Failed Password Matching Check
                else {
                    // Output password error
                }
            }
            // Failed Username Check
            else {
                // Output username error
            }
        }
        // Failed Email Check
        else {
            //Output email error
        }
        */
    }

    @FXML
    public void clearEmailText() {
        if (emailText.getText().equals("Enter email here")) {
            emailText.clear();
        }
    }

    @FXML
    public void clearUserText() {
        if (usernameText.getText().equals("Enter username here")) {
            usernameText.clear();
        }
    }

    @FXML
    public void clearPasswordText() {
        if (passwordText.getText().equals("Enter password here")) {
            passwordText.clear();
        }
    }

    @FXML
    public void clearVerifyText() {
        if (verifyPasswordText.getText().equals("Re-enter password here")) {
            verifyPasswordText.clear();
        }
    }
}
