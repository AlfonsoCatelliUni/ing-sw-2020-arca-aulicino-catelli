package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.PreGameLobby;
import it.polimi.ingsw.view.server.VirtualView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Observer, ClientToServerManager {


    private VirtualView virtualView;


    private PreGameLobby preGameLobby;

    private Game game;


    // MARK : Constructor Section ======================================================================================


    public Controller(Game game) {
        this.game = game;

    }


    public Controller() {
        this.virtualView = new VirtualView(this);

        this.preGameLobby = new PreGameLobby();

    }


    public VirtualView getVirtualView() {
        return this.virtualView;
    }


    // MARK : Pattern Visitor Methods ======================================================================================


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


    // MARK : Json Generator Section ======================================================================================


    public String generateJsonActions(List<Action> actions) {

        String generatedJSON = "";

        JSONArray jsonList = new JSONArray();

        for (Action a : actions ) {
            JSONObject cellObj = new JSONObject();

            // writing the cell coordinate into the Json
            cellObj.put("action_id", a.getActionID());

            jsonList.add(cellObj);
        }

        generatedJSON = jsonList.toString();

        return generatedJSON;
    }


    public String generateJsonCells(List<Cell> cells) {

        String generatedJSON = "";

        JSONArray jsonList = new JSONArray();

        for (Cell c : cells ) {
            JSONObject cellObj = new JSONObject();

            // writing the cell coordinate into the Json
            cellObj.put("row", c.getRowPosition());
            cellObj.put("column", c.getColumnPosition());

            jsonList.add(cellObj);
        }

        generatedJSON = jsonList.toString();

        return generatedJSON;
    }


    public String generateJsonBuildings(List<Building> buildings) {

        String generatedJSON = "";

        JSONArray jsonList = new JSONArray();

        for (Building b : buildings ) {
            JSONObject cellObj = new JSONObject();

            // writing the building info into the Json
            cellObj.put("level", b.getLevel());
            cellObj.put("is_dome", b.getIsDome());

            jsonList.add(cellObj);
        }

        generatedJSON = jsonList.toString();

        return generatedJSON;
    }


    public String generateJsonCards(List<Card> cards) {

        String generatedJSON = "";

        JSONArray jsonList = new JSONArray();

        for (Card c : cards ) {
            JSONObject cellObj = new JSONObject();

            // writing the cards info into the Json
            cellObj.put("name", c.getName());
            cellObj.put("effect", c.getEffect());

            jsonList.add(cellObj);
        }

        generatedJSON = jsonList.toString();

        return generatedJSON;
    }


    public String generateJsonPlayersInfo(List<String> players,
                                        List<Color> colors,
                                        List<Card> cards) {

        String generatedJSON = "";

        JSONArray jsonList = new JSONArray();

        for(int i = 0; i < players.size(); i++) {
            JSONObject cellObj = new JSONObject();

            // writing the cards info into the Json
            cellObj.put("nickname", players.get(i));
            cellObj.put("color", colors.get(i).toString());
            cellObj.put("card", cards.get(i).getName());
            cellObj.put("effect", cards.get(i).getEffect());

            jsonList.add(cellObj);
        }

        generatedJSON = jsonList.toString();

        return generatedJSON;
    }


    // MARK : Network Event Manager Section ======================================================================================


    @Override
    public void manageEvent(NewConnectionEvent event) {

        Integer ID = event.ID;

        String nickname = event.nickname;

        Boolean isNicknameFree = preGameLobby.isNicknameAvailable(nickname);


        if( isNicknameFree && !preGameLobby.getClosed() ) {
            //TODO : la addPlayer in PreGameLobby potrebbe essere sync
            preGameLobby.addPlayer(nickname);
            virtualView.newNicknameID(nickname, ID);

            List<String> connectedPlayers = preGameLobby.getConnectedPlayers();

            //if there's only one player connected to the waitingRoom, than this is the first one
            //and he has to choose the number of the players for the new match
            if(connectedPlayers.size() == 1) {
                virtualView.sendMessageTo(nickname, new FirstConnectedEvent(nickname));
            }
            //if the waitingRoom is filled than we broadcast a message that communicates this event
            //and we ask the first entered player to choose his card
            else if(connectedPlayers.size() == preGameLobby.getNumberOfPlayers()) {

                virtualView.sendMessage(new ClosedWaitingRoomEvent());

                List<Card> cards = new ArrayList<>(preGameLobby.getPickedCards());
                String firstPlayer = connectedPlayers.get(0);

                virtualView.sendMessageTo(firstPlayer, new GivePossibleCardsEvent(firstPlayer, cards, true));
            }
            //if is an intermediate connection than nothing happens
            else {
                virtualView.sendMessageTo(nickname, new SuccessfullyConnectedEvent(connectedPlayers));
            }

        }
        //if the waitingRoom is already closed than we disconnect the player
        else if(preGameLobby.getClosed()) {
            virtualView.sendMessageTo(ID, new UnableToEnterWaitingRoomEvent());
        }
        //if the chosen nickname is already taken we ask to enter it again
        else {
            virtualView.sendMessageTo(ID, new UnavailableNicknameEvent());
        }


    }


    @Override
    public void manageEvent(ClientDisconnectionEvent event) {

        String disconnectedPlayer = virtualView.removeNicknameIDConnection(event.ID);
        if(disconnectedPlayer == null)
            throw new RuntimeException("It's strange, but the ID of the player is corrupt!");


        if(preGameLobby != null) {

            //if the nickname is not available than the player was really connected in the preGameLobby
            if(!preGameLobby.isNicknameAvailable(disconnectedPlayer)) {

                if(preGameLobby.getClosed()) {
                    preGameLobby.deletePlayerInformation(disconnectedPlayer);
                }

            }
            else
                throw new RuntimeException("It's strange, but the Nickname of the player is corrupt!");

        }
        else if(game != null) {

            //TODO : fare tearDownGame
            game.tearDownGame();
        }

        virtualView.sendMessage(new DisconnectionEvent());
    }


    //TODO : da completare, problema di concorrenza connessioni
    @Override
    public void manageEvent(ChosenPlayerNumberEvent event) {

        String nickname = event.nickname;

        //Integer numberOfPlayers = event.getNumber();
        Integer numberOfPlayers = event.number;

        List<String> connectedPlayers = preGameLobby.getConnectedPlayers();

        if(numberOfPlayers == 2 || numberOfPlayers == 3) {
            preGameLobby.setNumberOfPlayers(numberOfPlayers);

            virtualView.sendMessageTo(nickname, new SuccessfullyConnectedEvent(connectedPlayers));
        }
        else {
            preGameLobby.setNumberOfPlayers(2);

        }

    }


    // MARK : Game Based Event Manager Section ======================================================================================


    @Override
    public void manageEvent(ChosenInitialPawnCellEvent event) {

        Boolean isSpotFree = preGameLobby.isSpotFree(event.pawnRow, event.pawnColumn);

        /* before I control that the selected spot is really free */
        if(isSpotFree) {
            preGameLobby.addNewPawnCoordinates(event.playerNickname, event.pawnRow, event.pawnColumn);
        }
        else {
            //TODO : mandare messaggio di inserimento del nome del giocatore
            //virtualView.sendMessage();
        }


    }


    @Override
    public void manageEvent(ChosenCardEvent event) {

        Boolean isCardAvailable = preGameLobby.isCardAvailable(event.card);

        if(isCardAvailable) {
            preGameLobby.addCard(event.playerNickname, event.card);

            int index = preGameLobby.getConnectedPlayers().indexOf(event.playerNickname);
            index++;

            if (index < preGameLobby.getNumberOfPlayers() - 1) {
                String nextPlayer = preGameLobby.getConnectedPlayers().get(index);

                virtualView.sendMessageTo(nextPlayer, new GivePossibleCardsEvent(nextPlayer, preGameLobby.getPickedCards(), true));

            } else
                //inviare richiesta inizializzazione pawn al primo giocatore
                virtualView.sendMessageTo(preGameLobby.getConnectedPlayers().get(0), new AskInitPawnsEvent(preGameLobby.getConnectedPlayers().get(0),true) );
        }
        else {
            virtualView.sendMessageTo(event.playerNickname, new GivePossibleCardsEvent(event.playerNickname, preGameLobby.getPickedCards(), false));
        }



    }


    @Override
    public void manageEvent(ChosenMoveActionEvent event) {

        List<Action> actionsAvailable = game.getPossibleActions(event.playerNickname, event.pawnRow, event.pawnColumn);

        boolean validAction = false;

        for(Action a : actionsAvailable) {
            if (a.getActionID().equals(event.action.getActionID())) {
                validAction = true;
                break;
            }
        }
        String nickname = event.playerNickname;

        Action chosenAction = event.action;

        int row = event.pawnRow;
        int column = event.pawnColumn;


        if( game.isValid(chosenAction) ) {
            //TODO : cambiare in point
            List<Cell> availableCellsToMove = game.wherePawnCanMove(nickname, row, column);

            virtualView.sendMessageTo(nickname, new GivePossibleCellsToMoveEvent(nickname, availableCellsToMove,true));
        }
        else {
            List<Action> possibleActions = game.getLastActionsList();

            //TODO : cambiare e mettere sendMessageTo
            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, possibleActions, false));
        }
    }


    @Override
    public void manageEvent(ChosenBuildActionEvent event) {

        String nickname = event.playerNickname;

        Action chosenAction = event.action;
        int row = event.pawnRow;
        int column = event.pawnColumn;

        //here there's the control to verify that the chosen action is valid
        if( game.isValid(chosenAction) ) {
            //if it's ok the game automatically return the cells available to this action
            //TODO : cambiare in point
            List<Cell> availableCellsToBuild = game.wherePawnCanBuild(nickname, row, column);

            virtualView.sendMessage(new GivePossibleCellsToBuildEvent(nickname, availableCellsToBuild, true));
        }
        else {
            //if it's not ok than the game automatically return the last possible actions list
            //because the game state isn't changed
            List<Action> possibleActions = game.getLastActionsList();

            //TODO : cambiare e mettere sendMessageTo
            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, possibleActions, false));
        }
    }


    @Override
    public void manageEvent(ChosenFinishActionEvent event) {
        String nickname = event.playerNickname;

        Action chosenAction = event.action;

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

        String nickname = event.playerNickname;

        int row = event.pawnRow;
        int column = event.pawnColumn;

        int nextRow = event.nextRow;
        int nextColumn = event.nextColumn;

        if(game.isValidCoordinate(row, column)
                &&  game.isValidCoordinate(nextRow, nextColumn)
                &&  game.isValid(nextRow, nextColumn)) {
            //THAT'S IMPORTANT!
            game.movePawn(nickname, row, column, nextRow, nextColumn);

            List<Action> availableActions = game.getPossibleActions(nickname, nextRow, nextColumn);
            if(availableActions.size() > 0) {
                virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, availableActions, true));
            }
            else {
                virtualView.sendMessageTo(nickname, new LosingByNoActionEvent(nickname, "OMG! YOU ARE SO BAD AT THIS GAME! LOSER!"));
            }
        }
        else {

            List<Action> possibleActions = game.getPossibleActions(nickname, row, column);

            virtualView.sendMessageTo(nickname, new GivePossibleCellsToMoveEvent(nickname, game.wherePawnCanMove(nickname, row, column), false));
        }

    }
    

    @Override
    public void manageEvent(ChosenCellToBuildEvent event) {
        String nickname = event.playerNickname;

        int row = event.pawnRow;
        int column = event.pawnColumn;

        int nextRow = event.nextRow;
        int nextColumn = event.nextColumn;

        if(game.isValidCoordinate(row, column)
                &&  game.isValidCoordinate(nextRow, nextColumn)
                &&  game.isValid(nextRow, nextColumn)) {
            //THAT'S IMPORTANT!

            List<Building> availableBuildings = game.getPossibleBuildingOnCell(nickname, nextRow, nextColumn);
            if(availableBuildings.size() > 0) {
                //TODO : fare l'evento GivePossibleBuildingsEvent
                virtualView.sendMessageTo(nickname, new GivePossibleBuildingsEvent(nickname, availableBuildings, true));
            }
            else {
                throw new RuntimeException("I don't really know what's happen here, please control some of your code! It's something with the buildings on a cell. :/");
            }
        }
        else {
            virtualView.sendMessageTo(nickname, new GivePossibleCellsToBuildEvent(nickname, game.getLastCellsList(), false));
        }
    }


    @Override
    public void manageEvent(VictoryEvent event) {

        Player winnerPlayer = game.getPlayerByName(event.winnerNickname);



    }


    // MARK : Support Methods Section ======================================================================================





}
