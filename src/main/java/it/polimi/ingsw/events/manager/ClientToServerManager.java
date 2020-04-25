package it.polimi.ingsw.events.manager;

import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.ClientToServerEvent;

public interface ClientToServerManager {


    void receiveEvent(ClientToServerEvent event);


    // ======================================================================================
    // MARK : Network And Update Events


    void manageEvent(NewConnectionEvent event);

    void manageEvent(ClientDisconnectionEvent event);

    void manageEvent(ChosenPlayerNumberEvent event);


    // ======================================================================================
    // MARK : Game Based Events


    void manageEvent(ChosenInitialPawnCellEvent event);

    void manageEvent(ChosenCardEvent event);


    void manageEvent(ChosenPawnToUseEvent event);

    void manageEvent(ChosenMoveActionEvent event);

    void manageEvent(ChosenBuildActionEvent event);

    void manageEvent(ChosenDestroyActionEvent event);

    void manageEvent(ChosenFinishActionEvent event);



    void manageEvent(ChosenCellToMoveEvent event);

    void manageEvent(ChosenCellToBuildEvent event);

    void manageEvent(ChosenBuildingEvent event);



    void manageEvent(VictoryEvent event);



}
