package it.polimi.ingsw.events.manager;

import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;

public interface ServerToClientManager {


    void receiveEvent(ServerToClientEvent event);


    // ======================================================================================
    // MARK : Network And Update Events

    void manageEvent(ConnectionEstablishedEvent event);

    void manageEvent(FirstConnectedEvent event);

    void manageEvent(SuccessfullyConnectedEvent event);

    void manageEvent(NotifyStatusEvent event);

    void manageEvent(OneClientDisconnectedEvent event);

    void manageEvent(OnePlayerEnteredEvent event);

    void manageEvent(UnableToEnterWaitingRoomEvent event);

    void manageEvent(PlainTextEvent event);


    // ======================================================================================
    // MARK : Game Based Events

    void manageEvent(ClosedWaitingRoomEvent event);

    void manageEvent(RoomNotFilledEvent event);

    void manageEvent(AskInitPawnsEvent event);

    void manageEvent(AskWhichPawnsUseEvent event);

    void manageEvent(UnavailableNicknameEvent event);

    void manageEvent(GivePossibleCardsEvent event);




    void manageEvent(GivePossibleActionsEvent event);

    void manageEvent(GivePossibleCellsToMoveEvent event);

    void manageEvent(GivePossibleCellsToBuildEvent event);

    void manageEvent(GivePossibleBuildingsEvent event);

    void manageEvent(GivePossibleCellsToDestroyEvent event);

    void manageEvent(GivePossibleCellsToForceEvent event);

    void manageEvent(StartGameEvent event);

    void manageEvent(DisconnectionClientEvent event);

    void manageEvent(LosingByNoActionEvent event);

    void manageEvent(EndGameSTCEvent event);


}
