package canvas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class CanvasClient extends Application /*implements Runnable*/ {

    private static int PORT = 8888;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ArrayList<DrawCommand> drawCommandBuffer = new ArrayList<>();
    private Parent root;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Scene scene;
    private Controller controller;
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("canvas.fxml"));
        rootLayout = (BorderPane) loader.load();

        root = FXMLLoader.load(getClass().getResource("canvas.fxml"));
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Canvas mockup");
        scene = new Scene(root);
        primaryStage.setScene(scene);

        controller = loader.getController();

        canvas = controller.getCanvas();

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

    public CanvasClient(String serverAddress) {
        try {
            Socket s = new Socket(serverAddress, PORT);

            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());

            play();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void play() {
        try {
            while(true) {
                //message handling usually done here for the specific packet
                DrawCommand response = (DrawCommand) in.readObject();
                if(checkBufferFull()) {
                    drawBufferedCommands();
                    drawCommandBuffer.clear();
                    drawCommandBuffer.add(response);
                } else {
                    drawCommandBuffer.add(response);
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkBufferFull() {
        if(drawCommandBuffer.size() == 50) {
            return true;
        } else {
            return false;
        }
    }

    public void drawBufferedCommands() {
        // draw the buffered draw commands on client
        GraphicsContext g = canvas.getGraphicsContext2D();

        drawCommandBuffer.forEach((command) -> {
            g.setFill(controller.getBrushColor());
            g.fillOval(command.getX(), command.getY(), command.getSize(), command.getSize());
        });
    }


    public static void main(String[] args) {
        launch(args);
        CanvasClient c = new CanvasClient("127.0.0.1");

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
