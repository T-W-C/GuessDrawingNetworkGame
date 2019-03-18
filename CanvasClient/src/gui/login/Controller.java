package gui.login;

import chat.Login;
import gui.activation.ActivationParent;
import gui.registration.RegistrationParent;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import networking.Connection;
import networking.ConnectionHandler;

public class Controller {

    @FXML
    public TextField usernameText;
    @FXML
    public PasswordField passwordText;

    @FXML
    public void loginAction(){
        LoginHandler loginHandler = new LoginHandler();

        /* The purpose of Task is to give time for us to Send the Packets and get the results back from the server before checking data*/
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    /* Send all Packets to server for Login */
                    loginHandler.SendLoginUsernameCheck(usernameText.getText());
                    loginHandler.SendCheckPasswordHashCheck(usernameText.getText(), passwordText.getText());
                    // Enforced delay to wait for results
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        // Actions to complete after delay
        sleeper.setOnSucceeded(event -> {
            // Username exists
            if (LoginHandler.userExisting){
                // Check if password Matches
                // Password Matches
                if (LoginHandler.passwordMatches){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Logged in!", ButtonType.OK);
                    alert.showAndWait();                }
                // Incorrect Password
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Password entered does not exist!", ButtonType.OK);
                    alert.showAndWait();
                }
            }
            // Username doesn't exist
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username entered does not exist!", ButtonType.OK);
                alert.showAndWait();
            }

        });
        new Thread(sleeper).start();
    }

    public void registrationButton(){
        Main.getPrimaryStage().setScene(RegistrationParent.getInstance().getScene());
    }
}
