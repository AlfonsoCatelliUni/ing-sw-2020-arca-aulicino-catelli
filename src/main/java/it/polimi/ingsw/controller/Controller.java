package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.STCEvents.UnavailableNicknameEvent;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.PreGameLobby;
import it.polimi.ingsw.view.server.VirtualView;

import java.util.List;

public class Controller implements Observer, ClientToServerManager {


    private VirtualView virtualView;


    private PreGameLobby preGameLobby;

    private Game game;


    // ======================================================================================


    public Controller(Game game) {
        this.game = game;

    }


    public Controller() {
        this.virtualView = new VirtualView();
        this.preGameLobby = new PreGameLobby();

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
        throw new RuntimeException("How the hell is possible that the Controller received a ServerToClientEvent? --- Great! Now I've to fix it.");
    }


    @Override
    public void receiveEvent(ClientToServerEvent event) {
        event.accept(this);
    }


    // ======================================================================================


    @Override
    public void manageEvent(NewConnectionEvent event) {

        Integer ID = event.getID();
        String nickname = event.getNickname();

        Boolean isNicknameFree = preGameLobby.isNicknameAvailable(nickname);

        if(isNicknameFree) {
            //TODO : la addPlayer in PreGameLobby potrebbe essere sync
            preGameLobby.addPlayer(nickname);
        }
        else {
            virtualView.sendMessageTo(ID, new UnavailableNicknameEvent(ID));
        }


    }


    @Override
    public void manageEvent(DisconnectionEvent event) {

    }


    // ======================================================================================


    @Override
    public void manageEvent(ChosenInitialPawnCellEvent event) {

        Boolean isSpotFree = preGameLobby.isSpotFree(event.getPawnRow(), event.getPawnColumn());

        /* before I control that the selected spot is really free */
        if(isSpotFree) {
            preGameLobby.addNewPawnCoordinates(event.getPlayerNickname(), event.getPawnRow(), event.getPawnColumn());
        }
        else {
            //TODO : mandare messaggio di inserimento del nome del giocatore
            //virtualView.sendMessage();
        }


    }


    @Override
    public void manageEvent(ChosenCardEvent event) {

        Boolean isCardAvailable = preGameLobby.isCardAvailable(event.getCard());

        if(isCardAvailable) {
            preGameLobby.addCard(event.getPlayerNickname(), event.getCard());
        }
        else {
            //TODO : mandare messaggio
        }

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

        Player winnerPlayer = game.getPlayerByName(event.getWinnerNickname());



    }


    // ======================================================================================





}
