package game.networking;

import game.networking.objects.Player;

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
    private String currentUserName; // the player representing the user who this instance is open for
    private static int height;
    private static int width;

    public SideBar(String username, ArrayList<Player> players, int w, int h) {
        this.currentUserName = username; // set the user field variable to be equal to the player passed through, this will be passed from the game home screen
        width = w;
        height = h;
        this.players = players;

        AdvancedBevelBorder border = new AdvancedBevelBorder(new Color(120, 172, 220), new Color(55, 93, 128),
                new Color(73, 124, 169), new Color(150, 191, 229), new Color(150, 202, 243), 10);

        this.setBorder(border);
        this.setSize(width, height);
        this.setLayout(new GridLayout(1,1));
        this.setBackground(new Color(156,202,243));
        this.add(names());
    }

    private JPanel names() {
        JPanel namesPanel = new JPanel();
        namesPanel.setSize(width, height);
        namesPanel.setBackground(new Color(156,202,243));
        namesPanel.setLayout(new GridLayout(10,1)); // change this 12 if you want to make the sizes of each username box bigger/smaller
        for(int i=0; i<players.size(); i++) {
            JPanel user = new JPanel();
            user.setBackground(new Color(156,202,243));
            user.setLayout(new GridLayout(2, 1, 10, 0));
            user.setSize(SideBar.width, SideBar.height);
            user.setBorder(new LineBorder(new Color(156,202,243), 3));

            if (players.get(i).getIsDrawer()) {
                user.setBorder(new LineBorder(Color.YELLOW, 3)); // set the border of the drawer's username box to yellow
            }
            else if (i % 2 == 0) {
                user.setBackground(new Color(156,202,243)); // every other user background should be gray
            }
            if (players.get(i).getPlayerName().equals(currentUserName)) { // if the current player in the array is the user who this instance is open for
                JLabel name = new JLabel(players.get(i).getPlayerName() + " (You)", SwingConstants.CENTER); // create a new label to represent the username
                name.setForeground(Color.BLUE);
                name.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
                user.add(name); // add the username label to the panel
            } else {
                JLabel name = new JLabel(players.get(i).getPlayerName(), SwingConstants.CENTER); // create a new label to represent the username
                name.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
                user.add(name); // add the username label to the panel
            }

            // note how the score is received using getPlayerScore, after each word, the method to update player score will have to be called
            // and then this username will need to be recalled or reconstructed so when getPlayerScore is called, the score has updated
            JLabel score = new JLabel("Score: " + players.get(i).getPlayerScore(), SwingConstants.CENTER); // create a label for the players current score
            score.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
            user.add(score);

            namesPanel.add(user); // add the username panel to the namesPanel
        }

        return namesPanel;
    }

    public static void main(String[] args) throws Exception {
        Player tommy = new Player("tommy");
        tommy.updatePlayerScore(50);
        ArrayList<Player> players1 = new ArrayList<>();
        ArrayList<Player> players2 = new ArrayList<>();
        players2.add(tommy);
        players2.add(new Player("zak"));
        players2.add(new Player("tom"));
        players2.add(new Player("bryn"));
        players2.get(1).setIsDrawer(true);
        //players.add(new Player("asgar",false));

        JFrame f = new JFrame();
        f.setSize(700,500);
        f.setVisible(true);//making the frame visible
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SideBar sb = new SideBar("tommy", players1,200, 300); // try to figure out why this width of 200 does not work
        WordBar wb = new WordBar("temp", 700, 200);

        f.add(wb, BorderLayout.PAGE_START); // top
        f.add(sb, BorderLayout.LINE_START); // left

        f.getContentPane().remove(sb);
        f.getContentPane().invalidate();
        SideBar sb2 = new SideBar("tommy", players2, 200, 300);
        f.getContentPane().add(sb2, BorderLayout.LINE_START);
        f.getContentPane().revalidate();

        f.getContentPane().remove(wb);
        f.getContentPane().invalidate();
        WordBar wb2 = new WordBar("giraffe", 700, 200);
        f.getContentPane().add(wb2, BorderLayout.PAGE_START);
        f.getContentPane().revalidate();


        JPanel pan = new JPanel();
        pan.setBackground(Color.yellow);
        pan.setSize(500, 500);
        f.add(pan);
    }
}
