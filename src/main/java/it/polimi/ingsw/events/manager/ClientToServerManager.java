package it.polimi.ingsw.events.manager;

import it.polimi.ingsw.events.CTSEvents.ChosenMoveActionEvent;
import it.polimi.ingsw.events.CTSEvents.VictoryEvent;
import it.polimi.ingsw.events.ClientToServerEvent;

public interface ClientToServerManager {


    void receiveEvent( ClientToServerEvent event );



    void manageEvent(ChosenMoveActionEvent event);
    void manageEvent(VictoryEvent event);


}
