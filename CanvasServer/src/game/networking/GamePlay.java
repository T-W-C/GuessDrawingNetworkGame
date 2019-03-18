package game.networking;

import java.net.Socket;
import java.util.ArrayList;

public class GamePlay {

    private ArrayList<Socket> playersInGame = new ArrayList<>();

    private int round = 0;
    private int turn = 0;
    private boolean gameRunning;

    //store the drawer as a socket? for now at least
    private Socket drawer;

    //defines the connected server
    private GameServer server;

    private String word;


    //get each socket, send a packet to each socket to determine whether they are a drawer or not
    //  implement a drawer boolean value within the canvascomponent? to enable whoever is a drawer to send events to canvas and tool component




    /*
    initialise players
        initialise player scores
        start round - int variable that is incremented?



     */


    public GamePlay(GameServer server) {
        this.server = server;
        playersInGame = server.getConnectedClients();
        gameRunning = true;
    }


    //this is to be started on a separate thread within the GameServer
    public void start() {

        while(gameRunning) {
            startRound();


        }
    }


    public void startRound() {
        round++;
        if(round > 3) {
            endGame();
        }
    }

    public void endRound() {

    }



    public void startTurn() {

    }


    public void endTurn() {

    }

    public void endGame() {

    }




    public boolean handleGuess(String guess) {
        if(guess.equals(word)) {
            //send boolean that the player has guessed correctly
            //this boolean value will then be interpreted by the server and a winner packet distributed
        }
    }




}
