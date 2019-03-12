package networking;

import database.dao.PlayerDAO;
import networking.helper.ClassMatchCache;
import networking.packets.incoming.*;
import networking.packets.outgoing.SendEmailCheckResult;
import networking.packets.outgoing.SendPasswordHashConfirmation;
import networking.packets.outgoing.SendPasswordHashRequest;
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
						.with(CheckPasswordHash.class, this::handleCheckPasswordHash)
						.with(CheckPasswordHashConfirmation.class, this::handleCheckPasswordHashConfirmation)
						.with(CheckCreateAccountPacket.class, this::handleCheckCreateAccountPacket)
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
		SendUsernameCheckResult response = new SendUsernameCheckResult(p.playerSession);
		response.result = RegistrationHandler.isValidUsername(p.username.toLowerCase());
		System.out.println("Sending Result... " + response.result);
		// Send back result
		connection.sendObject(response);
	}

	private void handleCheckEmail(CheckEmailPacket p) {
		System.out.println("Got Packet to Check Email....  " + p.email);
		SendEmailCheckResult response = new SendEmailCheckResult(p.playerSession);
		response.result = RegistrationHandler.isValidEmail(p.email.toLowerCase());
		System.out.println("Sending Result... " + response.result);
		// Send back result
		connection.sendObject(response);
	}

	private void handleCheckPasswordHash(CheckPasswordHash p) {
		System.out.println("Got Packet to Check Password....  " + p.password);
		SendPasswordHashRequest response = new SendPasswordHashRequest(p.playerSession);
		response.password = RegistrationHandler.computeHashOfPassword(p.password);
		System.out.println("Sending Result... " + response.password);
		// Send back result
		connection.sendObject(response);
	}

	private void handleCheckPasswordHashConfirmation(CheckPasswordHashConfirmation p) {
		System.out.println("Got Packet to Check Password....  " + p.password);
		SendPasswordHashConfirmation response = new SendPasswordHashConfirmation(p.playerSession);
		response.passwordResult = RegistrationHandler.confirmHashOfPassword(p.password, p.passwordHash);
		System.out.println("Sending Result... " + response.passwordResult);
		// Send back result
		connection.sendObject(response);
	}

	private void handleCheckCreateAccountPacket(CheckCreateAccountPacket p){
		System.out.println("Creating account for username " + p.username);
		PlayerDAO.createPlayer(p.username, p.hashedPassword, p.email);
	}
}