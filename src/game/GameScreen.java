import javax.swing.*;
import java.awt.*;

public class GameScreen extends JFrame {

    private static GameScreen ref = null;

    /**
     *  Swing data structures not thread safe - launch on the event dispatching
     *  thread through invoke later.
     * @param args
     */
    public static void main(String[] args) {
//        Runnable gameRunnable = () -> {
//            GameScreen gameScreen = GameScreen.getRef();
//            gameScreen.setVisible(true);
//        };
        SwingUtilities.invokeLater(() -> {
                GameScreen gameScreen = GameScreen.getRef();
                gameScreen.setVisible(true);
        });
    }

    public static GameScreen getRef() {
        if(GameScreen.ref == null) {
            GameScreen.ref = new GameScreen();
        }
        return GameScreen.ref;
    }

    public GameScreen() {
        // set size and properties of the main game frame:
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // creates main component panel for the game frame
        JPanel componentPanel = new JPanel();
        setContentPane(componentPanel);

        componentPanel.setLayout(new BorderLayout());

        // create the main menu component
        drawNewScreenComponents(createMainMenu());



    }


    public void drawNewScreenComponents(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        revalidate();
        //calls the paint Component
        repaint();
    }

    public GameMenu createMainMenu() {
        return new GameMenu();
    }




}
