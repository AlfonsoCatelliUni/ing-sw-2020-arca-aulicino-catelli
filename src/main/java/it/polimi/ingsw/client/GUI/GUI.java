package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class GUI implements Client, ServerToClientManager {


    public GUI() {

    }


    // ======================================================================================


    @Override
    public void run() {

    }


    @Override
    public void receiveEvent(ServerToClientEvent event) {

    }

    @Override
    public void manageEvent(ConnectionEstablishedEvent event) {

    }

    @Override
    public void manageEvent(FirstConnectedEvent event) {

    }

    @Override
    public void manageEvent(SuccessfullyConnectedEvent event) {

    }

    @Override
    public void manageEvent(NotifyStatusEvent event) {

    }

    @Override
    public void manageEvent(OneClientDisconnectedEvent event) {

    }

    @Override
    public void manageEvent(UnableToEnterWaitingRoomEvent event) {

    }

    @Override
    public void manageEvent(PlainTextEvent event) {

    }

    @Override
    public void manageEvent(ClosedWaitingRoomEvent event) {

    }

    @Override
    public void manageEvent(RoomNotFilled event) {

    }

    @Override
    public void manageEvent(AskInitPawnsEvent event) {

    }

    @Override
    public void manageEvent(AskWhichPawnsUseEvent event) {

    }

    @Override
    public void manageEvent(UnavailableNicknameEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCardsEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleActionsEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCellsToMoveEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCellsToBuildEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleBuildingsEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCellsToDestroyEvent event) {

    }

    @Override
    public void manageEvent(GivePossibleCellsToForceEvent event) {

    }

    @Override
    public void manageEvent(StartGameEvent event) {

    }

    @Override
    public void manageEvent(DisconnectionClientEvent event) {

    }

    @Override
    public void manageEvent(LosingByNoActionEvent event) {

    }

    @Override
    public void manageEvent(EndGameSTCEvent event) {

    }
}
