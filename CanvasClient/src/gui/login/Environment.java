package gui.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import networking.Client;
import networking.packets.incoming.AddConnectionPacket;

public class Environment extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Canvas Application");
        primaryStage.setScene(new Scene(root, 730, 445));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Client client = new Client("localhost",1337);
        client.connect();

        AddConnectionPacket packet = new AddConnectionPacket();
        client.sendObject(packet);

        launch(args);
    }
}

