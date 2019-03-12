package gui.registration;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.io.IOException;
import java.util.Timer;

public class Controller {
    @FXML
    public TextField emailText;
    @FXML
    public TextField usernameText;
    @FXML
    public PasswordField passwordText;
    @FXML
    public PasswordField verifyPasswordText;
    @FXML
    public CheckBox termsCheckBox;

    @FXML
    public void createAndSubmitClient() throws Exception {

        if (termsCheckBox.isSelected()) {
            // Passed First Check (Email Valid Phase)
            RegistrationHandler registrationHandler = new RegistrationHandler();

            /* Packet Sends for Checks */
            registrationHandler.SendEmailPacket(emailText.getText());
            registrationHandler.SendUsernamePacket(usernameText.getText());
            String hashedPassword = HashPassword(passwordText.getText());
            /* */

            Timer t = new java.util.Timer();

            t.schedule(
                    new java.util.TimerTask() {
                        public void run() {
                            // Check Email
                            if (registrationHandler.emailResult) {
                                // Username Passed
                                if (registrationHandler.userResult) {
                                    System.out.println("It worked");
                                    // Check Passwords Match
                                    if (hashedPassword.equals(HashPassword(verifyPasswordText.getText()))) {
                                        // Done, create user now
                                        registrationHandler.SendCreateAccountPacket(emailText.getText(), usernameText.getText(), hashedPassword);
                                        System.out.println("Created Account for username " + usernameText.getText());
                                    } else {
                                        System.out.println("Passwords don't match!");
                                    }
                                } else {
                                    System.out.println("Username is not valid!");
                                }
                            } else {
                                System.out.println("Email is not valid!");
                            }
                            t.cancel();
                        }
                    },
                    100
            );
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Tick the User Agreement Checkbox!", ButtonType.OK);
            alert.showAndWait();
        }
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

    public static String HashPassword(String plainTextPassword) {
        String hashedPassword = DigestUtils.sha512Hex(plainTextPassword);
        return hashedPassword;
    }
}
