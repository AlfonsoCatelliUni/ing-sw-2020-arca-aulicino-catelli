package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.events.ClientToServerEvent;
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
            virtualView.newNicknameID(nickname, ID);

            List<String> connectedPlayers = preGameLobby.getConnectedPlayers();

            if(connectedPlayers.size() == 1) {
                virtualView.sendMessageTo(nickname, new FirstConnectedEvent());
            }
            else {
                virtualView.sendMessageTo(nickname, new SuccessfullyConnectedEvent(connectedPlayers));
            }

        }
        else {
            virtualView.sendMessageTo(ID, new UnavailableNicknameEvent());
        }


    }


    @Override
    public void manageEvent(DisconnectionEvent event) {

    }


    //TODO : da completare, problema di concorrenza connessioni
    @Override
    public void manageEvent(ChosenPlayerNumberEvent event) {

        String nickname = event.getNickname();

        Integer numberOfPlayers = event.getNumber();

        if(numberOfPlayers == 2 || numberOfPlayers == 3) {
            preGameLobby.setNumberOfPlayers(numberOfPlayers);

            //virtualView.sendMessageTo(nickname, new );
        }
        else {
            preGameLobby.setNumberOfPlayers(2);
        }

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

//        List<Action> actionsAvailable = game.getPossibleActions(event.getPlayerNickname(), event.getPawnRow(), event.getPawnColumn());

//        Boolean validAction = false;

//        for(Action a : actionsAvailable) {
//            if (a.getActionID().equals(event.getActionID())) {
//                validAction = true;
//                break;
//            }
//        }
        String nickname = event.getPlayerNickname();

        Action chosenAction = event.getAction();

        int row = event.getPawnRow();
        int column = event.getPawnColumn();


        if( game.isValid(chosenAction) ) {
            //TODO : cambiare in point
            List<Cell> availableCellsToMove = game.wherePawnCanMove(nickname, row, column);

            virtualView.sendMessage(new GivePossibleCellsToMoveEvent(nickname, availableCellsToMove));
        }
        else {
            List<Action> possibleActions = game.getLastActionsList();

            //TODO : cambiare e mettere sendMessageTo
            virtualView.sendMessage(new InvalidChosenActionEvent(nickname, possibleActions));
        }
    }


    @Override
    public void manageEvent(ChosenBuildActionEvent event) {

        String nickname = event.getPlayerNickname();

        Action chosenAction = event.getAction();

        int row = event.getPawnRow();
        int column = event.getPawnColumn();

        //here there's the control to verify that the chosen action is valid
        if( game.isValid(chosenAction) ) {
            //if it's ok the game automatically return the cells available to this action
            //TODO : cambiare in point
            List<Cell> availableCellsToBuild = game.wherePawnCanBuild(nickname, row, column);

            virtualView.sendMessage(new GivePossibleCellsToBuildEvent(nickname, availableCellsToBuild));
        }
        else {
            //if it's not ok than the game automatically return the last possible actions list
            //because the game state isn't changed
            List<Action> possibleActions = game.getLastActionsList();

            //TODO : cambiare e mettere sendMessageTo
            virtualView.sendMessage(new InvalidChosenActionEvent(nickname, possibleActions));
        }
    }


    @Override
    public void manageEvent(ChosenFinishActionEvent event) {
        String nickname = event.getPlayerNickname();

        Action chosenAction = event.getAction();

        if( game.isValid(chosenAction) ) {
            //TODO : devo chiamare il next current player
            game.nextCurrentPlayer();

            //virtualView.sendMessageTo();
        }
        else {
            List<Action> availableActions = game.getLastActionsList();
        }

    }


    @Override
    public void manageEvent(ChosenCellToMoveEvent event) {

        String nickname = event.getPlayerNickname();

        int row = event.getPawnRow();
        int column = event.getPawnColumn();

        int nextRow = event.getNextRow();
        int nextColumn = event.getNextColumn();

        if(game.isValidCoordinate(row, column)
                &&  game.isValidCoordinate(nextRow, nextColumn)
                &&  game.isValid(nextRow, nextColumn)) {
            //THAT'S IMPORTANT!
            game.movePawn(nickname, row, column, nextRow, nextColumn);

            List<Action> availableActions = game.getPossibleActions(nickname, nextRow, nextColumn);
            if(availableActions.size() > 0) {
                virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, availableActions));
            }
            else {
                virtualView.sendMessageTo(nickname, new LosingByNoActionEvent(nickname, "OMG! YOU ARE SO BAD AT THIS GAME! LOSER!"));
            }
        }
        else {
            virtualView.sendMessageTo(nickname, new InvalidChosenCellEvent(nickname, game.getLastCellsList()));
        }

    }


    @Override
    public void manageEvent(ChosenCellToBuildEvent event) {
        String nickname = event.getPlayerNickname();

        int row = event.getPawnRow();
        int column = event.getPawnColumn();

        int nextRow = event.getNextRow();
        int nextColumn = event.getNextColumn();

        if(game.isValidCoordinate(row, column)
                &&  game.isValidCoordinate(nextRow, nextColumn)
                &&  game.isValid(nextRow, nextColumn)) {
            //THAT'S IMPORTANT!

            List<Building> availableBuildings = game.getPossibleBuildingOnCell(nickname, nextRow, nextColumn);
            if(availableBuildings.size() > 0) {
                //TODO : fare l'evento GivePossibleBuildingsEvent
                //virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, availableBuildings));
            }
            else {
                throw new RuntimeException("I don't really know what's happen here, please control some of your code! It's something with the buildings on a cell. :/");
            }
        }
        else {
            virtualView.sendMessageTo(nickname, new InvalidChosenCellEvent(nickname, game.getLastCellsList()));
        }
    }


    @Override
    public void manageEvent(VictoryEvent event) {

        Player winnerPlayer = game.getPlayerByName(event.getWinnerNickname());



    }


    // ======================================================================================





}
