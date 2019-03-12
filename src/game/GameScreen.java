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
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel componentPanel = new JPanel();

        this.setContentPane(componentPanel);

        componentPanel.setLayout(new BorderLayout());



    }




}
