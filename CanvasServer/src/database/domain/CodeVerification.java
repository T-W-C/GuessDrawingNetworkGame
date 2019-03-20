package database.domain;

public class CodeVerification {
    private PlayerDomain playerDomain;
    private int activationCode;
    private boolean codeUsed;

    public CodeVerification(PlayerDomain playerDomain, int activationCode, boolean codeUsed) {
        this.playerDomain = playerDomain;
        this.activationCode = activationCode;
        this.codeUsed = codeUsed;
    }

    public PlayerDomain getPlayerDomain() {
        return playerDomain;
    }

    public void setPlayerDomain(PlayerDomain playerDomain) {
        this.playerDomain = playerDomain;
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
