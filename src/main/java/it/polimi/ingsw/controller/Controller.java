package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.CTSEvents.*;
import it.polimi.ingsw.events.STCEvents.*;
import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveAction;
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


    // MARK : Controller Function Section ======================================================================================


    public void startGame (){
    // TODO: vedere come gestire i vari costruttori

       List<Color> colors = Color.getRandomColors(preGameLobby.getNumberOfPlayers());

       game = new Game(preGameLobby.getConnectedPlayers(), colors, preGameLobby.getPlayerCardMap(), preGameLobby.getEffectsClassMap() );

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

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(preGameLobby != null) {
                    int numberOfConnected = preGameLobby.getConnectedPlayers().size();
                    int lobbySize = preGameLobby.getNumberOfPlayers();

                    if (numberOfConnected < lobbySize) {
                        virtualView.sendMessage(new RoomNotFilled("Room Not Filled In Time!"));
                        virtualView.sendMessage(new DisconnectionEvent());
                    }
                }
            }
        }, 120000); // 2 minutes timer

    }


    public void firstTurnGame(){
        String firstPlayer = game.getPlayersNickname().get(0);

        List<Cell> availablePawnsCell = game.getAvailablePawns(firstPlayer);

        if (availablePawnsCell.size() == 0){
            virtualView.sendMessageTo(firstPlayer, new LosingByNoActionEvent(firstPlayer, "So Sad"));
        }
        else {
            List<Point> points = new ArrayList<>();
            for (Cell c : availablePawnsCell){
                points.add(new Point(c.getRowPosition(),c.getColumnPosition()));
            }

            virtualView.sendMessageTo(firstPlayer, new AskWhichPawnsUseEvent(firstPlayer, true, points));
        }
    }


    public void endTurn(){

        String playerTurnEnded = game.getCurrentPlayer().getName();

        int index = game.getPlayersNickname().indexOf(playerTurnEnded);
        index++;

        if (index < game.getPlayersNickname().size()){
            String nextPlayer = game.getPlayersNickname().get(index);

            List<Cell> availablePawnsCell = game.getAvailablePawns(nextPlayer);
            List<Point> points = new ArrayList<>();

            for(Cell c : availablePawnsCell)
                points.add(new Point(c.getRowPosition(), c.getColumnPosition()));

            virtualView.sendMessageTo(nextPlayer, new AskWhichPawnsUseEvent(nextPlayer, true, points));

        }
        else {
            firstTurnGame();
        }





    }


    // MARK : Network Event Manager Section ======================================================================================


    @Override
    public synchronized void manageEvent(NewConnectionEvent event) {

        Integer ID = event.ID;

        String nickname = event.nickname;

        Boolean isNicknameFree = preGameLobby.isNicknameAvailable(nickname);
        Boolean isNicknameValid = preGameLobby.isNicknameValid(nickname);


        if(isNicknameFree && isNicknameValid && !preGameLobby.getClosed()) {
            //TODO : la addPlayer in PreGameLobby potrebbe essere sync
            preGameLobby.addPlayer(nickname);
            virtualView.newNicknameID(nickname, ID);

            List<String> connectedPlayers = preGameLobby.getConnectedPlayers();


            //if there's only one player connected to the waitingRoom, than this is the first one
            //and he has to choose the number of the players for the new match
            if(connectedPlayers.size() == 1) {
                virtualView.sendMessageTo(nickname, new FirstConnectedEvent(nickname));
                countdownStart();
            }
            //if the waitingRoom is filled than we broadcast a message that communicates this event
            //and we ask the first entered player to choose his card
            else if(connectedPlayers.size() == preGameLobby.getNumberOfPlayers()) {

                virtualView.sendMessageTo(nickname, new SuccessfullyConnectedEvent(connectedPlayers, nickname));

                virtualView.sendMessage(new ClosedWaitingRoomEvent());

                List<String> cardsName = preGameLobby.getPickedCards().stream().map(Card::getName).collect(Collectors.toList());
                List<String> cardsEffect = preGameLobby.getPickedCards().stream().map(Card::getEffect).collect(Collectors.toList());

                String firstPlayer = connectedPlayers.get(0);

                virtualView.sendMessageTo(firstPlayer, new GivePossibleCardsEvent(firstPlayer, cardsName, cardsEffect,  true));
            }
            //if is an intermediate connection than nothing happens
            else {
                virtualView.sendMessageTo(nickname, new SuccessfullyConnectedEvent(connectedPlayers, nickname));
            }

        }
        //if the waitingRoom is already closed than we disconnect the player
        else if(preGameLobby.getClosed()) {
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

        Integer numberOfPlayers = event.number;

        List<String> connectedPlayers = preGameLobby.getConnectedPlayers();

        if(numberOfPlayers == 2 || numberOfPlayers == 3) {
            preGameLobby.setNumberOfPlayers(numberOfPlayers);

            virtualView.sendMessageTo(nickname, new SuccessfullyConnectedEvent(connectedPlayers, nickname));
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


        /* before I control that the selected spot is really free */
        if(isMaleSpotFree && isFemaleSpotFree) {
            game.initializePawn(event.playerNickname, event.malePawnRow, event.malePawnColumn);

            game.initializePawn(event.playerNickname, event.femalePawnRow, event.femalePawnColumn);

            int index = game.getPlayersNickname().indexOf(event.playerNickname);
            index++;

            if(index < game.getPlayersNickname().size()) {
                List <Cell> occupiedCell = game.getAllPawnsCoordinates();
                List<Point> info = generatePointsByCells(occupiedCell);
                virtualView.sendMessageTo(game.getPlayersNickname().get(index), new AskInitPawnsEvent(game.getPlayersNickname().get(index), true, info ));
            }
            else {
                firstTurnGame();
            }
        }
        else {
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
                        preGameLobby.getPickedCards().stream().map(Card::getEffect).collect(Collectors.toList()),
                        true));

            } else {
                startGame();
            }
        }
        else {
            virtualView.sendMessageTo(event.playerNickname, new GivePossibleCardsEvent(event.playerNickname,
                    preGameLobby.getPickedCards().stream().map(Card::getName).collect(Collectors.toList()),
                    preGameLobby.getPickedCards().stream().map(Card::getEffect).collect(Collectors.toList()),
                    false));
        }



    }


    @Override
    public void manageEvent(ChosenPawnToUseEvent event) {

        String player = event.playerNickname;
        int row = event.pawnRow;
        int column = event.pawnColumn;

        boolean isValid = game.isValid(row, column);

        if (isValid) {

            List<String> actionsInfo = generateActionIDByActions(game.getPossibleActions(player, row, column));

            virtualView.sendMessageTo(player, new GivePossibleActionsEvent(player, actionsInfo, true));

        } else {
            List<Cell> availablePawnsCell = game.getAvailablePawns(player);

            List<Point> points = new ArrayList<>();

            for(Cell c: availablePawnsCell)
                points.add(new Point(c.getRowPosition(), c.getColumnPosition()));

            virtualView.sendMessageTo(player, new AskWhichPawnsUseEvent(player, false, points));

        }
    }


    @Override
    public void manageEvent(ChosenMoveActionEvent event) {

        String nickname = event.playerNickname;
        String chosenAction = event.action;
        int row = event.pawnRow;
        int column = event.pawnColumn;


        if( game.isValid(chosenAction) ) {

            List<Cell> availableCellsToMove = game.wherePawnCanMove(nickname, row, column);
            List<Point> cellInfo = generatePointsByCells(availableCellsToMove);

            virtualView.sendMessageTo(nickname, new GivePossibleCellsToMoveEvent(nickname, cellInfo,true));
        }
        else {
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
        if( game.isValid(chosenAction) ) {
            //if it's ok the game automatically return the cells available to this action

            List<Cell> availableCellsToBuild = game.wherePawnCanBuild(nickname, row, column);
            List<Point> cellsInfo = generatePointsByCells(availableCellsToBuild);

            virtualView.sendMessage(new GivePossibleCellsToBuildEvent(nickname, cellsInfo, true));
        }
        else {
            //if it's not ok than the game automatically return the last possible actions list
            //because the game state isn't changed
            List<Action> possibleActions = game.getLastActionsList();
            List<String> actionsInfo = generateActionIDByActions(possibleActions);

            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, false));
        }
    }



    //TODO : da completare
    @Override
    public void manageEvent(ChosenDestroyActionEvent event) {

    }


    @Override
    public void manageEvent(ChosenForceActionEvent event) {

    }


    @Override
    public void manageEvent(ChosenFinishActionEvent event) {
        String nickname = event.playerNickname;

        String chosenAction = event.action;

        if( game.isValid(chosenAction) ) {
            //TODO : devo chiamare il next current player
            game.nextCurrentPlayer();

            //virtualView.sendMessageTo();
        }
        else {
            List<String> actionsInfo = generateActionIDByActions((game.getLastActionsList()));
            virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, false));
        }

    }


    @Override
    public void manageEvent(ChosenCellToMoveEvent event) {

        String nickname = event.playerNickname;

        int row = event.pawnRow;
        int column = event.pawnColumn;

        int nextRow = event.nextRow;
        int nextColumn = event.nextColumn;

        if(game.isValidCoordinate(row, column) &&  game.isValid(nextRow, nextColumn)) {
            //THAT'S IMPORTANT!
            game.movePawn(nickname, row, column, nextRow, nextColumn);

            List<Action> availableActions = game.getPossibleActions(nickname, nextRow, nextColumn);
            if(availableActions.size() > 0) {
                List<String> actionsInfo = generateActionIDByActions(availableActions);
                virtualView.sendMessageTo(nickname, new GivePossibleActionsEvent(nickname, actionsInfo, true));
            }
            else {
                virtualView.sendMessageTo(nickname, new LosingByNoActionEvent(nickname, "OMG! YOU ARE SO BAD AT THIS GAME! LOSER!"));
            }
        }
        else {
            List<Cell> availableCellsToMove = game.wherePawnCanMove(nickname, row, column);
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
        if(game.isValidCoordinate(row, column) && game.isValid(nextRow, nextColumn)) {
            //THAT'S IMPORTANT!

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
        else {
            List<Point> cellsInfo = generatePointsByCells(game.getLastCellsList());
            virtualView.sendMessageTo(nickname, new GivePossibleCellsToBuildEvent(nickname, cellsInfo , false));
        }
    }


    @Override
    public void manageEvent(ChosenBuildingEvent event) {

        String player = event.playerNickname;
        int level = event.levelBuilding;
        int pawnRow = event.pawnRow;
        int pawnColumn = event.pawnColumn;
        int buildRow = event.buildRow;
        int buildColumn = event.buildColumn;

        if (game.isValid(buildRow, buildColumn) && game.isValid(level)) {
            // IMPORTANT
            game.pawnBuild(player, pawnRow, pawnColumn, buildRow, buildColumn, level);

            List <Action> possibleActions = game.getPossibleActions(player, pawnRow, pawnColumn);
            if (possibleActions.size()>0) {
                List<String> actionsInfo = generateActionIDByActions(possibleActions);
                virtualView.sendMessageTo(player, new GivePossibleActionsEvent(player, actionsInfo, true));
            }
            else {
                virtualView.sendMessageTo(player, new LosingByNoActionEvent(player, "So sad bro"));
            }
        }
        else {
            List<Building> availableBuildings = game.getPossibleBuildingOnCell(player, buildRow, buildColumn);
            List<Integer> buildingInfo = generateLevelByBuilding(availableBuildings);

            virtualView.sendMessageTo(player, new GivePossibleBuildingsEvent(player, buildingInfo, false ));
        }
    }


    @Override
    public void manageEvent(VictoryEvent event) {

        Player winnerPlayer = game.getPlayerByName(event.winnerNickname);

       // TODO : controllare

        virtualView.sendMessage(new EndGameSTCEvent(winnerPlayer.getName()));

        virtualView.sendMessage(new DisconnectionEvent());



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




}
