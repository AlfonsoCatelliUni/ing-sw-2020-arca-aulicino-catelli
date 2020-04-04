package it.polimi.ingsw.events.manager;

import it.polimi.ingsw.events.CTSEvents.ChosenMoveActionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;

public interface ClientToServerManager {


    void receiveCTS( ClientToServerEvent event );


    void manageCTSEvent(ChosenMoveActionEvent event);



}
