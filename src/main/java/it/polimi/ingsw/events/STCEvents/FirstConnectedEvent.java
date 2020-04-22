package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.io.Serializable;

public class FirstConnectedEvent implements ServerToClientEvent, Serializable {


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


}
