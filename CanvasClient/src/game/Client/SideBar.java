package game.client;

import game.client.objects.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * this is to contain the players information who is in a game
 * it is to be passed the socket of a game...
 * all clients are to be updated to keep up to date with who joined the game and should be highlighted which player is drawing
 *
 */

public class SideBar extends JPanel {
    // either store them in side bar or fetch from game instance class
    // array of players to retrieve their names + see if they are drawing or not
    // assume array has all 4 players as game cannot start until there are 4 players
    public ArrayList<Player> players;
    public int playerSize = 4; // change this to work with more than 4 players
    private Player currentUser; // the player representing the user who this instance is open for
    private static int height;
    private static int width;

    public SideBar(Player user, ArrayList<Player> players, int w, int h) {

        this.currentUser = user; // set the user field variable to be equal to the player passed through, this will be passed from the game home screen
        this.players = players;
        width = w;
        height = h;

        this.setSize(width, height);
        this.setLayout(new GridLayout(1,1));
        this.add(names());
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        //this.revalidate();
        this.repaint(); // <-- do one of these
    }

    private JPanel names() {
        JPanel namesPanel = new JPanel();
        namesPanel.setSize(width, height);
        namesPanel.setLayout(new GridLayout(12,1));

        for(int i=0; i<players.size(); i++) {
                JPanel user = new JPanel();
                user.setLayout(new GridLayout(2, 1));
                user.setSize(SideBar.width, SideBar.height);
                user.setBorder(new LineBorder(Color.GRAY));

                if (players.get(i).getIsDrawer()) {
                    user.setBorder(new LineBorder(Color.YELLOW)); // set the border of the drawer's username box to yellow
                }

                if(players.get(i).getGuessedWordCorrectly()) { // if the player has correctly guessed the word then make their background green
                    user.setBackground(Color.green);
                }
                else if (i % 2 == 0) {
                    user.setBackground(Color.GRAY); // every other user background should be gray
                }

                if (players.get(i).equals(currentUser)) { // if the current player in the array is the user who this instance is open for
                    JLabel name = new JLabel(players.get(i).getPlayerName() + " (You)", SwingConstants.CENTER); // create a new label to represent the username
                    name.setForeground(Color.BLUE);
                    name.setFont(new Font("SansSerif", 1, 15));
                    user.add(name); // add the username label to the panel
                } else {
                    JLabel name = new JLabel(players.get(i).getPlayerName(), SwingConstants.CENTER); // create a new label to represent the username
                    name.setFont(new Font("SansSerif", 1, 15));
                    user.add(name); // add the username label to the panel
                }

                // note how the score is received using getPlayerScore, after each word, the method to update player score will have to be called
                // and then this username will need to be recalled or reconstructed so when getPlayerScore is called, the score has updated
                JLabel score = new JLabel("Score: " + players.get(i).getPlayerScore(), SwingConstants.CENTER); // create a label for the players current score
                score.setFont(new Font("SansSerif", 1, 15));
                user.add(score);

                namesPanel.add(user); // add the username panel to the namesPanel
            }

        return namesPanel;
    }

    public static void main(String[] args) throws Exception {
        Player tommy = new Player("tommy", false);
        tommy.updatePlayerScore(50);
        ArrayList<Player> players = new ArrayList<>();
        players.add(tommy);
        players.add(new Player("zak", false));
        players.add(new Player("tom", true));
        players.add(new Player("bryn", false));
        players.get(0).setGuessedWordCorrectly(true);
        //players.add(new Player("asgar",false));

        JFrame f = new JFrame();
        f.setSize(700,500);
        f.setVisible(true);//making the frame visible
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SideBar sb = new SideBar(tommy, players, 200, 300); // try to figure out why this width of 200 does not work
        WordBar wb = new WordBar("giraffe", 700, 200);

        f.add(wb, BorderLayout.PAGE_START); // top
        f.add(sb, BorderLayout.LINE_START); // left

        JPanel pan = new JPanel();
        pan.setBackground(Color.yellow);
        pan.setSize(500, 500);
        f.add(pan);
    }
}
