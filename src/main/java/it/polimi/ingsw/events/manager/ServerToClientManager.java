package it.polimi.ingsw.events.manager;

import it.polimi.ingsw.events.CTSEvents.ChosenMoveActionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.STCEvents.GivePossibleActionsEvent;
import it.polimi.ingsw.events.STCEvents.NotifyStatusEvent;
import it.polimi.ingsw.events.ServerToClientEvent;

public interface ServerToClientManager {


    void receiveSTC( ServerToClientEvent event );


    void manageSTCEvent(NotifyStatusEvent event);
    void manageSTCEvent(GivePossibleActionsEvent event);


}
