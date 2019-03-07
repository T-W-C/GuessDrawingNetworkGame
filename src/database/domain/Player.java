package database.domain;

public class Player {
    /**
     * The ID of the Player, this variable will be unique and cannot be modified
     */
    public int playerID;

    /**
     * The username of the Player, this can be used for signing into the game
     */
    public String username;

    /**
     * The password of the user, they will not be able to get this variable normally, as it contains secure information
     */
    public String password;

    /**
     * This variable will store the Level of the user
     */
    public int level;

    /**
     * The accumulative score from all the matches combined
     */
    public int totalScore;

    /**
     * The constructor to create an instance to set the data
     * @param playerID to set
     * @param username to set
     * @param level to set
     * @param totalScore to set
     */
    public Player(int playerID, String username, int level, int totalScore) {
        this.playerID = playerID;
        this.username = username;
        this.level = level;
        this.totalScore = totalScore;
    }

    public int getPlayerID(){
        return playerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
