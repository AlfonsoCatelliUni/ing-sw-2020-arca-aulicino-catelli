package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.Connection;
import it.polimi.ingsw.server.PreGameLobby;
import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.view.server.VirtualView;

import java.util.List;

public class Controller implements Observer, ClientToServerManager {


    private VirtualView virtualView;


    private PreGameLobby preGameLobby;


    private Game game;


    // ======================================================================================


    public Controller( Game game ){

        this.game = game;

    }


    // ======================================================================================


    @Override
    public void update(Object event) {
        throw new RuntimeException("Yo, Unknown Event Type... This is NOT your hood, men!");
    }


    @Override
    public void update(ClientToServerEvent event) {
        receiveEvent(event);
    }


    @Override
    public void update(ServerToClientEvent event) {
        throw new RuntimeException("How the [beep] is possible that the Controller received a ServerToClientEvent? --- Great! Now I've to fix it.");
    }


    @Override
    public void receiveEvent(ClientToServerEvent event) {
        event.accept(this);
    }


    // ======================================================================================


    @Override
    public void manageEvent(NewConnectionEvent event) {

    }


    @Override
    public void manageEvent(DisconnectionEvent event) {

    }


    // ======================================================================================


    @Override
    public void manageEvent(ChosenCardEvent event) {

    }


    @Override
    public void manageEvent(ChosenMoveActionEvent event) {

        List<Action> actionsAvailable = game.getPossibleActions(event.getPlayerNickname(), event.getPawnRow(), event.getPawnColumn());

        Boolean validAction = false;


        for(Action a : actionsAvailable) {
            if (a.getActionID().equals(event.getActionID())) {
                validAction = true;
                break;
            }
        }

        if(validAction) {
            /* the player have chosen to move the pawn,
             * so I return to him the available cell to move */
            List<Cell> cellsAvailableToMove = game.wherePawnCanMove(event.getPlayerNickname(), event.getPawnRow(), event.getPawnColumn());

            /* mandare messaggio con le celle disponibili per muovere */

        }
        else {
            /* mandare messaggio con le azioni disponibili */

        }





    }


    @Override
    public void manageEvent(ChosenBuildActionEvent event) {

    }


    @Override
    public void manageEvent(ChosenFinishActionEvent event) {

    }


    @Override
    public void manageEvent(ChosenCellToMoveEvent event) {

    }


    @Override
    public void manageEvent(ChosenCellToBuildEvent event) {

    }


    @Override
    public void manageEvent(VictoryEvent event) {

        //done with LAMBDA BITCH!
        Player winnerPlayer = game.getPlayerByName(event.getWinnerNickname());



    }


    // ======================================================================================





}
