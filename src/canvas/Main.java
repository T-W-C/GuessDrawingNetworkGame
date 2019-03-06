package canvas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Main extends Application /*implements Runnable*/ {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("canvas.fxml"));
        primaryStage.setTitle("Canvas mockup");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //WAYS OF ACHIEVING THE NETWORKING:
        //
        // METHOD 1:
        //
        // GENERATE AN IMAGE OF THE GRAPHICALCONTEXT AT REGULAR INTERVALS WHICH IS THEN CONVERTED
        // TO A BYTE STREAM AND SENT TO THE SERVER. THIS IS THEN DISTRIBUTED TO ALL CONNECTED CLIENTS

        // METHOD 2:

        // pass the messages as JSON messages instead of byte streams

        // GENERATE A COMMAND PROTOCOL FOR THE CURRENT CANVAS - I.E. ARRAYLIST OF POINT OBJECTS WHICH
        // CONTAIN THE X, Y LOCATION OF THE POINT ALONG WITH THE SIZE OF THE BRUSH...
        // THIS IS THEN SERIALIZED AND SENT OVER THE NETWORK TO THE SERVER WHICH IS THEN DISTRIBUTED
        // TO ALL OF THE CLIENTS. THE OTHER CLIENTS WILL THEN HAVE TO CALL A DRAWCANVAS METHOD...
        // INSTEAD OF SENDING OVER THE WHOLE STREAM EACH TIME - ONE COULD JUST CHECK FOR CHANGES AND
        // SEND THE NEW POINT OBJECTS THAT HAVE SINCE BEEN ADDED - TO IMPROVE EFFICIENCY...
        // WOULD HAVE TO DO CHECKS TO ARRAYLIST CHANGES IN SIZE.
        

//        try {
//            Socket socket = new Socket("localhost", 8888);
//            ObjectInputStream os = new ObjectInputStream(socket.getInputStream());
//            ByteArrayOutputStream byteOut = new ByteArrayOutputStream(socket.getOutputStream());
//
//        }

    }


    public static void main(String[] args) {
        launch(args);
    }
/*
    @Override
    public void run() {
        boolean isRunning = true;
        while(isRunning) {
            try {
                // receive the draw command
                Object o = is.readObject();
                s = (DrawCommand) o;
            } catch(Exception e) {
                isRunning = false;
                e.printStackTrace();
            }
            if(s!=null) {
                // add the draw command to the canvas
            }
        }*/
//    }
}
