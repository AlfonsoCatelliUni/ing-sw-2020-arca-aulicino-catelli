package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.List;

public class ClosedWaitingRoomEvent extends ServerToClientEvent {

    public List<String> connectedPlayers;

    public ClosedWaitingRoomEvent (List<String> players){
        this.connectedPlayers = players;
    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

}
