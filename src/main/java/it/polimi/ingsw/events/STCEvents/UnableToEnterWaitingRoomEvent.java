package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class UnableToEnterWaitingRoomEvent extends ServerToClientEvent {


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
