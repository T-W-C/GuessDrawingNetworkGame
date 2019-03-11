package networking;

import gui.registration.RegistrationHandler;
import networking.helper.ClassMatchCache;
import networking.packets.incoming.AddConnectionPacket;
import networking.packets.incoming.RemoveConnectionPacket;
import networking.packets.outgoing.SendUsernameCheckResult;

import static networking.helper.ClassMatcher.match;

public class EventListener {

	private ClassMatchCache mainDispatcher = new ClassMatchCache();

	public void received(Object packet) {
		mainDispatcher.cache(
				() -> match().with(AddConnectionPacket.class, this::handleAddConnection)
						.with(RemoveConnectionPacket.class, this::handleRemoveConnection)
						.with(SendUsernameCheckResult.class, this::handleSendUsernameCheckResult)
						.fallthrough(this::fallthrough))
				.exec(packet);
	}

	private void fallthrough(Object o) {
	}

	private void handleAddConnection(AddConnectionPacket p) {
		AddConnectionPacket packet = p;
		ConnectionHandler.connections.put(packet.playerSession, new Connection(packet.playerSession));
		CurrentSession.id = packet.playerSession;
		System.out.println(packet.playerSession + " has connected");
	}

	private void handleRemoveConnection(RemoveConnectionPacket p) {
		RemoveConnectionPacket packet = p;
		System.out.println("Connection: " + packet.playerSession + " has disconnected");
		ConnectionHandler.connections.remove(packet.playerSession);
	}

	private void handleSendUsernameCheckResult(SendUsernameCheckResult p) {
		// Push Result to RegistrationHandler
		System.out.println("Got Result "+ p.result);
	}
}
