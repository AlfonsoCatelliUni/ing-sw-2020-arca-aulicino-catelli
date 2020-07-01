package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.List;

public class OneClientDisconnectedEvent extends ServerToClientEvent {

    public String disconnected;

    public List<String> connectedPlayers;

    public OneClientDisconnectedEvent(String disconnected, List<String> connectedPlayers) {
        this.disconnected = disconnected;
        this.connectedPlayers = connectedPlayers;
    }


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    //USED IN TESTING
    @Override
    public String toString() {
        return "OneClientDisconnectedEvent{" + "\n" +
                "disconnected='" + disconnected + '\'' + ",\n" +
                "connectedPlayers=" + connectedPlayers + "\n" +
                '}';
    }
}
