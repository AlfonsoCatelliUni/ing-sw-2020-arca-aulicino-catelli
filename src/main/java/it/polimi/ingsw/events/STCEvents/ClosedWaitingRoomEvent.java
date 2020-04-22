package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class ClosedWaitingRoomEvent extends ServerToClientEvent {


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

}
