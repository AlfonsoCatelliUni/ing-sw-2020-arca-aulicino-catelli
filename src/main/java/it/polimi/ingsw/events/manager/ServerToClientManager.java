package it.polimi.ingsw.events.manager;

import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;

public interface ServerToClientManager {


    void receiveEvent( ServerToClientEvent event );


    // ======================================================================================
    // MARK : Network And Update Events

    void manageEvent(ConnectionEstablishedEvent event);

    void manageEvent(FirstConnectedEvent event);

    void manageEvent(SuccessfullyConnectedEvent event);

    void manageEvent(NotifyStatusEvent event);

    void manageEvent(DisconnectionEvent event);

    void manageEvent(UnableToEnterWaitingRoomEvent event);


    // ======================================================================================
    // MARK : Game Based Events

    void manageEvent(ClosedWaitingRoomEvent event);

    void manageEvent(RoomNotFilled event);

    void manageEvent(AskNewNicknameEvent event);

    void manageEvent(AskInitPawnsEvent event);

    void manageEvent(AskWhichPawnsUseEvent event);

    void manageEvent(UnavailableNicknameEvent event);

    void manageEvent(GivePossibleCardsEvent event);




    void manageEvent(GivePossibleActionsEvent event);

    void manageEvent(GivePossibleCellsToMoveEvent event);

    void manageEvent(GivePossibleCellsToBuildEvent event);

    void manageEvent(GivePossibleBuildingsEvent event);


    void manageEvent(StartGameEvent event);

    void manageEvent(LosingByNoActionEvent event);

    void manageEvent(EndGameSTCEvent event);


}
