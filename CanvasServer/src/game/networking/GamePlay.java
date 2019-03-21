package game.networking;
import game.networking.objects.Player;
import game.networking.packets.GameServerArrayListPacket;
import game.networking.packets.SendWordPacket;
import game.networking.packets.UpdateChatDrawerPacket;
import game.networking.packets.UpdateSideBarPacket;

import java.net.Socket;
import java.util.ArrayList;

public class GamePlay {

//    private ArrayList<Socket> activeConnections = new ArrayList<>();




    private int round = 0;
    private int turn = 0;
    private boolean gameRunning;


    //defines the connected server
    private GameServer server;

    //THIS VARIABLE FOR NOW IS REDUNDANT - COULD MAYBE BE USED AT A LATER DATE
    private boolean roundActive = false;


    private String word = "bob";

    private volatile boolean hasGuessed;
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



    public String getWord() {
        return word;
    }

    public void updateWord() {
//        FETCH RANDOM WORD FROM THE DATABASE HERE AND SET IT TO WORD INSTANCE VARIABLE
    }


    public GamePlay(GameServer server) {
        this.server = server;
//        activeConnections = server.getConnectedClients();
        gameRunning = true;
    }


    //this is to be started on a separate thread within the GameServer
    public void start() {

        while(gameRunning) {
            startRound();
            return;
        }

//        endGame();
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

    public void resetDrawers() {
        for(Player player: server.getConnectedPlayers()) {
            player.setIsDrawer(false);
        }
    }



    public void startTurn() {
        resetDrawers();
        updateWord();
        server.getConnectedPlayers().get(turn).setIsDrawer(true);
        server.sendPacket(new UpdateChatDrawerPacket(server.getConnectedPlayers().get(turn)));

        GameServerArrayListPacket alp = new GameServerArrayListPacket(); // create new packet to send arraylist
        //ArrayList<Integer> bla = new ArrayList<>();
        //bla.add(1);
        //bla.add(2);
        //alp.setTestBool(true);
        //alp.setTestInts(bla);
        //server.sendPacket("sending a boolean of true and a list {1,2} from the gameplay class to the gamescreen");
        alp.setPlayers(this.server.getConnectedPlayers()); // set arraylist of packet to updated arraylist of server
        SendWordPacket wp = new SendWordPacket(); // create new packet to send new word
        wp.setWord(word); // set word of packet to be updated word of this class

        server.sendPacket("about to send a GameServerArrayListPacket from the gameplay class");
        server.sendPacket("with arraylist of size: " + alp.getPlayers().size()); // this line is for testing purposes only
        for(int i=0; i<alp.getPlayers().size(); i++) { // this line is for testing purposes only
            server.sendPacket("player" + i + " is drawer = " + alp.getPlayers().get(i).getIsDrawer() + " and player score = " + alp.getPlayers().get(i).getPlayerScore()); // this line is for testing purposes only
        } // this line is for testing purposes only

        server.sendPacket(alp); // send array list packet
        server.sendPacket("just sent an GameServerArrayListPacket packet from the gameplay class"); // this line is for testing purposes only
        server.sendPacket(wp); // send word packet
        hasGuessed = false;

        System.out.println("Player start on turn: " + (turn+1) + " now they can draw");
        //send packet to set drawer of player whose turn it is
        //send packet to update the side panel (so it indicates whose active turn it is) and updates word and timer

//        //this empty while loop executed until the player has guessed or timer is no longer active (triggered by packets)
//        while(!hasGuessed && timerActive) {
//            System.out.println("Waiting for correct guess or timer to run out...");
//        }
        //below is just testing the guess functionality above uncommented
        while(!hasGuessed) {
//            System.out.println("Waiting for correct guess or timer to run out...");
        }

        System.out.println("player has guessed correctly... ending turn");
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
        if(turn > 1) {
            System.out.println("All players have had their turn, end the round.");
            endRound();
            return;
        }
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
    public void guessedCorrect() {
        System.out.println("Player has guessed changing guess state...");
        hasGuessed = true;
    }

}
