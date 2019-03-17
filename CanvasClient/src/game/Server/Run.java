package Server;

public class Run {

    public static void main(String[] args) {
        GameServer gameServer= GameServer.getInstance().setServerIP("127.0.0.1").setPort(8888);
        gameServer.start();
    }
}
