package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.List;

public class OnePlayerEnteredEvent extends ServerToClientEvent {

    public String newPlayer;

    public List<String> connectedPlayers;



    public OnePlayerEnteredEvent(String newPlayer, List<String> connectedPlayers) {
        this.newPlayer = newPlayer;
        this.connectedPlayers = connectedPlayers;
    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }
}
