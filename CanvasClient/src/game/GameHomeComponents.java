import javax.swing.*;
import java.awt.*;

/**
 * contains components of the game screen
 */
public class GameHomeComponents extends JPanel {

    public GameHomeComponents() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel centredComponents = new JPanel();
        centredComponents.setLayout(new FlowLayout());


        JLabel title = new JLabel("Canvas.IO");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton joinGame = new JButton("Join Game");

        JButton createGame = new JButton("Create Game");

        JButton privateGame = new JButton("Join Private Game");

        centredComponents.add(joinGame);
        centredComponents.add(createGame);
        centredComponents.add(privateGame);

        add(title, BorderLayout.NORTH);
        add(centredComponents, BorderLayout.CENTER);


        // if user clicks on join game button - launch join game logic
        joinGame.addActionListener((e) -> {
            remove(centredComponents);
            remove(title);
            GameHomeScreen.getRef().joinExistingGame(new Player("tr", 23, false));
            revalidate();
            repaint();
        });

        // if user clicks on create game button - launch create game logic
        createGame.addActionListener((e) -> {
//            remove(centredComponents);
//            remove(title);
//            add(createGame, BorderLayout.CENTER);
            GameHomeScreen.getRef().createNewGame(new Player("fjoi", 2903, true));

            revalidate();
            repaint();
        });

        // if user clicks on private game button - launch private game screen
        privateGame.addActionListener((e) -> {
            remove(centredComponents);
            remove(title);
            this.add(privateGame, BorderLayout.CENTER);
            revalidate();
            repaint();
        });





    }

}
