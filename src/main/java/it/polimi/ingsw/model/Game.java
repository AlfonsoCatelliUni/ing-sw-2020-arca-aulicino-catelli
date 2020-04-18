package it.polimi.ingsw.model;

import it.polimi.ingsw.events.CTSEvents.VictoryEvent;
import it.polimi.ingsw.events.STCEvents.NotifyStatusEvent;
import it.polimi.ingsw.model.Consequence.*;
import it.polimi.ingsw.model.Player.Effect.BasicEffect;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Effect.NotMoveUpEffect;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game extends Observable implements GameConsequenceHandler {


    private Board gameBoard;


    private List<Player> players;


    private Player currentPlayer;

    private int indexCurrentPlayer;


    // MARK : Constructors ======================================================================================


    /**
     * constructor of the Game class
     * @param playersNickname is the list of players that will play this game
     * @param colors is the enumeration of possible colors assignable to the players
     * @param nicknameCardMap is where is mapped a nickname's player to his chosen card
     * @param playerDecoratorMap is where is mapped a god's name to his decoration
     * @param playerPawnPoints is where is mapped a nickname's player to the coordinates of his pawns
     */
    public Game(List<String> playersNickname, List<Color> colors, Map<String, Card> nicknameCardMap, Map<String, Effect> playerDecoratorMap, Map<String, List<Point>> playerPawnPoints) {

        super();

        this.gameBoard = new Board();

        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.indexCurrentPlayer = 0;

        for (int i = 0; i < playersNickname.size(); i++) {

            players.add(new Player(playersNickname.get(i), colors.get(i), nicknameCardMap.get(playersNickname.get(i)), playerDecoratorMap.get(playersNickname.get(i))));

            // here i save the initial cells of the two pawns
            Cell firstPawnCell = gameBoard.getCell(playerPawnPoints.get(playersNickname.get(i)).get(0).x, playerPawnPoints.get(playersNickname.get(i)).get(0).y);
            Cell secondPawnCell = gameBoard.getCell(playerPawnPoints.get(playersNickname.get(i)).get(1).x, playerPawnPoints.get(playersNickname.get(i)).get(1).y);

            // first placing of the pawn into the board
            players.get(i).initPawn(gameBoard, firstPawnCell);
            players.get(i).initPawn(gameBoard, secondPawnCell);

        }

        currentPlayer = players.get(indexCurrentPlayer);


    }


    public Game() {

        super();

        this.gameBoard = new Board();

        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.indexCurrentPlayer = 0;

    }


    /* USED ONLY FOR TESTING */
    public Game(String playerName, String opponentName) {

        super();

        this.gameBoard = new Board();
        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.indexCurrentPlayer = 0;


        players.add(new Player(playerName, Color.BLUE, new Card("God_Player", true, "effect_god"), new BasicEffect()));
        players.add(new Player(opponentName, Color.GREY, new Card("God_Player", true, "effect_god"), new BasicEffect()));

        players.get(0).initPawn(gameBoard, gameBoard.getCell(0,0));
        players.get(0).initPawn(gameBoard, gameBoard.getCell(0,1));

        players.get(1).initPawn(gameBoard, gameBoard.getCell(4,4));
        players.get(1).initPawn(gameBoard, gameBoard.getCell(4,3));


    }


    // MARK : Main Functional Methods ======================================================================================


    /**
     * which are the possible actions that i can do with the selected pawn
     * if i can't do any move with the pawn i lose the game
     * @param row the row coordinate of the pawn
     * @param column the column coordinate of the pawn
     * @return the list of possible actions
     */
    public List<Action> getPossibleActions(String playerName, int row, int column) {

        Player player = getPlayerByName(playerName);

        return player.getPossibleActions(gameBoard, gameBoard.getPawnByCoordinates(row, column));
    }


    /**
     * where the pawn can be moved based on its position?
     * @param row the row position of the pawn you want the list
     * @param column the column position of the pawn you want the list
     * @return the list of cells where this pawn can be moved
     */
    public List<Cell> wherePawnCanMove(String playerName, int row, int column ){

        Player player = getPlayerByName(playerName);

        return player.wherePawnCanMove(gameBoard, gameBoard.getPawnByCoordinates(row, column));
    }


    /**
     * where the pawn can build based on its position ?
     * @param row the row coordinate of the pawn
     * @param column the column coordinate of the pawn
     * @return a list of cells where the pawn can build
     */
    public List<Cell> wherePawnCanBuild(String playerName, int row, int column ) {

        Player player = getPlayerByName(playerName);

        return player.wherePawnCanBuild(gameBoard, gameBoard.getPawnByCoordinates(row, column));
    }


    public List<Cell> wherePawnCanForce(String playerName, int row, int column){

        Player player = getPlayerByName(playerName);

        return player.wherePawnCanForce(gameBoard, gameBoard.getPawnByCoordinates(row,column));
    }


    /**
     * which are the possible type of buildings that can be built on this position ?
     * @param row the row coordinate the position
     * @param column the column coordinate of the position
     * @return a list of type of buildings
     */
    public List<Building> getPossibleBuildingOnCell(String playerName, int row, int column) {

        Player player = getPlayerByName(playerName);

        return player.getPossibleBuildingOnCell(gameBoard, gameBoard.getCell(row, column));
    }


    /**
     * the current master <3 move the pawn based on his decoration
     * @param row the row coordinate of the pawn that he wants to move
     * @param column the column coordinate of the pawn that he wants to move
     * @param newRow the row coordinate of the new position of the pawn
     * @param newColumn the column coordinate of the new position of the pawn
     */
    public void movePawn(String playerName, int row, int column, int newRow, int newColumn) {

        Player player = getPlayerByName(playerName);

        Consequence moveResult = player.move(gameBoard, gameBoard.getPawnByCoordinates(row, column), gameBoard.getCell(newRow, newColumn));

        updateAllObservers( new NotifyStatusEvent(generateStatusJson()) );

        receiveConsequence(moveResult);
    }


    /**
     * the current master build based on the position of the pawn he has moved
     * @param pawnRow the row coordinate of the pawn that have to build
     * @param pawnColumn the column coordinate of the pawn that have to build
     * @param buildRow the row coordinate of the cell that i want to build in
     * @param buildColumn the column coordinate of the cell that i want to build in
     * @param level the level of the building that i want build, the level indicate a unique building
     */
    public void pawnBuild(String playerName, int pawnRow, int pawnColumn, int buildRow, int buildColumn, int level ){

        Player player = getPlayerByName(playerName);

        Pawn designatedPawn = gameBoard.getPawnByCoordinates(pawnRow, pawnColumn);
        Cell designatedCell = gameBoard.getCell(buildRow, buildColumn);

        player.build(designatedPawn, designatedCell, level, gameBoard.getBuildings());
    }


    public void forceOpponent(String playerName, int pawnRow, int pawnColumn, int opponentRow, int opponentColumn){
        Cell nextPosition = gameBoard.getSymmetrical(gameBoard.getCell(pawnRow,pawnColumn), gameBoard.getCell(opponentRow, opponentColumn));

        Player player = getPlayerByName(playerName);

        player.force(gameBoard.getPawnByCoordinates(opponentRow,opponentColumn), nextPosition);

    }


    /* devo eliminare il player nel caso non possa muovere nessuna pedina */
    public void removePlayer(String playerName) {

        Player player = getPlayerByName(playerName);

        List<Pawn> pawns = player.getPawns();

        //remove the pawns of the losing player from the game board
        for( Pawn p : pawns ) {
            player.removePawn(gameBoard, p);
        }

        //remove the losing player from the players list
        players.removeIf(p -> p.equals(player));

        //pick the next player
        nextCurrentPlayer();
        currentPlayer = players.get(indexCurrentPlayer);

        //if there's only one player, this player is the winner
        if( players.size() == 1 ) {
            receiveConsequence(new VictoryConsequence( players.get(0).getName() ));
        }

    }


    /**
     * puts in a json file all the info of the game in a give moment
     * @return the status of the game in a given moment
     */
    public String generateStatusJson(){

        String statusString = "";

        JSONObject obj = new JSONObject();

        JSONArray jsonGameBoard = new JSONArray();

        /* pass each cell of the board and format the information */
        for(int row = 0; row <= 4; row++) {
            for(int column = 0; column <= 4; column++) {

                Cell cell = gameBoard.getCell(row, column);
                Building roof = cell.getRoof();
                Pawn pawn = null;

                if(cell.getPawnInThisCell() != null) {
                    pawn = cell.getPawnInThisCell();
                }

                JSONObject cellObj = new JSONObject();

                /* writing the cell coordinate into the Json */
                cellObj.put("height", cell.getHeight());
                cellObj.put("column", cell.getColumnPosition());
                cellObj.put("row", cell.getRowPosition());


                JSONObject roofObj = new JSONObject();

                /* writing the roof information into the Json */
                roofObj.put("level", roof.getLevel());
                roofObj.put("isDome", roof.getIsDome());

                cellObj.put("roof", roofObj);

                /* write the pawn info, if it's present in this cell */
                if(pawn == null) {
                    cellObj.put("pawn", "null");
                }
                else {
                    JSONObject pawnObj = new JSONObject();

                    pawnObj.put("color", pawn.getColor());
                    pawnObj.put("sex", pawn.getSex());
                    pawnObj.put("hasMoved", pawn.getHasMoved());
                    pawnObj.put("hasBuilt", pawn.getHasBuilt());
                    pawnObj.put("forcedMoved", pawn.getForcedMove());
                    pawnObj.put("goneUp", pawn.getGoneUp());

                    cellObj.put("pawn", pawnObj);
                }

                /* add the cell in the gameBoard JsonObject */
                jsonGameBoard.add(cellObj);
            }
        }


        JSONArray playersJson = new JSONArray();
        for( Player p : players ){
            JSONObject playerObj = new JSONObject();

            playerObj.put("name", p.getName());
            //playerObj.put("god", p.getGodCard().getName());

            playersJson.add(playerObj);
        }


        obj.put("players", playersJson);
        obj.put("gameBoard", jsonGameBoard);

        statusString = obj.toString();

        return statusString;
    }


    // MARK : Consequence of an Action ======================================================================================


    /**
     * receives the consequence of an action
     * this method is a part of the visitor pattern
     * once the consequence is received, is handled by the game handler
     * @param consequence is a consequence of an action
     */
    @Override
    public void receiveConsequence(Consequence consequence) {
        consequence.accept(this);
    }

    /**
     * This method notifies all the observers that there is a winner for this game
     * with a victory event with the nickname's winner
     * @param consequence is the consequence received from the handler
     */
    @Override
    public void doConsequence(VictoryConsequence consequence) {
        updateAllObservers( new VictoryEvent(consequence.getNickname()) );
    }


    /**
     * Blocks all the players from moving up the next turn, less the one who invoked this method
     * @param consequence is the consequence received from the handler
     */
    @Override
    public void doConsequence(BlockConsequence consequence) {
        players.stream().filter(p -> !p.getName().equals(consequence.getNickname())).forEach(p -> p.setEffect(new NotMoveUpEffect(p.getEffect())));
    }


    /**
     * This method do nothing because there is no consequence from the action who invoked this
     * @param consequence is the consequence received from the handler
     */
    @Override
    public void doConsequence(NoConsequence consequence) {
        //nothing to do here :(
    }


    /**
     * this method calls the destroyTowers method from the Board class
     * @param consequence is the consequence received from the handler
     */
    @Override
    public void doConsequence(DestroyTowersConsequence consequence) {
        gameBoard.destroyTowers();
    }


    // MARK : Support Methods ======================================================================================


    public List<String> getAllNames() {

        List<String> names = new ArrayList<>();

        for (Player p : players ) {
            names.add(p.getName());
        }

        return names;
    }


    public List<Player> getPlayers() {
        return players;
    }


    public List<Cell> getAvailablePawns(String nickname) {

        Player player = getPlayerByName(nickname);
        List<Pawn> playerPawns = player.getPawns();

        List<Action> possibleActions = new ArrayList<>();
        List<Cell> availablePawns = new ArrayList<>();

        for (Pawn p : playerPawns ) {
            possibleActions = player.getPossibleActions(gameBoard, p);

            if (possibleActions.size() != 0) {
                availablePawns.add(p.getPosition());
            }

            possibleActions.clear();
        }

        return new ArrayList<>(availablePawns);
    }


    /**
     * sets the current player of this turn
     * @return the new current player for this turn
     */
    public String newCurrentPlayer() {

        currentPlayer.resetPlayerStatus();

        nextCurrentPlayer();

        currentPlayer = players.get(indexCurrentPlayer);

        return players.get(indexCurrentPlayer).getName();

    }

    
    /**
     * sets the index of the next current player in the arraylist of players
     */
    public void nextCurrentPlayer() {

        if( indexCurrentPlayer == players.size() - 1 ) {

            indexCurrentPlayer = 0;
            return;
        }

        indexCurrentPlayer++;

    }


    /**
     * this method only invokes the resetPlayerStatus in the Player class
     * @param playerName is the nickname of the player that has to be reset
     */
    public void resetPlayerStatus(String playerName) {
        Player player = getPlayerByName(playerName);

        player.resetPlayerStatus();
    }


    /**
     * @param nickname is the nickname of the player that I want
     * @return the player with the given nickname
     */
    public Player getPlayerByName(String nickname) {
        return getPlayers().stream().filter(p -> p.getName().equals(nickname)).findAny().orElse(null);
    }


    /**
     * this method is used when there is a DestroyBlockPlayer
     * @param playerName is the name of the player
     * @param row is the row of the cell where a block will be destroyed
     * @param column is the column od the cell where a block will be destroyed
     */
    public void destroyBlock(String playerName, int row, int column) {

        Player player = getPlayerByName(playerName);

        player.destroy(gameBoard, gameBoard.getCell(row, column));
    }


    /**
     * this method is used when there is a DestroyBlockPlayer
     * @param playerNickname is the name of the DestroyBlockPlayer
     * @return the list of cells where the not-moved pawn can destroy a block
     */
    public List<Cell> wherePawnCanDestroy(String playerNickname) {

        Player player = getPlayerByName(playerNickname);

        Pawn notMovedPawn = player.getPawns().stream().filter(pawn -> !pawn.getHasMoved()).findAny().get();

        return player.wherePawnCanDestroy(gameBoard, notMovedPawn);

    }


}
