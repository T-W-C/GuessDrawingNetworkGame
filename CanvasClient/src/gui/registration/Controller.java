package gui.registration;

import gui.activation.ActivationParent;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
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
            Task<Void> sleeper = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        /* Packet Sends for Checks */
                        registrationHandler.SendEmailPacket(emailText.getText());
                        registrationHandler.SendUsernamePacket(usernameText.getText());
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(event -> {
                // Check Email
                if (registrationHandler.emailResult) {
                    // Username Passed
                    if (registrationHandler.userResult) {
                        System.out.println("It worked");
                        String hashedPassword = HashPassword(passwordText.getText());
                        // Check Passwords Match
                        if(!passwordText.getText().isEmpty()){
                            if (hashedPassword.equals(HashPassword(verifyPasswordText.getText()))) {
                                // Done, create user now
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations your user " + usernameText.getText() + "", ButtonType.OK);
                                registrationHandler.SendCreateAccountPacket(emailText.getText(), usernameText.getText(), hashedPassword);
                                alert.showAndWait();
                                System.out.println("Created Account for username " + usernameText.getText());
                                Main.getPrimaryStage().setScene(new Scene(ActivationParent.getInstance().getParent(), 590, 360));
                                /* Send Activation Email */
                                registrationHandler.SendEmailActivationPacket(emailText.getText());
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords do not match!", ButtonType.OK);
                                alert.showAndWait();
                                System.out.println("Passwords don't match!");
                            }
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Password cannot be empty!", ButtonType.OK);
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Username is either invalid or in use!", ButtonType.OK);
                        alert.showAndWait();
                        System.out.println("Username is not valid!");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Email is either invalid or in use!", ButtonType.OK);
                    alert.showAndWait();
                    System.out.println("Email is not valid!");
                }
            });
            new Thread(sleeper).start();
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
