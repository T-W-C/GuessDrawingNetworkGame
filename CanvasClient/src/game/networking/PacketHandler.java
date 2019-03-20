package game.networking;

public interface PacketHandler {

    public void handlePacket(Object packet) throws Exception;
}
