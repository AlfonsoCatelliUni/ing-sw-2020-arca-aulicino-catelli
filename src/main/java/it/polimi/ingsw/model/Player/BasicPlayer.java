package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;

import java.util.ArrayList;

public class BasicPlayer implements Player {


    private final int QUANTITY = 2;


    private String name;


    private Color color;


    private Card godCard;


    private Pawn[] pawns;


    private Boolean canBuildAgain;


    private Boolean canMoveAgain;


    // ======================================================================================


    public BasicPlayer() {
        this.name = "";
        this.color = null;
        this.godCard = null;
        this.pawns = new Pawn[QUANTITY];
    }


    public BasicPlayer(String name, Color color, String nameGodCard) {
        this.name = name;
        this.color = color;
        this.godCard = null;
        this.pawns = new Pawn[QUANTITY];
    }


    // ======================================================================================


    public String getName() {
        return name;
    }


    public Color getColor() {
        return color;
    }


    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public void setColor(Color color) {
        this.color = color;
    }


    @Override
    public void setCard(Card card) {
        this.godCard = card;
    }


    public Card getGodCard() {
        return godCard;
    }


    public Pawn[] getPawns() {
        return pawns;
    }


    // ======================================================================================


    /**
     * this method initiate one male pawn and one female pawn and place them on the board
     * @param gameBoard the board where we have to place the pawns
     * @param color the color of the pawn
     * @param sex the sex of the pawn
     * @param startCell the cell where to place the pawn initiated
     */
    @Override
    public void initPawn(Board gameBoard, Color color, Sex sex, Cell startCell ) {

        /* control if the pawn is already present */
        if( sex == Sex.MALE && pawns[0] == null) {
            this.pawns[0] = new Pawn(color, sex, startCell);
            placePawn( gameBoard, this.pawns[0], startCell);
        }
        else if (sex == Sex.FEMALE && pawns[1] == null){
            this.pawns[1] = new Pawn(color, sex, startCell);
            placePawn( gameBoard, this.pawns[1], startCell);
        }

    }


    @Override
    public void movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition ) {


        removePawn( gameBoard,  designatedPawn ); // remove the pawn from the game board

        int moveRetEncoded = 0; // save the return of Pawn.moveTo

        /* save the old height of the pawn to compare it with the new height to declare if there is a winner */
        int oldPawnHeight = designatedPawn.getPosition().getHeight();

        /* change the position in the pawn and set the propriety of the pawn ( hasMove, hasGoneUp, ... ) */
        designatedPawn.moveTo(nextPosition);

        /* this is the control for the victory moving from 2 to 3 level height,
         * only if it's not been forced to move in the position */
        if ( oldPawnHeight == 2 && nextPosition.getHeight() == 3
                && !designatedPawn.getForcedMove() && designatedPawn.getHasMoved() ) {
            moveRetEncoded = 1;
            //TODO : call the method winByMove (maybe we have to do this in Game class)
        }

        placePawn( gameBoard, designatedPawn, nextPosition ); // place the pawn on the board in the new position

    }


    //TODO : implements all of this methods


    @Override
    public void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, ArrayList<Building> buildings) {

    }


    @Override
    public void forcePawn(Pawn designatedPawn, Cell nextPosition) {

    }


    @Override
    public ArrayList<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        return null;
    }


    @Override
    public ArrayList<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        return null;
    }


    @Override
    public ArrayList<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell, ArrayList<Building> buildings) {
        return null;
    }


    /**
     * remove a pawn from the board
     * @param gameBoard we have to remove the pawn form this board
     * @param designatedPawn the pawn to remove
     */
    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {

        int rowPosition = designatedPawn.getPosition().getRowPosition();
        int columnPosition = designatedPawn.getPosition().getColumnPosition();

        gameBoard.getCell(rowPosition, columnPosition).freeCell();
    }


    /**
     * place the pawn on the board
     * @param gameBoard the board where to place the pawn
     * @param designatedPawn the pawn that we have to place
     * @param designatedCell the cell where place the pawn
     */
    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {

        int rowPosition = designatedCell.getRowPosition();
        int columnPosition = designatedCell.getColumnPosition();

        gameBoard.getCell(rowPosition, columnPosition).placePawnHere(designatedPawn);

    }


    @Override
    public ArrayList<String> getPossibleAction() {
        return null;
    }


}
