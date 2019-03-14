package chat;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

/**
 *  ClientMain class for creating a new user instance.
 */
public class ClientMain extends Application {

    /**
     *  Main method.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *  Overriding start method that loads up the login screen.
     */
    @Override
    public void start(Stage loginStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        loginStage.setScene(new Scene(root, 350, 180));
        loginStage.setTitle("Log in to the game");
        loginStage.show();
    }
}