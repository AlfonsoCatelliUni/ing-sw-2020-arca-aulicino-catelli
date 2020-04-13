package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.NewConnectionEvent;
import it.polimi.ingsw.events.CTSEvents.VictoryEvent;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.events.CTSEvents.ChosenMoveActionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.PreGameLobby;

import java.util.List;

public class Controller implements Observer, ClientToServerManager {


    private PreGameLobby preGameLobby;


    private Game game;


    // ======================================================================================


    public Controller( Game game ){

        this.game = game;

    }


    // ======================================================================================


    @Override
    public void update(Object event) {
        throw new RuntimeException("Unknown Event Type!");
    }


    @Override
    public void update(ClientToServerEvent event) {
        receiveEvent(event);
    }


    @Override
    public void update(ServerToClientEvent event) {
        throw new RuntimeException("The controller cant receive a ServerToClientEvent!");
    }


    // ======================================================================================


    @Override
    public void receiveEvent(ClientToServerEvent event) {
        event.accept(this);
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
    public void manageEvent(VictoryEvent event) {

        //done with LAMBDA BITCH!
        Player winnerPlayer = game.getPlayerByName(event.getWinnerNickname());



    }


    // ======================================================================================


    @Override
    public void manageEvent(NewConnectionEvent event) {
        throw new RuntimeException("The Controller does not manage NewConnectionEvent... sadly :(");
    }


}
