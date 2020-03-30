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


    private String nameGodCard;


    private int numMove;


    private int numBuild;


    private Boolean canMoveUp;


    // ======================================================================================


    public BasicPlayer() {
        this.name = "nomeNullo";
        this.color = null;
        this.godCard = null;
        this.pawns = new Pawn[2];

        this.canMoveUp = true;

        this.numMove = 0;
        this.numBuild = 1;
    }


    public BasicPlayer(String name, Color color, String nameGodCard) {
        this.name = name;
        this.color = color;
        this.nameGodCard = nameGodCard;
        this.godCard = null;
        this.pawns = new Pawn[2];

        this.canMoveUp = true;

        this.numMove = 0;
        this.numBuild = 1;
    }


    // ======================================================================================


    @Override
    public String getName() {
        return name;
    }


    @Override
    public Color getColor() {
        return color;
    }


    @Override
    public Pawn[] getPawns() {
        return pawns;
    }


    @Override
    public Card getGodCard() {
        return godCard;
    }


    @Override
    public int getNumMove() {
        return numMove;
    }


    @Override
    public int getNumBuild() {
        return numBuild;
    }


    @Override
    public Boolean getCanMoveUp() {
        return canMoveUp;
    }


    // ======================================================================================


    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public void setColor(Color color) {
        this.color = color;
    }


    @Override
    public void setNameGod(String nameGod) {
        this.nameGodCard = nameGod;
    }


    @Override
    public void setCard(Card card) {
        this.godCard = card;
    }


    @Override
    public void setNumMove(int numMove) {
        this.numMove = numMove;
    }


    @Override
    public void setNumBuild(int numBuild) {
        this.numBuild = numBuild;
    }


    @Override
    public void setCanMoveUp(Boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }


    @Override
    public void resetNumMove() {
        this.numMove = 0;
    }


    @Override
    public void resetNumBuild() {
        this.numBuild = 0;
    }


    // ======================================================================================


    @Override
    public int movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition ) {


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
        }

        numMove++;
        numBuild--;

        placePawn( gameBoard, designatedPawn, nextPosition ); // place the pawn on the board in the new position



        return moveRetEncoded;
    }


    /**
     * the standard method that return the cells where a pawn can move
     * @param gameBoard the game board where the pawn have to move on
     * @param designatedPawn the pawn that's designated to move
     * @return the list of cells available to be move
     */
    @Override
    public ArrayList<Cell> wherePawnCanMove( Board gameBoard, Pawn designatedPawn ) {
        ArrayList<Cell> availableCellsToMove = gameBoard.getCellAvailableToMove( designatedPawn );

        if( !this.canMoveUp ) {
            for ( Cell c : availableCellsToMove ) {
                if( c.getHeight() - designatedPawn.getzPosition() == 1 ) {
                    availableCellsToMove.remove(c);
                }
            }
        }

        return availableCellsToMove;
    }


    @Override
    public void forcePawn(Pawn designatedPawn, Cell nextPosition) {
        designatedPawn.forcePawn(nextPosition);
    }


    // ======================================================================================


    @Override
    public void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, ArrayList<Building> buildings) {

        designatedPawn.pawnBuild();

        Building designatedBuilding = new Building();

        for (Building b : buildings) {
            if ( b.getLevel() == chosenLevel ) {
                designatedBuilding = b;
            }
        }

        numBuild++;

        designatedCell.buildOnThisCell(designatedBuilding);

    }


    /**
     * the standard method that return the cells where a pawn can build
     * @param gameBoard the game board where the pawn have to build on
     * @param designatedPawn the pawn that's designated to build
     * @return the list of cells available to be built
     */
    @Override
    public ArrayList<Cell> wherePawnCanBuild( Board gameBoard, Pawn designatedPawn ) {
        return gameBoard.getCellAvailableToBuild( designatedPawn );
    }


    @Override
    public ArrayList<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell, ArrayList<Building> buildings) {
        return gameBoard.getPossibleBuildingOnCell( designatedCell, buildings );
    }


    // ======================================================================================


    /**
     * this method initiate one male pawn and one female pawn and place them on the board
     * @param gameBoard the board where we have to place the pawns
     * @param sex the sex of the pawn
     * @param startCell the cell where to place the pawn initiated
     */
    @Override
    public void initPawn(Board gameBoard, Sex sex, Cell startCell ) {

        /* control if the pawn is already present */
        if( sex == Sex.MALE) {
            this.pawns[0] = new Pawn(this.color, sex, startCell);
            placePawn( gameBoard, this.pawns[0], startCell);
        }
        else if (sex == Sex.FEMALE){
            this.pawns[1] = new Pawn(this.color, sex, startCell);
            placePawn( gameBoard, this.pawns[1], startCell);
        }

    }


    @Override
    public ArrayList<Cell> getPawnsCoordinates(Board gameBoard) {
        ArrayList<Cell> retPawnsCells = new ArrayList<>();

        for (int i = 0; i < pawns.length; i++) {

            ArrayList<Cell> availableCellsToMove = wherePawnCanMove(gameBoard, pawns[i]);

            if( availableCellsToMove.size() != 0 ) {
                retPawnsCells.add(pawns[i].getPosition());
            }

        }

        if( retPawnsCells.size() == 0 ) {
            //TODO : PERSO
        }

        return retPawnsCells;
    }


    @Override
    public ArrayList<String> getPossibleAction( Board gameBoard, Pawn designatedPawn ) {

        ArrayList<String> possibleAction = new ArrayList<>();

        if(numBuild < 1) {
            possibleAction.add("build");
        }
        if (numMove < 1) {
            possibleAction.add("move");
        }
        if( numBuild == 1 && numMove == 1 ) {
            possibleAction.add("finish");
        }

        return possibleAction;
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





}
