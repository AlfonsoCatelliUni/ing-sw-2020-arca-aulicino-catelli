package it.polimi.ingsw.model;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.events.CTSEvents.VictoryEvent;
import it.polimi.ingsw.events.STCEvents.NotifyStatusEvent;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Player.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game extends Observable implements GameConsequenceHandler {


    private Board gameBoard;


    private List<Player> players;


    private Player currentPlayer;


    private int indexCurrentPlayer;


    // ======================================================================================
    // MARK : constructors


    public Game(List<String> playersNickname, List<Color> colors, Map<String, Card> nicknameCardMap, Map<String, Player> effectClassMap) {

        super();

        this.gameBoard = new Board();

        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.indexCurrentPlayer = 0;

        for (int i = 0; i < playersNickname.size(); i++) {

            players.add(effectClassMap.get(playersNickname.get(i)));

            players.get(i).setName(playersNickname.get(i));
            players.get(i).setColor(colors.get(i));
            players.get(i).setCard(nicknameCardMap.get(playersNickname.get(i)));

            //TODO : cambiare l'istanziazione dei pedoni
            players.get(i).initPawn(gameBoard, Sex.MALE, gameBoard.getCell(i, i));
            players.get(i).initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(i, i+1));

        }

        currentPlayer = players.get(indexCurrentPlayer);


    }


    //TODO : finish the standard Game constructor
    public Game(){

        super();

        this.gameBoard = new Board();

    }


    /* USED ONLY FOR TESTING */
    public Game(String playerName, String opponentName) {

        super();

        this.gameBoard = new Board();
        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.indexCurrentPlayer = 0;

        players.add(new BasicPlayer(playerName, Color.BLUE, new Card("God_Player", true, true, "effect_god")));
        players.add(new BasicPlayer(opponentName, Color.GREY, new Card("God_Opponent", true, true, "effect_god")));

        players.get(0).initPawn(gameBoard, Sex.MALE, gameBoard.getCell(0,0));
        players.get(0).initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(0,1));

        players.get(1).initPawn(gameBoard, Sex.MALE, gameBoard.getCell(4,4));
        players.get(1).initPawn(gameBoard, Sex.MALE, gameBoard.getCell(4,3));


    }


    // ======================================================================================
    // MARK : main functional methods


    /**
     * which are the pawn that i can at least move in this turn ?
     * if i can't move any pawn i lose the game
     * @return the cells of the pawn i can at least move
     */
    public List<Cell> getPawnsCoordinates(String playerName) {

        Player player = getPlayerByName(playerName);

        return player.getPawnsCoordinates(gameBoard);
    }


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
     * the current master move the pawn based on his decoration
     * @param row the row coordinate of the pawn that he wants to move
     * @param column the column coordinate of the pawn that he wants to move
     * @param newRow the row coordinate of the new position of the pawn
     * @param newColumn the column coordinate of the new position of the pawn
     */
    public void movePawn(String playerName, int row, int column, int newRow, int newColumn) {

        Player player = getPlayerByName(playerName);

        Consequence moveResult = player.movePawn(gameBoard, gameBoard.getPawnByCoordinates(row, column), gameBoard.getCell(newRow, newColumn));

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

        player.pawnBuild(designatedPawn, designatedCell, level, gameBoard.getBuildings());
    }


    /* devo eliminare il player nel caso non possa muovere nessuna pedina */
    public void removePlayer(String playerName) {

        Player player = getPlayerByName(playerName);

        Pawn[] pawns = player.getPawns();

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


    // ======================================================================================
    // MARK : consequence of a move

    @Override
    public void receiveConsequence(Consequence consequence) {
        consequence.accept(this);
    }


    @Override
    public void doConsequence(VictoryConsequence consequence) {
        updateAllObservers( new VictoryEvent(consequence.getWinnerNickname()) );
    }


    @Override
    public void doConsequence(BlockConsequence consequence) {

        /* block the other player */
        for (Player p : players) {
            if (!p.equals(currentPlayer)) {
                p.setCanMoveUp(false);
            }
        }

        //TODO : testare lambda function
        //players.stream().filter(p -> !p.getName().equals(consequence.getBlockerNickname())).forEach(p -> p.setCanMoveUp(false));

    }


    @Override
    public void doConsequence(NoConsequence consequence) {
        //nothing to do here :)
    }


    @Override
    public void doConsequence(DestroyTowersConsequence consequence) {
        gameBoard.destroyTowers();
    }


    // ======================================================================================
    // MARK : support methods


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


    public String newCurrentPlayer() {

        currentPlayer.resetPlayerStatus();

        nextCurrentPlayer();

        currentPlayer = players.get(indexCurrentPlayer);

        return players.get(indexCurrentPlayer).getName();

    }


    public String boardToString() {

        String retString = "";

        retString = ("╔═══╦════╦════╦════╦════╦════╗\n" +
                "║   ║ 1  ║ 2  ║ 3  ║ 4  ║ 5  ║\n" +
                "╠═══╬════╬════╬════╬════╬════╣\n" +
                "║ A ║ " + gameBoard.getStringCellInfo(0,0) + " ║ "
                + gameBoard.getStringCellInfo(0,1) + " ║ "
                + gameBoard.getStringCellInfo(0,2) + " ║ "
                + gameBoard.getStringCellInfo(0,3) + " ║ "
                + gameBoard.getStringCellInfo(0,4) + " ║\n" + /* printed the first line */
                "║ B ║ "+ gameBoard.getStringCellInfo(1,0) + " ║ "
                + gameBoard.getStringCellInfo(1,1) + " ║ "
                + gameBoard.getStringCellInfo(1,2) + " ║ "
                + gameBoard.getStringCellInfo(1,3) + " ║ "
                + gameBoard.getStringCellInfo(1,4) + " ║\n" + /* printed the second line */
                "║ C ║ " + gameBoard.getStringCellInfo(2,0) + " ║ "
                + gameBoard.getStringCellInfo(2,1) + " ║ "
                + gameBoard.getStringCellInfo(2,2) + " ║ "
                + gameBoard.getStringCellInfo(2,3) + " ║ "
                + gameBoard.getStringCellInfo(2,4) + " ║\n" + /* printed the third line */
                "║ D ║ " + gameBoard.getStringCellInfo(3,0) + " ║ "
                + gameBoard.getStringCellInfo(3,1) + " ║ "
                + gameBoard.getStringCellInfo(3,2) + " ║ "
                + gameBoard.getStringCellInfo(3,3) + " ║ "
                + gameBoard.getStringCellInfo(3,4) + " ║\n" + /* printed the fourth line */
                "║ E ║ " + gameBoard.getStringCellInfo(4,0) + " ║ "
                + gameBoard.getStringCellInfo(4,1) + " ║ "
                + gameBoard.getStringCellInfo(4,2) + " ║ "
                + gameBoard.getStringCellInfo(4,3) + " ║ "
                + gameBoard.getStringCellInfo(4,4) + " ║\n" + /* printed the fifth line */
                "╚═══╩════╩════╩════╩════╩════╝\n\n");


        return retString;
    }


    public void nextCurrentPlayer() {

        if( indexCurrentPlayer == players.size() - 1 ) {

            indexCurrentPlayer = 0;
            return;
        }

        indexCurrentPlayer++;

    }


    public void resetPlayerStatus(String playerName) {
        Player player = getPlayerByName(playerName);

        player.resetPlayerStatus();
    }


    public Player getPlayerByName(String nickname) {
        return getPlayers().stream().filter(p -> p.getName().equals(nickname)).findAny().orElse(null);
    }






}
