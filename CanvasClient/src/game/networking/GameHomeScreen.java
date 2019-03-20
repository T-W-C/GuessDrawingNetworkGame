package game.networking;

import game.networking.objects.Player;
import javafx.scene.Parent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GameHomeScreen extends JFrame {

    private static GameHomeScreen ref = null;
    // array list of active ports
    private ArrayList<Integer> activePorts = new ArrayList<>();
    private static final String SERVER_ADDRESS = "127.0.0.1";

    public static Player currentPlayer = null;

    /**
     * Swing data structures not thread safe - launch on the event dispatching
     * thread through invoke later.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Runnable gameRunnable = () -> {
        // networking.GameScreen gameScreen = networking.GameScreen.getRef();
        // gameScreen.setVisible(true);
        // };
        SwingUtilities.invokeLater(() -> {
            GameHomeScreen gameScreen = GameHomeScreen.getRef();
            gameScreen.setVisible(true);
        });
    }

    public static GameHomeScreen getRef() {
        if (GameHomeScreen.ref == null) {
            GameHomeScreen.ref = new GameHomeScreen();
        }
        return GameHomeScreen.ref;
    }

    public GameHomeScreen() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }

        // set size and properties of the main game frame:
        this.setSize(1000, 750);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // creates main component panel for the game frame
        JPanel componentPanel = new JPanel();
        this.setContentPane(componentPanel);

        componentPanel.setLayout(new BorderLayout());

        // create the main menu component
        this.drawNewScreenComponents(createMainMenu());

        // JButton btn = new JButton("Ok");
        // setSize(400, 300);
        // setLayout(new FlowLayout());
        // getContentPane().add(btn);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);

        // btn.addActionListener(new ActionListener() {
        //
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // final JDialog dialog = new GameNotifications(GameHomeScreen.this, true,
        // "Hello javaknowledge!");
        // Timer timer = new Timer(GameNotifications.LENGTH_LONG, new ActionListener() {
        //
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // dialog.setVisible(false);
        // dialog.dispose();
        // }
        // });
        // timer.setRepeats(false);
        // timer.start();
        //
        // dialog.setVisible(true); // if modal, application will pause here
        // }
        // });
        // this.add(btn);

    }

    /**
     * Responsible for redrawing the new screen - remove all components then redraw
     * the new Panel passed in as an argument
     * 
     * @param panel
     */
    public void drawNewScreenComponents(JPanel panel) {
        getContentPane().removeAll();
        // networking.CircleButton cBtn = new networking.CircleButton(Color.YELLOW);
        // panel.add(cBtn);
        //
        // cBtn.addActionListener((e) -> {
        // System.out.println("pressed the button ennit");
        // });
        getContentPane().add(panel, BorderLayout.CENTER);
        revalidate();
        // calls the paint Component
        repaint();
    }

    /**
     * creates and returns a new instance of the networking.GameHomeComponents
     * 
     * @return
     */
    public GameHomeComponents createMainMenu() {
        return new GameHomeComponents();
    }

    public void joinGame(Player p) {
        try {
            GameScreen gameInstance = new GameScreen(p);
            this.drawNewScreenComponents(gameInstance);
            gameInstance.joinGame(SERVER_ADDRESS);
        } catch (Exception e) {

        }
    }
}
