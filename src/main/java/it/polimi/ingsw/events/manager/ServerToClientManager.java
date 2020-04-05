package it.polimi.ingsw.events.manager;

import it.polimi.ingsw.events.STCEvents.GivePossibleActionsEvent;
import it.polimi.ingsw.events.STCEvents.NotifyStatusEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

public interface ServerToClientManager {


    void receiveEvent( ServerToClientEvent event );


    void manageEvent(NotifyStatusEvent event);
    void manageEvent(GivePossibleActionsEvent event);


}
