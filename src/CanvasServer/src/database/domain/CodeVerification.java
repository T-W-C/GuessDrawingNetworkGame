package database.domain;

public class CodeVerification {
    private Player player;
    private int activationCode;
    private boolean codeUsed;

    public CodeVerification(Player player, int activationCode, boolean codeUsed) {
        this.player = player;
        this.activationCode = activationCode;
        this.codeUsed = codeUsed;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(int activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isCodeUsed() {
        return codeUsed;
    }

    public void setCodeUsed(boolean codeUsed) {
        this.codeUsed = codeUsed;
    }
}
