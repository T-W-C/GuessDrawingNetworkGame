public class Player {
    private String playerName;
    private int playerScore;
    private boolean isDrawer;

    public Player(String playerName, int playerScore, boolean isDrawer) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.isDrawer = isDrawer;
    }

    public void setIsDrawer(boolean isDrawer) {
        this.isDrawer = isDrawer;
    }

    public boolean getIsDrawer() {
        return isDrawer;
    }

}
