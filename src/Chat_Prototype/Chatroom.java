package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.IOException;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;


/**
 *  Class for the chatroom.
 */
public class Chatroom {

    @FXML public TextArea messageArea;
    @FXML public TextField messageField;

    public static Thread thread;
    Socket socket;
    DataOutputStream dataos;
    DataInputStream datais;

    public Chatroom() {
        try {
            socket = new Socket(Data.ip_address, Data.port);
            dataos = new DataOutputStream(socket.getOutputStream());
            datais = new DataInputStream(socket.getInputStream());
            dataos.writeUTF(Data.username);

            /**
             *  Create a thread and override 'Run'.
             */
            thread = new Thread(() -> {
                try {
                    JSONParser Jsonparser = new JSONParser();
                    /**
                     * This while loop loops continuously, waiting for new messages, and accepting any messages it gets
                     * from the data input stream. It then creates a new message and JSON object and appends the
                     * messageArea with the username and message from the new Message object.
                     */
                    while(true) {
                        String newMessageJSON = datais.readUTF();
                        Message newMessage = new Message();
                        Object object = Jsonparser.parse(newMessageJSON);
                        JSONObject message = (JSONObject) object;

                        String messageUsername = message.get("username").toString();
                        String messageMessage = message.get("message").toString();
                        newMessage.setUsername(messageUsername);
                        newMessage.setMessage(messageMessage);
                        messageArea.appendText(newMessage.getUsername() + ": " + newMessage.getMessage() + "\n");
                    }
                } catch(Exception exception) {
                    exception.printStackTrace();
                }
            });
            thread.start();

        } catch(IOException exception) {
            exception.printStackTrace();
        }

    }

    /**
     *  onButtonPress method that, when the user clicks 'send', stores the contents of the messageField in a JSONObject,
     *  converts it to a String, sends that to the data output stream, and then clears the message field.
     */
    public void onButtonPress(KeyEvent keyEvent) {
        if(keyEvent.getCode().toString().equals("ENTER"))
        {
            try {
                String msg = messageField.getText();
                JSONObject JObject = new JSONObject();
                JObject.put("username", Data.username);
                JObject.put("message", msg);
                String message = JObject.toString();
                dataos.writeUTF(message);
                messageField.clear();

            } catch(IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}