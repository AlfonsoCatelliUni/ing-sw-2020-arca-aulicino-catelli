package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.VictoryEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.events.CTSEvents.ChosenMoveActionEvent;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Game;

import java.util.List;

public class Controller implements Observer, ClientToServerManager {


    private Game game;


    // ======================================================================================


    public Controller( Game game ){

        this.game = game;

    }


    // ======================================================================================


    @Override
    public void update(Object event) throws RuntimeException {

        if(event instanceof ClientToServerEvent) {
            ((ClientToServerEvent) event).accept(this);
        }
        else {
            throw new RuntimeException("The controller cant receive a ServerToClientEvent!");
        }

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

        /* controllo che tra le azioni possibili ci sia una MoveAction */
        for(Action a : actionsAvailable) {
            if (a instanceof MoveAction) {
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




}
