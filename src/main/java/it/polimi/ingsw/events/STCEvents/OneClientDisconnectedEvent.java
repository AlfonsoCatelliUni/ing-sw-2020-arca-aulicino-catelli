package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.List;

public class OneClientDisconnectedEvent extends ServerToClientEvent {

    public String disconnected;

    public List<String> playersInside;

    public OneClientDisconnectedEvent(String disconnected, List<String> playersInside) {
        this.disconnected = disconnected;
        this.playersInside = playersInside;
    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }
}
