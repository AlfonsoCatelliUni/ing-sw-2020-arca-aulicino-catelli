package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.server.PreGameLobby;
import it.polimi.ingsw.view.server.VirtualView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class Controller implements Observer, ClientToServerManager {


    private VirtualView virtualView;


    private PreGameLobby preGameLobby;


    private Game game;


    private TimerTask timerTask;

    private Object lock;


    // MARK : Constructor Section ======================================================================================


    public Controller(Game game) {
        this.game = game;

        lock = new Object();

    }


    public Controller() {
        this.virtualView = new VirtualView(this);

        this.preGameLobby = new PreGameLobby();

        lock = new Object();

    }

    //ONLY FOR TESTING
    public Controller(VirtualView virtualView) {

        this.virtualView = virtualView;

        this.preGameLobby = new PreGameLobby();

        lock = new Object();

    }


    public VirtualView getVirtualView() {
        return this.virtualView;
    }


    // MARK : Pattern Visitor Methods ======================================================================================


    @Override
    public void update(ClientToServerEvent event) {
        synchronized (lock) {
            receiveEvent(event);
        }
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
            cellObj.put("effect", c.getEffectDescription());

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
            cellObj.put("effect", cards.get(i).getEffectDescription());

            jsonList.add(cellObj);
        }

        generatedJSON = jsonList.toString();

        return generatedJSON;
    }


    // MARK : Controller Function Section ======================================================================================


    public void startGame (){

       List<Color> colors = Color.getRandomColors(preGameLobby.getNumberOfPlayers());

       game = new Game(preGameLobby.getConnectedPlayers(), colors, preGameLobby.getPlayerCardMap(), virtualView);

       List<Card> cards = new ArrayList<>();

       for (String name : preGameLobby.getConnectedPlayers())
           cards.add(preGameLobby.getCardOfPlayer(name));

       String cardsInfo = generateJsonCards(cards);

       String info = generateJsonPlayersInfo(preGameLobby.getConnectedPlayers(), colors, cards);

       virtualView.sendMessage(new StartGameEvent(info));

       preGameLobby = null;

       String firstPlayer = game.getPlayersNickname().get(0);

       virtualView.sendMessageTo(firstPlayer, new AskInitPawnsEvent(firstPlayer, true));
    }


    public void countdownStart() {

        new Timer().schedule(timerTask = new TimerTask() {
            @Override
            public void run() {

                if(preGameLobby != null && !preGameLobby.isClosed()) {

                        virtualView.sendMessage(new RoomNotFilled("Room Not Filled In Time!"));

                }
            }
        }, 2 * 60 * 1000); // 2 minutes timer

    }


    /**
     * this method takes in the player that has ended his turn, reset his status and
     * take the successor, calculate his available pawns then send theis coordinates
     * to their player.
     * if there are no pawns available notify the owner that he has loosed the game
     * because he can't do any action
     * @param isLosingEnding this must be set true if the player "nickname" has lost the game,
     *                       in this case before picking the next player, the method remove
     *                       the losing player from the game info
     *                       this must be set false if is a normal end of turn
     */
    public void endTurn(Boolean isLosingEnding) {

        String nameWhoEndedTurn = game.getCurrentPlayer();

        //reset status of the ending player
        game.resetPlayerStatus(nameWhoEndedTurn);

        //if the player has ended his turn because he has lost then we send to him a losing message and remove his info from the game
        if(isLosingEnding) {
            //virtualView.sendMessageTo(nickname, new LosingByNoActionEvent(nickname, "So Sad!"));
            virtualView.sendMessage(new LosingByNoActionEvent(nameWhoEndedTurn, "So Sad!"));
            game.removePlayer(nameWhoEndedTurn);
        } else {
            game.nextCurrentPlayer();
        }

        //takes the index, nickname and pawn of the next player that has to play his turn
        String currentPlayerNickname = game.getCurrentPlayer();
        List<Point> currentPlayerAvailablePawns = generatePointsByCells(game.getAvailablePawns(currentPlayerNickname));



        while(currentPlayerAvailablePawns.size() == 0 && game.getPlayers().size() > 1) {

            //send the message that the current player has lost the game because he can't do any action
            //virtualView.sendMessageTo(currentPlayerNickname, new LosingByNoActionEvent(currentPlayerNickname, "So Sad!"));
            virtualView.sendMessage(new LosingByNoActionEvent(currentPlayerNickname, "So Sad!"));

            //save the nickname of the loser player
            String loserPlayer = currentPlayerNickname;

            //remove the loser player
            game.removePlayer(loserPlayer);

            //now the current is the player after the removed player because the index shifted
            currentPlayerNickname = game.getCurrentPlayer();


            currentPlayerAvailablePawns = generatePointsByCells(game.getAvailablePawns(currentPlayerNickname));


        }

        //if there's only one player we have to notify him with a victory event and return because doesn't need his next actions
        if( game.getPlayersNickname().size() == 1 ) {
            manageEvent( new VictoryEvent(game.getPlayersNickname().get(0)) );
            return;
        }

        virtualView.sendMessageTo(currentPlayerNickname, new AskWhichPawnsUseEvent(currentPlayerNickname, true, currentPlayerAvailablePawns));

    }


    public void closeGameLobby(){

        preGameLobby.closeLobby();

        virtualView.sendMessage(new ClosedWaitingRoomEvent(preGameLobby.getConnectedPlayers()));


        List<String> cardsName = preGameLobby.getPickedCards().stream().map(Card::getName).collect(Collectors.toList());
        List<String> cardsEffect = preGameLobby.getPickedCards().stream().map(Card::getEffectDescription).collect(Collectors.toList());

        String firstPlayer = preGameLobby.getConnectedPlayers().get(0);

        virtualView.sendMessageTo(firstPlayer, new GivePossibleCardsEvent(firstPlayer, cardsName, cardsEffect,  true));

    }


    public void endGame(String winner) {

        Player winnerPlayer = game.getPlayerByName(winner);

        virtualView.sendMessage(new EndGameSTCEvent(winnerPlayer.getName()));

        game = null;

        // a new preGameLobby to handler new connections
        this.preGameLobby = new PreGameLobby();

    }


    /**
     * this method takes in the nickname of the player we want to know the new possible actions,
     * if this player has some new actions then we send them to him, in case the player doesn't have
     * any type of action then he lose the game
     * @param nickname the nickname of the player that wants his next actions
     * @param pawnRow the row of the pawn of the player that wants to know the new actions
     * @param pawnColumn the row of the column of the player that wants to know the new actions
     */
    public void newPossibleActions(String nickname, int pawnRow, int pawnColumn) {

        List<Action> possibleActions = new ArrayList<>();

        //take the possible actions of the player with "nickname" for the pawn in (pawnRow, pawnColumn)
        try {
            possibleActions = game.getPossibleActions(nickname, pawnRow, pawnColumn);
        } catch (NullPointerException e) {
            return;
        }

        //if there is at least one possible action then we have to send to the player
        if (possibleActions.size() > 0) {
            //transform the actions into string and then send
            List<String> actionsInfo = generateActionIDByActions(possibleActions);
            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, true));
        }
        //if the player have no possible action then he lose the game and pass the turn to the next player
        else {
            endTurn(true);
        }

    }


    // MARK : Network Event Manager Section ======================================================================================


    @Override
    public synchronized void manageEvent(NewConnectionEvent event) {

        Integer ID = event.ID;

        String nickname = event.nickname;

        Boolean isNicknameFree = preGameLobby.isNicknameAvailable(nickname);
        Boolean isNicknameValid = preGameLobby.isNicknameValid(nickname);


        if(isNicknameFree && isNicknameValid && !preGameLobby.isClosed()) {

            preGameLobby.addPlayer(nickname);
            virtualView.newNicknameID(nickname, ID);

            List<String> connectedPlayers = preGameLobby.getConnectedPlayers();


            //if there's only one player connected to the waitingRoom, than this is the first one
            //and he has to choose the number of the players for the new match
            if(connectedPlayers.size() == 1) {
                virtualView.sendMessageTo(nickname, new SuccessfullyConnectedEvent(connectedPlayers, nickname));
                virtualView.sendMessageTo(nickname, new FirstConnectedEvent(nickname));
                countdownStart();

            }
            //if the waitingRoom is filled than we broadcast a message that communicates this event
            //and we ask the first entered player to choose his card
            else if(connectedPlayers.size() == preGameLobby.getNumberOfPlayers()) {

                virtualView.sendMessageTo(nickname, new SuccessfullyConnectedEvent(connectedPlayers, nickname));

                closeGameLobby();

            }
            //if is an intermediate connection than nothing happens
            else {
                virtualView.sendMessageTo(nickname, new SuccessfullyConnectedEvent(connectedPlayers, nickname));
            }

        }
        //if the waitingRoom is already closed than we disconnect the player
        else if(preGameLobby.isClosed()) {

            virtualView.sendMessageTo(ID, new UnableToEnterWaitingRoomEvent());
        }
        //if the chosen nickname is already taken we ask to enter it again
        else {
            virtualView.sendMessageTo(ID, new UnavailableNicknameEvent(ID));
        }


    }


    @Override
    public void manageEvent(ClientDisconnectionEvent event) {

        String disconnectedPlayer = virtualView.removeNicknameIDConnection(event.ID);

        if (disconnectedPlayer == null)
            throw new RuntimeException("It's strange, but the ID of the player is corrupt!");

        //if the nickname is not available then the player was really connected in the preGameLobby
        if (preGameLobby != null && preGameLobby.isNicknameAvailable(disconnectedPlayer))
            throw new RuntimeException("It's strange, but the Nickname of the player is corrupt!");



        if(preGameLobby != null && !preGameLobby.isClosed()) {

            boolean disconnectedPlayerWasFirst = disconnectedPlayer.equals(preGameLobby.getConnectedPlayers().get(0));

            //if the preGameLobby isn't closed we have to delete all info of the disconnected player
            preGameLobby.deletePlayerInformation(disconnectedPlayer);

            if (preGameLobby.getConnectedPlayers().size() > 0) {

                List<String> connectedPlayers = new ArrayList<>(preGameLobby.getConnectedPlayers());
                virtualView.sendMessage(new OneClientDisconnectedEvent(disconnectedPlayer, connectedPlayers));

                //if the disconnected player was the creator of the lobby
                //we have to ask to reselect the number of the players for the match
                if (disconnectedPlayerWasFirst) {
                    preGameLobby.setNumberOfPlayers(-1);
                    virtualView.sendMessageTo(preGameLobby.getConnectedPlayers().get(0), new PlainTextEvent("Now you are the first player, so"));
                    virtualView.sendMessageTo(preGameLobby.getConnectedPlayers().get(0), new FirstConnectedEvent(preGameLobby.getConnectedPlayers().get(0)));
                }

            }
            //if there are no players connected to the lobby we reset to default value the number of players of the match
            else {
                preGameLobby.setNumberOfPlayers(-1);
                timerTask.cancel(); // stop the timer
            }

        }
        //the player lost the game and decided to quit
        else if(game != null && !game.getPlayersNickname().contains(disconnectedPlayer)) {
            virtualView.removeNicknameIDConnection(event.ID);
        }
        //if the game is started or the preGameLobby has been closed we have to disconnect all the players
        else {

            virtualView.sendMessage(new PlainTextEvent(disconnectedPlayer + " is disconnected, so the game ended"));
            virtualView.sendMessage(new DisconnectionClientEvent());

            //if the game is not started but the preGameLobby is closed we have to delete all the info of the players
            if(preGameLobby != null) {
                preGameLobby.clearLobby();
            }
            else {
                game = null;
                preGameLobby = new PreGameLobby();
            }

        }


    }


    @Override
    public void manageEvent(ChosenPlayerNumberEvent event) {

        String nickname = event.nickname;

        Integer numberOfPlayers = event.number;

        List<String> connectedPlayers = preGameLobby.getConnectedPlayers();

        if(numberOfPlayers == 2 || numberOfPlayers == 3) {
            preGameLobby.setNumberOfPlayers(numberOfPlayers);
            if (preGameLobby.getConnectedPlayers().size() == numberOfPlayers){
                closeGameLobby();
            }
            else if (preGameLobby.getConnectedPlayers().size() > numberOfPlayers) {
                int numConnected = preGameLobby.getConnectedPlayers().size();
                int diff = numConnected - numberOfPlayers;

                do {
                    String player = preGameLobby.getConnectedPlayers().get(preGameLobby.getConnectedPlayers().size() - 1);
                    preGameLobby.deletePlayerInformation(player);
                    virtualView.sendMessageTo(player, new UnableToEnterWaitingRoomEvent());
                    diff--;

                } while(diff > 0);

                closeGameLobby();
            }

        }
        else {
            preGameLobby.setNumberOfPlayers(2);

        }


    }



    // MARK : Game Based Event Manager Section ======================================================================================


    @Override
    public void manageEvent(ChosenInitialPawnCellEvent event) {


        boolean isMaleSpotFree = game.isValidSpot(event.malePawnRow, event.malePawnColumn);

        boolean isFemaleSpotFree = game.isValidSpot(event.femalePawnRow, event.femalePawnColumn);


        /* before I control that the selected spot is really free and if the player is the current*/
        if(isMaleSpotFree && isFemaleSpotFree && game.isValidPlayer(event.playerNickname)) {
            game.initializePawn(event.playerNickname, event.malePawnRow, event.malePawnColumn);

            game.initializePawn(event.playerNickname, event.femalePawnRow, event.femalePawnColumn);

            if (game.getPlayersNickname().indexOf(event.playerNickname) != game.getPlayers().size() - 1) {

                game.nextCurrentPlayer();

                int index = game.getPlayersNickname().indexOf(game.getCurrentPlayer());

                List <Cell> occupiedCell = game.getAllPawnsCoordinates();
                List<Point> info = generatePointsByCells(occupiedCell);
                virtualView.sendMessageTo(game.getPlayersNickname().get(index), new AskInitPawnsEvent(game.getPlayersNickname().get(index), true, info ));
            }
            else {
                // if everybody initialized pawns, last player end turn
                endTurn(false);
            }
        }
        else{
            List <Cell> occupiedCell = game.getAllPawnsCoordinates();
            List<Point> info = generatePointsByCells(occupiedCell);
            virtualView.sendMessageTo(event.playerNickname, new AskInitPawnsEvent(event.playerNickname, false, info));
        }


    }


    @Override
    public void manageEvent(ChosenCardEvent event) {

        Boolean isCardAvailable = preGameLobby.isCardAvailable(event.card);

        if(isCardAvailable) {
            preGameLobby.addCard(event.playerNickname, event.card);

            int index = preGameLobby.getConnectedPlayers().indexOf(event.playerNickname);
            index++;

            if (index < preGameLobby.getNumberOfPlayers()) {
                String nextPlayer = preGameLobby.getConnectedPlayers().get(index);

                virtualView.sendMessageTo(nextPlayer, new GivePossibleCardsEvent(nextPlayer,
                        preGameLobby.getPickedCards().stream().map(Card::getName).collect(Collectors.toList()),
                        preGameLobby.getPickedCards().stream().map(Card::getEffectDescription).collect(Collectors.toList()),
                        true));

            } else {
                startGame();
            }
        }
        else {
            virtualView.sendMessageTo(event.playerNickname, new GivePossibleCardsEvent(event.playerNickname,
                    preGameLobby.getPickedCards().stream().map(Card::getName).collect(Collectors.toList()),
                    preGameLobby.getPickedCards().stream().map(Card::getEffectDescription).collect(Collectors.toList()),
                    false));
        }



    }


    @Override
    public void manageEvent(ChosenPawnToUseEvent event) {

        String nickname = event.playerNickname;
        int row = event.pawnRow;
        int column = event.pawnColumn;

        boolean isValid = game.isValid(row, column);

        if (isValid && game.isValidPlayer(nickname)) {

            game.setChosenPawn(nickname, row, column);

            List<String> actionsInfo = generateActionIDByActions(game.getPossibleActions(nickname, row, column));

            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, true));

        } else if (!isValid){
            List<Cell> availablePawnsCell = game.getAvailablePawns(nickname);

            List<Point> points = generatePointsByCells(availablePawnsCell);

            virtualView.sendMessageTo(nickname, new AskWhichPawnsUseEvent(nickname, false, points));
        }
    }


    @Override
    public void manageEvent(ChosenMoveActionEvent event) {

        String nickname = event.playerNickname;
        String chosenAction = event.action;
        int row = event.pawnRow;
        int column = event.pawnColumn;


        if( game.isValid(chosenAction) && game.isValidPawn(nickname, row, column) && game.isValidPlayer(nickname) ) {

            List<Cell> availableCellsToMove = game.wherePawnCanMove(nickname, row, column);
            List<Point> cellInfo = generatePointsByCells(availableCellsToMove);

            virtualView.sendMessageTo(nickname, new GivePossibleCellsToMoveEvent(nickname, cellInfo,true));

        } else if ( !(game.isValid(chosenAction) && game.isValidPawn(nickname, row, column)) ){
            List<Action> possibleActions = game.getLastActionsList();
            List<String> actionsInfo = generateActionIDByActions(possibleActions);

            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, false));
        }
    }


    @Override
    public void manageEvent(ChosenBuildActionEvent event) {

        String nickname = event.playerNickname;
        String chosenAction = event.action;

        int row = event.pawnRow;
        int column = event.pawnColumn;

        //here there's the control to verify that the chosen action is valid
        if ( game.isValid(chosenAction) && game.isValidPawn(nickname, row, column) && game.isValidPlayer(nickname) ) {
            //if it's ok the game automatically return the cells available to this action

            List<Cell> availableCellsToBuild = game.wherePawnCanBuild(nickname, row, column);
            List<Point> cellsInfo = generatePointsByCells(availableCellsToBuild);

            virtualView.sendMessageTo(nickname, new GivePossibleCellsToBuildEvent(nickname, cellsInfo, true));
        }
        else if ( !(game.isValid(chosenAction) && game.isValidPawn(nickname, row, column)) ) {
            //if it's not ok than the game automatically return the last possible actions list
            //because the game state isn't changed
            List<Action> possibleActions = game.getLastActionsList();
            List<String> actionsInfo = generateActionIDByActions(possibleActions);

            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, false));
        }
    }


    @Override
    public void manageEvent(ChosenDestroyActionEvent event) {
        String nickname = event.playerNickname;

        String actionID = event.action;

        int pawnRow = event.pawnRow;
        int pawnColumn = event.pawnColumn;

        if (game.isValid(actionID) && game.isValidPawn(nickname, pawnRow, pawnColumn) && game.isValidPlayer(nickname)){

            List<Cell> availableCells = game.wherePawnCanDestroy(nickname);
            List<Point> cellsInfo = generatePointsByCells(availableCells);

            virtualView.sendMessageTo(nickname, new GivePossibleCellsToDestroyEvent(nickname, cellsInfo, true));

        } else if ( !(game.isValid(actionID) && game.isValidPawn(nickname, pawnRow, pawnColumn)) ){
            List<Action> possibleActions = game.getLastActionsList();
            List<String> actionsInfo = generateActionIDByActions(possibleActions);

            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, false));
        }

    }


    @Override
    public void manageEvent(ChosenForceActionEvent event) {

        String nickname = event.playerNickname;

        String actionID = event.action;

        int pawnRow = event.pawnRow;
        int pawnColumn = event.pawnColumn;

        if (game.isValid(actionID) && game.isValidPawn(nickname, pawnRow, pawnColumn) && game.isValidPlayer(nickname)){

            List<Cell> availableCellsToForce = game.getOpponentsNeighboring(nickname, pawnRow, pawnColumn);
            List<Point> cellInfo = generatePointsByCells(availableCellsToForce);

            virtualView.sendMessageTo(nickname, new GivePossibleCellsToForceEvent(nickname, cellInfo, true));
        }
        else if ( !(game.isValid(actionID) && game.isValidPawn(nickname, pawnRow, pawnColumn)) ) {
            List<Action> possibleActions = game.getLastActionsList();
            List<String> actionsInfo = generateActionIDByActions(possibleActions);

            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, false));
        }

    }


    @Override
    public void manageEvent(ChosenFinishActionEvent event) {
        String nickname = event.playerNickname;

        String chosenAction = event.action;

        if( game.isValid(chosenAction) && game.isValidPlayer(nickname) ) {
            endTurn(false);
        }
        else if ( !(game.isValid(chosenAction)) ){
            List<String> actionsInfo = generateActionIDByActions((game.getLastActionsList()));
            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, false));
        }

    }


    @Override
    public void manageEvent(ChosenCellToMoveEvent event) {

        String nickname = event.playerNickname;

        int pawnRow = event.pawnRow;
        int pawnColumn = event.pawnColumn;

        int nextRow = event.nextRow;
        int nextColumn = event.nextColumn;

        if( game.isValid(nextRow, nextColumn) && game.isValidPawn(nickname, pawnRow, pawnColumn) && game.isValidPlayer(nickname)) {

            //THAT'S IMPORTANT!
            game.movePawn(nickname, pawnRow, pawnColumn, nextRow, nextColumn);

            newPossibleActions(nickname, nextRow, nextColumn);

        }
        else if ( !(game.isValid(nextRow, nextColumn) && game.isValidPawn(nickname, pawnRow, pawnColumn)) ){
            List<Cell> availableCellsToMove = game.wherePawnCanMove(nickname, pawnRow, pawnColumn);
            List<Point> cellInfo = generatePointsByCells(availableCellsToMove);

            virtualView.sendMessageTo(nickname, new GivePossibleCellsToMoveEvent(nickname, cellInfo,true));
        }

    }


    @Override
    public void manageEvent(ChosenCellToBuildEvent event) {
        String nickname = event.playerNickname;

        int row = event.pawnRow;
        int column = event.pawnColumn;

        int nextRow = event.nextRow;
        int nextColumn = event.nextColumn;

        //controlling if has been selected a valid cell
        if(game.isValidPawn(nickname, row, column) && game.isValid(nextRow, nextColumn) && game.isValidPlayer(nickname)) {


            //taking the available buildings on the selected cell
            List<Building> availableBuildings = game.getPossibleBuildingOnCell(nickname, nextRow, nextColumn);

            //controlling if there are available building
            if(availableBuildings.size() > 0) {
                List<Integer> buildingInfo = generateLevelByBuilding(availableBuildings);

                //sending the available buildings to the client
                virtualView.sendMessageTo(nickname, new GivePossibleBuildingsEvent(nickname, buildingInfo, true));
            }
            else {
                throw new RuntimeException("I don't really know what's happen here, please control some of your code! It's something with the buildings on a cell. :/");
            }
        }
        //if the selected cell to build is invalid i ask again the cell
        else if ( !(game.isValidPawn(nickname, row, column) && game.isValid(nextRow, nextColumn)) ){
            List<Point> cellsInfo = generatePointsByCells(game.getLastCellsList());
            virtualView.sendMessageTo(nickname, new GivePossibleCellsToBuildEvent(nickname, cellsInfo , false));
        }
    }


    @Override
    public void manageEvent(ChosenCellToDestroyEvent event) {
        String nickname = event.playerNickname;

        int pawnRow = event.pawnRow;
        int pawnColumn = event.pawnColumn;

        int row = event.rowToDestroy;
        int column = event.columnToDestroy;

        //controlling if has been selected a valid cell
        if (game.isValid(row, column) && game.isValidPawn(nickname, pawnRow, pawnColumn) && game.isValidPlayer(nickname)){

            game.destroyBlock(nickname, row, column);

            newPossibleActions(nickname, pawnRow, pawnColumn);


        } else if ( !(game.isValid(row, column) && game.isValidPawn(nickname, pawnRow, pawnColumn)) ) {
            List <Point> cellsInfo = generatePointsByCells(game.getLastCellsList());
            virtualView.sendMessageTo(nickname, new GivePossibleCellsToDestroyEvent(nickname, cellsInfo, false ));
        }

    }


    @Override
    public void manageEvent(ChosenCellToForceEvent event) {

       String nickname = event.playerNickname;

       int pawnRow = event.pawnRow;
       int pawnColumn = event.pawnColumn;

       int row = event.rowForcedPawn;
       int column = event.columnForcedPawn;

       if (game.isValid(row, column) && game.isValidPawn(nickname, pawnRow, pawnColumn) && game.isValidPlayer(nickname) ){

           game.forceOpponent(nickname, pawnRow, pawnColumn, row, column);

           List<Action> possibleActions = game.getPossibleActions(nickname, pawnRow, pawnColumn );
           List<String> actionsID = generateActionIDByActions(possibleActions);

           virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsID, true) );

       } else if ( !game.isValid(row, column) && game.isValidPawn(nickname, pawnRow, pawnColumn) ){

           List <Point> cellsInfo = generatePointsByCells(game.getLastCellsList());
           virtualView.sendMessageTo(nickname, new GivePossibleCellsToForceEvent(nickname, cellsInfo, false));
       }

    }


    @Override
    public void manageEvent(ChosenBuildingEvent event) {

        String nickname = event.playerNickname;
        int level = event.levelBuilding;
        int pawnRow = event.pawnRow;
        int pawnColumn = event.pawnColumn;
        int buildRow = event.buildRow;
        int buildColumn = event.buildColumn;

        if (game.isValid(buildRow, buildColumn) && game.isValid(level) && game.isValidPawn(nickname, pawnRow, pawnColumn) && game.isValidPlayer(nickname)) {

            game.pawnBuild(nickname, pawnRow, pawnColumn, buildRow, buildColumn, level);

            newPossibleActions(nickname, pawnRow, pawnColumn);

        }
        else if ( !(game.isValid(buildRow, buildColumn) && game.isValid(level) && game.isValidPawn(nickname, pawnRow, pawnColumn)) ) {
            List<Building> availableBuildings = game.getPossibleBuildingOnCell(nickname, buildRow, buildColumn);
            List<Integer> buildingInfo = generateLevelByBuilding(availableBuildings);

            virtualView.sendMessageTo(nickname, new GivePossibleBuildingsEvent(nickname, buildingInfo, false ));
        }
    }


    @Override
    public void manageEvent(VictoryEvent event) {

       endGame(event.winnerNickname);

    }


    // MARK : Support Methods Section ======================================================================================


    public Point generatePointByCell (Cell c){
        return new Point(c.getRowPosition(), c.getColumnPosition());
    }


    public List<Point> generatePointsByCells(List<Cell> cells){

        List <Point> points = new ArrayList<>();

        for (Cell c : cells){
            points.add(generatePointByCell(c));
        }
        return points;

    }


    public List<String> generateActionIDByActions(List<Action> actions){

        List<String> actionsID = new ArrayList<>();
        for (Action a : actions)
            actionsID.add(a.getActionID());
        return actionsID;
    }


    public List<Integer> generateLevelByBuilding(List<Building> buildings){

        List<Integer> levels = new ArrayList<>();
        for (Building b : buildings)
            levels.add(b.getLevel());
        return levels;
    }


    /**
     * USED ONLY FOR TESTING
     * @return the preGameLobby
     */
    public PreGameLobby getPreGameLobby() {
        return this.preGameLobby;
    }


    /**
     * USED ONLY FOR TESTING
     * @return the game
     */
    public Game getGame() {
        return this.game;
    }


}