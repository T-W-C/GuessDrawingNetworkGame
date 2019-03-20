package database.domain;

public class PlayerDomain {
    /**
     * The ID of the PlayerDomain, this variable will be unique and cannot be modified
     */
    private int playerID;

    /**
     * The username of the PlayerDomain, this can be used for signing into the game
     */
    private String username;

    /**
     * The password of the user, they will not be able to get this variable normally, as it contains secure information
     */
    private String password;

    private String email;

    /**
     * This variable will store the Level of the user
     */
    private int level;

    /**
     * The accumulative score from all the matches combined
     */
    private int totalScore;

    /**
     * The constructor to create an instance to set the data
     * @param playerID to set
     * @param username to set
     * @param level to set
     * @param totalScore to set
     */
    public PlayerDomain(int playerID, String username, String email, int level, int totalScore) {
        this.playerID = playerID;
        this.username = username;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
