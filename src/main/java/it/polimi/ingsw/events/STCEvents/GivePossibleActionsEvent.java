package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class GivePossibleActionsEvent implements ServerToClientEvent {


    //TODO : fill the method
    @Override
    public void accept(ClientToServerManager visitor) {
    }


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageSTCEvent(this);
    }

}
