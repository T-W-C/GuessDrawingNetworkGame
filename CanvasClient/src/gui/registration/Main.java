package gui.registration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import networking.Client;
import networking.packets.incoming.AddConnectionPacket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
        primaryStage.setTitle("Canvas Application");
        primaryStage.setScene(new Scene(root, 500, 500));
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
