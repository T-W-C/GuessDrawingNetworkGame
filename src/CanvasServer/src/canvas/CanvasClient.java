package canvas;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class CanvasClient extends Application implements Runnable /*implements Runnable*/ {

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
    private Thread t;
    private Socket s;
    private String bob = "test";
    private ObjectInputStream is;
    private ObjectOutputStream os;
//    private Thread t = new Thread(new CanvasClient());

//    private Player p;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("canvas.fxml"));

        System.out.println(Thread.currentThread());
        root = (Parent)loader.load();
        controller = loader.getController();

//        controller.setCanvasClient(this);

        primaryStage.setScene(new Scene(root));


        primaryStage.setTitle("canvas");

        primaryStage.show();

//        t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    s = new Socket("127.0.0.1", PORT);
//
//                    is = new ObjectInputStream(s.getInputStream());
//
//                    try {
//                        while(true) {
//                            //message handling usually done here for the specific packet
//                            DrawCommand response = (DrawCommand) in.readObject();
//                            if(checkBufferFull()) {
//                                drawInputBufferedCommands();
//                                drawCommandBuffer.add(response);
//                            } else {
//                                drawCommandBuffer.add(response);
//                            }
//                        }
//
//                    } catch(Exception e) {
//                        System.out.println("hmmmmm");
//                        e.printStackTrace();
//                    }
//
//                } catch (IOException e) {
//                    System.out.println("hmm");
//                     e.printStackTrace();
//                }
//            }
//        });
//        t.start();








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




    public void play() {
        try {
            while(true) {
                //message handling usually done here for the specific packet
                DrawCommand response = (DrawCommand) in.readObject();
                if(checkBufferFull()) {
                    drawInputBufferedCommands();
                    drawCommandBuffer.add(response);
                } else {
                    drawCommandBuffer.add(response);
                }
            }

        } catch(Exception e) {
            System.out.println("hmmmmm");
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

    public void drawInputBufferedCommands() {
        // draw the buffered draw commands on client
        GraphicsContext g = canvas.getGraphicsContext2D();
    System.out.println("yes it gets here ");
        drawCommandBuffer.forEach((command) -> {
            g.setFill(Color.BLACK);
            g.fillOval(command.getX(), command.getY(), command.getSize(), command.getSize());
        });

        drawCommandBuffer.clear();
    }

//    public void sendDrawCommand(DrawCommand c) {
//        try {
//        out = new ObjectOutputStream(s.getOutputStream());
//            out.writeObject(c);
//            out.flush();
////        System.out.
////        println(bob);
////            System.out.println(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


    public static void main(String[] args) {
            launch(args);
//            CanvasClient c = new CanvasClient("127.0.0.1");
//            c.play();
//        CanvasClient c = new CanvasClient();
//        c.start();


    }

    @Override
    public void run() {

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
