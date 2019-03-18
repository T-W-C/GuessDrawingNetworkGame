package game.networking;
import java.net.Socket;
import java.util.ArrayList;

public class GamePlay {

    private ArrayList<Socket> activeConnections = new ArrayList<>();

    private int round = 0;
    private int turn = 0;
    private boolean gameRunning;

    //store the drawer as a socket? for now at least
    private Socket drawer;

    //defines the connected server
    private GameServer server;

    //THIS VARIABLE FOR NOW IS REDUNDANT - COULD MAYBE BE USED AT A LATER DATE
    private boolean roundActive = false;

    private String word;

    private boolean hasGuessed;
    private boolean timerActive;

    //get each socket, send a packet to each socket to determine whether they are a drawer or not
    //  implement a drawer boolean value within the canvascomponent? to enable whoever is a drawer to send events to canvas and tool component

    /*
    initialise players
        initialise player scores
        start round - int variable that is incremented?

    MAKE A COMPARATOR WHICH RETURNS THE INFORMATION TO BE DRAWN IN THE SIDEBAR IN THE ORDER AT WHICH THE PLAYERS SCORES ARE
    I.E. RANK 1 IS AT THE TOP OF THE SIDEBAR????????????????

     */

    public static void main(String[] args) {
        GamePlay gp = new GamePlay();
        gp.start();
    }

    public GamePlay( ) {
        this.server = server;
        activeConnections = server.getConnectedClients();
        gameRunning = true;
    }


    //this is to be started on a separate thread within the GameServer
    public void start() {

        while(gameRunning) {
            startRound();
            return;
        }

        endGame();
        return;
    }


    public void startRound() {
        if(!gameRunning) {
            return;
        }
        //initialise the turn to be 0
        turn = 0;
        //if the round is greater than 3 then end the game
        if(round > 2) {
            gameRunning = false;
            endGame();
            return;
        }

        System.out.println("round " + (round+1) + " has started");

        // round is thus active
        roundActive = true;

        //send packet to notify of the round number - dialog box maybe? or timed text field
        //start the turn if the round is active

        //new thread may not be needed...
        startTurn();

        round++;

        startRound();
    }

    public void endRound() {
        round++;
        roundActive = false;
        startRound();
    }



    public void startTurn() {
        if(turn > 3) {
            System.out.println("All players have had their turn, end the round.");
            endRound();
            return;
        }

        System.out.println("Player start on turn: " + (turn+1) + " now they can draw");
        //send packet to set drawer of player whose turn it is
        //send packet to update the side panel (so it indicates whose active turn it is) and updates word and timer

        //this empty while loop executed until the player has guessed or timer is no longer active (triggered by packets)
        while(!hasGuessed && timerActive) {
            System.out.println("Waiting for correct guess or timer to run out...");
        }


        endTurn();
        return;
    }

    /**
     * Updates whether the timer is still going for that particular turn.
     * @param timerActive
     */
    public void updateTimerActivity(boolean timerActive) {
        this.timerActive = timerActive;
    }


    /**
     *
     */
    public void endTurn() {
        System.out.println("Player turn: " + (turn+1) + " has ended");
        turn++;
        startTurn();
        return;
    }


    /**
     *
     */
    public void endGame() {
        System.out.println("Game has ended... had " + round + " rounds.");
        return;
        //get player with highest score
        //send end game (game event? ) packet that draws a new JPanel on the screen with a button to return to main screen
        //close the socket connections between all clients and that game server
        //update the match database
    }



    //may not be needed if its handled client sided
    public void handleGuess(String guess) {
        if(guess.equals(word)) {
            //send boolean that the player has guessed correctly
            //this boolean value will then be interpreted by the server and a winner packet distributed
        }
    }

}
