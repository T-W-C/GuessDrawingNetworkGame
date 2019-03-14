package chat;

import java.io.DataOutputStream;
import java.io.DataInputStream;

public class ChatClient {
    String username;
    String message;
    private DataOutputStream dataos;
    private DataInputStream datais;

    ChatClient(String username, DataOutputStream dataos, DataInputStream datais) {
        this.username = username;
        this.dataos = dataos;
        this.datais = datais;

        new Thread(() -> { //
            try {
                /**
                 * This while loop loops continuously, waiting for user input, and once the user has input a message,
                 * the content is stored in the message variable. It gets the list of chat clients from the server,
                 * and then sends the message to each of these clients, and writes it to the output stream of each client.
                 */
                while(true) {
                    message = datais.readUTF();
                    ChatServer.ChatClients.forEach((client) -> {
                        DataOutputStream dataout = client.getDataos();
                        try {
                            dataout.writeUTF(message);
                        } catch (Exception exception1) {
                            exception1.printStackTrace();
                        }
                    });
                }

                /**
                 * Catch statement for closing the input and output streams.
                 */
            } catch (Exception exception) {
                try {
                    dataos.close();
                    datais.close();
                } catch(Exception exception2) {
                    exception2.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Getter for dataos.
     */
    public DataOutputStream getDataos() {
        return dataos;
    }

}