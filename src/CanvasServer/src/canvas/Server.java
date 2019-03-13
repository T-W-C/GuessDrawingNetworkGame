package canvas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
//    private ArrayList<CanvasHandler> handlers = new ArrayList<>();
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        try {
            serverSocket = new ServerSocket(8888);

            System.out.println("jsofjsj");

            while (true) {
                Socket clientSocket = null;
                clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getLocalAddress().getHostAddress());
               // new CanvasHandler(clientSocket, handlers).start();
            }
        } catch(Exception e) {
                e.printStackTrace();
            }
//            CanvasHandler con = new CanvasHandler(clientSocket, this);
//            connectedClients.add(con);
//            con.start();

        }
    }


//    class CanvasHandler extends Thread {
//        private final Socket incoming;
//
//        private ObjectInputStream in;
//        private ObjectOutputStream out;
//
////        private Object command;
//
//        public static ArrayList<Socket> ConnectionArray = new ArrayList<>();
//        public static ArrayList<String> CurrentUsers = new ArrayList<>();
//
//        private ArrayList<CanvasHandler> handlers;
//
//        public CanvasHandler(Socket clientSocket, ArrayList<CanvasHandler> handlers ) {
//            this.incoming = clientSocket;
//            this.handlers = handlers;
//            handlers.add(this);
//        }
//
//        public synchronized void sendToAllClients(Object command) {
//            handlers.forEach((current) -> {
//                current.writeCommand(command);
//                try {
//                    current.out.writeObject(command);
//                    current.out.reset();
//                } catch(IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//
//        @Override
//        public void run() {
//            try {
//                String playerName;
//                String s;
//                in = new ObjectInputStream(incoming.getInputStream());
//                out = new ObjectOutputStream(incoming.getOutputStream());
//                ConnectionArray.add(incoming);
//
//
//                is = new ObjectInputStream(clientSocket.getInputStream());
//                os = new ObjectOutputStream(clientSocket.getOutputStream());
//                while (this.readCommand()) {
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        public boolean readCommand() {
//            try {
//                command = (DrawCommand) is.readObject();
//
//            } catch(Exception e) {
//                return false;
//            }
//            if(command == null) {
//
//            } else {
//            }
//            return true;
//        }
//
//        public void writeCommand(DrawCommand command) {
//            try {
//                os.writeObject(command);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//



//    }
