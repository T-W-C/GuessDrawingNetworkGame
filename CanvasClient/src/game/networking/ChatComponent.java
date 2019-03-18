package game.networking;

import game.networking.objects.Player;

import javax.swing.*;
import java.awt.*;

public class ChatComponent extends JPanel {

    private JTextField userMessage;
    private JTextArea chatArea;

    private Player player;

    private ConnectionHandler connectionHandler;


    public ChatComponent(Player player) {
        super();
        this.player = player;
        setLayout(new BorderLayout());

        userMessage = new JTextField();
        userMessage.setForeground(Color.BLACK);
        userMessage.setText("Enter your message");
        userMessage.setEnabled(false);
        userMessage.setEditable(false); //not allowed before connecting to anyone
        userMessage.addActionListener(
                event ->
                {
                    sendMessage(event.getActionCommand()); //take what is in text area and send it to next person
                    userMessage.setText("");
                }
        );
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setMinimumSize(new Dimension( 120, 150));
        scrollPane.setPreferredSize(new Dimension( 120, 150));
        scrollPane.setMaximumSize(new Dimension( 120, 150));
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(userMessage, BorderLayout.SOUTH);


    }

    private void sendMessage(String message) {
        message = player.getPlayerName() + ": " + message;
        this.connectionHandler.sendPacket(message);
//        showMessage(message);
    }

    public void showMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
        });
    }

    public void start(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
        userMessage.setEnabled(true);
        userMessage.setEditable(true);
    }

}
