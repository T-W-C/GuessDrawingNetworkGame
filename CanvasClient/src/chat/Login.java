package chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Class for the login.
 */
public class Login {
    @FXML public TextField username;

    /**
     * onLoginPress method. If a user preses 'enter' whilst in the 'port' textField, 'onLoginClick' (defined
     * below) is executed.
     */
    public void onLoginPress(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            try {
                onLoginClick();
            } catch (Exception exception1) {
                exception1.printStackTrace();
            }
        }
    }

    /**
     * onLoginClick method that, once the user has been created and logged in, opens a new Chatroom scene.
     */
    public void onLoginClick() throws Exception {
        Data.username = username.getText();

        /**
         *  Create a new stage for the chatroom itself, and show it.
         */
        Stage stage = (Stage) username.getScene().getWindow();
        Scene chatroomScene = new Scene(FXMLLoader.load(Login.class.getResource("Chatroom.fxml")), 350, 180);
        stage.setScene(chatroomScene);
        stage.setTitle("Guess the word!");
        stage.show();
    }
}