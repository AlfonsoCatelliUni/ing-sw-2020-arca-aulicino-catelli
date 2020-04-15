package it.polimi.ingsw.events.manager;

import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;

public interface ServerToClientManager {


    void receiveEvent( ServerToClientEvent event );


    // ======================================================================================
    // MARK : Network And Update Events


    void manageEvent(NotifyStatusEvent event);


    // ======================================================================================
    // MARK : Game Based Events


    void manageEvent(AskNewNicknameEvent event);

    void manageEvent(UnavailableNicknameEvent event);

    void manageEvent(GivePossibleCardsEvent event);



    void manageEvent(GivePossibleActionsEvent event);

    void manageEvent(GivePossibleCellsToMoveEvent event);

    void manageEvent(GivePossibleCellsToBuildEvent event);




}
