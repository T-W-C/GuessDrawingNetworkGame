package game.networking;

import game.networking.objects.Player;
import javafx.concurrent.Task;

import javax.swing.*;
import java.awt.*;

/**
 * contains components of the game screen
 */
public class GameHomeComponents extends JPanel {

    public GameHomeComponents() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setBackground(new Color(42,54,63));
        JPanel centredComponents = new JPanel();
        centredComponents.setLayout(new BoxLayout(centredComponents, BoxLayout.Y_AXIS));

        centredComponents.setBackground(new Color(42,54,63));
        JLabel title = new JLabel("Canvas.IO");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton joinGame = new JButton("JOIN GAME");

        JButton createGame = new JButton("CREATE GAME");

        Color btnBackground = new Color(91,163,125);

        joinGame.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        joinGame.setMinimumSize(new Dimension(200, 75));
        joinGame.setPreferredSize(new Dimension(200, 75));
        joinGame.setMaximumSize(new Dimension(200, 75));
        joinGame.setBackground(btnBackground);
        joinGame.setForeground(Color.WHITE);

        createGame.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        createGame.setMinimumSize(new Dimension(200, 75));
        createGame.setPreferredSize(new Dimension(200, 75));
        createGame.setMaximumSize(new Dimension(200, 75));
        createGame.setBackground(btnBackground);
        createGame.setForeground(Color.WHITE);

        title.setFont(new Font("Trebuchet MS", Font.PLAIN, 54));
        title.setForeground(Color.WHITE);

        joinGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        createGame.setAlignmentX(Component.CENTER_ALIGNMENT);


        centredComponents.add(Box.createRigidArea(new Dimension(100,200)));
        centredComponents.add(joinGame);
        centredComponents.add(Box.createRigidArea(new Dimension(100,100)));
        centredComponents.add(createGame);

        add(title, BorderLayout.NORTH);
        add(centredComponents, BorderLayout.CENTER);


        // if user clicks on join game button - launch join game logic
        joinGame.addActionListener((e) -> {
                    remove(centredComponents);
                    remove(title);
                    GameHomeScreen.getRef().joinGame(GameHomeScreen.currentPlayer);
                    revalidate();
                    repaint();
                });

        // if user clicks on create game button - launch create game logic
        createGame.addActionListener((e) -> {
//            remove(centredComponents);
//            remove(title);
//            add(createGame, BorderLayout.CENTER);
            //GameHomeScreen.getRef().createNewGame(new Player("fjoi", true));

            revalidate();
            repaint();
        });

        // if user clicks on private game button - launch private game screen
    }
}
