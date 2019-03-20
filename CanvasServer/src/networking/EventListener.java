package networking;

import database.dao.MatchDAO;
import database.dao.PlayerDAO;
import database.manager.PasswordManager;
import database.manager.PlayerManager;
import email.EmailHandler;

import game.networking.objects.Match;
import game.networking.objects.MatchManager;
import game.networking.objects.Player;
import networking.helper.ClassMatchCache;
import networking.packets.incoming.*;
import networking.packets.outgoing.*;

import java.util.ArrayList;

import static networking.helper.ClassMatcher.match;

public class EventListener {

	private ClassMatchCache mainDispatcher = new ClassMatchCache();
	private Connection connection;

	public void received(Object packet, Connection connection) {
		this.connection = connection;
		mainDispatcher.cache(
				/* Register Packets and their functionality here */
				() -> match().with(AddConnectionPacket.class, this::handleAddConnection)
						.with(RemoveConnectionPacket.class, this::handleRemoveConnection)
						.with(CheckUsernamePacket.class, this::handleCheckUsername)
						.with(CheckEmailPacket.class, this::handleCheckEmail)
						.with(CheckPasswordHashConfirmation.class, this::handleCheckPasswordHashConfirmation)
						.with(CheckCreateAccountPacket.class, this::handleCheckCreateAccountPacket)
						.with(CheckActivationEmail.class, this::handleCheckActivationEmail)
						.with(LoginUsernamePacket.class, this::handleLoginUsernamePacket)
						.with(SendFindMatchPacket.class, this::handleSendFindMatchPacket)
						.fallthrough(this::fallthrough))
				.exec(packet);
	}

	private void fallthrough(Object o) {
		System.out.println("Can't find packet " + o.toString());
	}

	private void handleAddConnection(AddConnectionPacket p) {
		AddConnectionPacket packet = p;
		for (int i = 0; i < ConnectionHandler.connections.size(); i++) {
			Connection c = ConnectionHandler.connections.get(i);
			if (c != connection) {
				c.sendObject(packet);
			}
		}
	}

	private void handleRemoveConnection(RemoveConnectionPacket p) {
		RemoveConnectionPacket packet = p;
		//System.out.println("Connection: " + packet.playerID + " has disconnected");
		//ConnectionHandler.connections.remove(packet.playerID);
	}

	private void handleCheckUsername(CheckUsernamePacket p) {
		System.out.println("Got Packet to Check Username....  " + p.username);
		SendUsernameCheckResult response = new SendUsernameCheckResult(p.playerSession);
		response.result = PlayerManager.isValidUsername(p.username.toLowerCase());
		System.out.println("Sending Result... " + response.result);
		// Send back result
		connection.sendObject(response);
	}

	private void handleCheckEmail(CheckEmailPacket p) {
		System.out.println("Got Packet to Check Email....  " + p.email);
		SendEmailCheckResult response = new SendEmailCheckResult(p.playerSession);
		response.result = PlayerManager.isValidEmail(p.email.toLowerCase());
		System.out.println("Sending Result... " + response.result);
		// Send back result
		connection.sendObject(response);
	}

	private void handleCheckPasswordHashConfirmation(CheckPasswordHashConfirmation p) {
		System.out.println("Got Packet to Check Login Attempt....  ");
		SendPasswordHashConfirmation response = new SendPasswordHashConfirmation();
		response.passwordResult = PasswordManager.isValidPassword(p.username, p.password);
		System.out.println("Login Attempt Result is... " + response.passwordResult);

		// If passwords match, then user is going to login anyway, so we can put them into the active users array list
		if(response.passwordResult){
			game.networking.objects.Player newPlayer = new game.networking.objects.Player(p.username);
			//CurrentActivePlayers.playerArrayList.add(newPlayer);
		}
		// Send back result
		connection.sendObject(response);
	}

	private void handleCheckCreateAccountPacket(CheckCreateAccountPacket p){
		System.out.println("Creating account for username " + p.username);
		PlayerDAO.createPlayer(p.username, p.hashedPassword, p.email);
	}

	private void handleCheckActivationEmail(CheckActivationEmail p){
		EmailHandler emailHandler = new EmailHandler();
		// Send activation email
		emailHandler.SendActivationEmail(p.email);

		System.out.println("Send activation email to " + p.email + " with activation code: " + emailHandler.getActivationCode());
	}

	private void handleLoginUsernamePacket(LoginUsernamePacket p){
		SendLoginUsernameResult response = new SendLoginUsernameResult();
		response.result = PlayerManager.checkIfUserExists(p.username);
		connection.sendObject(response);
		System.out.println("Got request to check if username " + p.username + " exists.\nResult is " + response.result);

	}

	private void handleSendFindMatchPacket(SendFindMatchPacket p){
		CheckFindMatchResult response = new CheckFindMatchResult();
		response.activeMatch = MatchManager.ReturnActiveMatch();

		int newPlayerCount = response.activeMatch.getPlayerCount() + 1;
		MatchDAO.UpdatePlayerCount(response.activeMatch.getMatchID(), newPlayerCount);

		// Get existing people in match
		ArrayList<Player> value = MatchManager.GetPlayersInMatch().get(response.activeMatch.getMatchID());

		// Match Already Exists in ArrayList
		if(value != null){
			ArrayList<Player> peopleInMatch = MatchManager.GetPlayersInMatch().get(response.activeMatch.getMatchID());
			// Add new player
			peopleInMatch.add(p.player);
			// Update List with New Player
			MatchManager.GetPlayersInMatch().put(response.activeMatch.getMatchID(), peopleInMatch);
			response.setPlayers(peopleInMatch);
		}
		// No Existing Match in HashMap
		else {
			ArrayList<Player> peopleInMatch = new ArrayList<>();
			// Add new player
			peopleInMatch.add(p.player);
			// Update List with New Player
			MatchManager.GetPlayersInMatch().put(response.activeMatch.getMatchID(), peopleInMatch);
			response.setPlayers(peopleInMatch);
		}

		this.connection.sendObject(response);
	}

	private void sendToAllInMatch(ArrayList<Player> players, Match match) {

	}
}