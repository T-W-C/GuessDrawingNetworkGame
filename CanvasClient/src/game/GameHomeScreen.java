import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameHomeScreen extends JFrame {

    private static GameHomeScreen ref = null;
    //array list of active ports
    private ArrayList<Integer> activePorts = new ArrayList<>();
    private static final String SERVER_ADDRESS = "127.0.0.1";



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
                GameHomeScreen gameScreen = GameHomeScreen.getRef();
                gameScreen.setVisible(true);
        });
    }

    public static GameHomeScreen getRef() {
        if(GameHomeScreen.ref == null) {
            GameHomeScreen.ref = new GameHomeScreen();
        }
        return GameHomeScreen.ref;
    }

    public GameHomeScreen() {
        // set size and properties of the main game frame:
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // creates main component panel for the game frame
        JPanel componentPanel = new JPanel();
        this.setContentPane(componentPanel);

        componentPanel.setLayout(new BorderLayout());



        // create the main menu component
        this.drawNewScreenComponents(createMainMenu());

    }


    /**
     * Responsible for redrawing the new screen - remove all components then
     * redraw the new Panel passed in as an argument
     * @param panel
     */
    public void drawNewScreenComponents(JPanel panel) {
        getContentPane().removeAll();
//        CircleButton cBtn = new CircleButton(Color.YELLOW);
//        panel.add(cBtn);
//
//        cBtn.addActionListener((e) -> {
//            System.out.println("pressed the button ennit");
//        });
        getContentPane().add(panel, BorderLayout.CENTER);
        revalidate();
        //calls the paint Component
        repaint();
    }

    /**
     * creates and returns a new instance of the GameHomeComponents
     * @return
     */
    public GameHomeComponents createMainMenu() {
        return new GameHomeComponents();
    }

    public void createNewGame(Player p) {
        GameScreen gameInstance = new GameScreen(p);
        this.drawNewScreenComponents(gameInstance);
        gameInstance.initialiseGame(SERVER_ADDRESS, randomInactivePort());
    }

    public void joinExistingGame(Player p) {
        GameScreen gameInstance = new GameScreen(p);
        this.drawNewScreenComponents(gameInstance);
        int port = getRandomActivePort();
        gameInstance.joinExistingGame(SERVER_ADDRESS, port);

    }

    public int randomInactivePort() {
        Random random = new Random();
        int randomNum = random.nextInt(1000);
        if(!activePorts.contains(randomNum)) {
            return randomNum;
        } else {
            return randomInactivePort();
        }
    }

    public int getRandomActivePort() {
        return 8987;
    }





}
