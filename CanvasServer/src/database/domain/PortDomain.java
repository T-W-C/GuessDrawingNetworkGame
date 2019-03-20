package database.domain;

public class PortDomain {
    private int port;

    private boolean isActive;

    public PortDomain(int port, boolean isActive) {
        this.port = port;
        this.isActive = isActive;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
