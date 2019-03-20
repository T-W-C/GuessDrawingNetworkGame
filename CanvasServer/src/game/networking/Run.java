package game.networking;

import database.dao.MatchDAO;
import database.dao.PlayerDAO;
import database.dao.PortDomainDAO;
import game.networking.objects.Match;
import game.networking.objects.MatchManager;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Run {

    public static void main(String[] args) {
        PortDomainDAO gameServerPorts = new PortDomainDAO();
        for (Map.Entry<Integer,Boolean> ports : gameServerPorts.getPorts().entrySet()){
            int port = ports.getKey();
            boolean isActive = ports.getValue();
            if (!isActive){
                // Create new MatchID
                int match1 = MatchDAO.CreateNewMatch();
                MatchDAO.UpdateMatchPort(match1, port);

                GameServer gameServer= GameServer.getInstance().setServerIP("127.0.0.1").setPort(port);
                System.out.println("(GameServer) -> Started a Match on Port " + port);
                System.out.println("(GameServer) -> Hosted Match ID: " + match1);
                gameServer.start();

                // Make Port In-Use
                gameServerPorts.PortInUse(port);
                return;
            }
        }
    }
}
