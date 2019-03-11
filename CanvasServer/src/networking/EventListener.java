package networking;

import networking.helper.ClassMatchCache;
import networking.packets.incoming.AddConnectionPacket;
import networking.packets.incoming.CheckEmailPacket;
import networking.packets.incoming.CheckUsernamePacket;
import networking.packets.incoming.RemoveConnectionPacket;
import networking.packets.outgoing.SendEmailCheckResult;
import networking.packets.outgoing.SendUsernameCheckResult;

import static networking.helper.ClassMatcher.match;

public class EventListener {

	private ClassMatchCache mainDispatcher = new ClassMatchCache();
	private Connection connection;

	public void received(Object packet, Connection connection) {
		this.connection = connection;
		mainDispatcher.cache(
				() -> match().with(AddConnectionPacket.class, this::handleAddConnection)
						.with(RemoveConnectionPacket.class, this::handleRemoveConnection)
						.with(CheckUsernamePacket.class, this::handleCheckUsername)
						.with(CheckEmailPacket.class, this::handleCheckEmail)
						.fallthrough(this::fallthrough))
				.exec(packet);
	}

	private void fallthrough(Object o) {
		System.out.println("Can't find packet " + o.toString());
	}

	private void handleAddConnection(AddConnectionPacket p) {
		AddConnectionPacket packet = p;
		packet.playerID = connection.id;
		for (int i = 0; i < ConnectionHandler.connections.size(); i++) {
			Connection c = ConnectionHandler.connections.get(i);
			if (c != connection) {
				c.sendObject(packet);
			}
		}
	}

	private void handleRemoveConnection(RemoveConnectionPacket p) {
		RemoveConnectionPacket packet = p;
		System.out.println("Connection: " + packet.playerID + " has disconnected");
		ConnectionHandler.connections.remove(packet.playerID);
	}

	private void handleCheckUsername(CheckUsernamePacket p) {
		System.out.println("Got Packet to Check Username....  " + p.username);
		SendUsernameCheckResult response = new SendUsernameCheckResult(p.playerID);
		response.result = RegistrationHandler.isValidUsername(p.username);
		System.out.println("Sending Result... " + response.result);
		// Send back result
		connection.sendObject(response);
	}

	private void handleCheckEmail(CheckEmailPacket p) {
		System.out.println("Got Packet to Check Email....  " + p.email);
		SendEmailCheckResult response = new SendEmailCheckResult(p.playerSession);
		response.result = RegistrationHandler.isValidEmail(p.email);
		System.out.println("Sending Result... " + response.result);
		// Send back result
		connection.sendObject(response);
	}
}