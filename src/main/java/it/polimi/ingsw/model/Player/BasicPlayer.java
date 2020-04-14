package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;

import java.util.ArrayList;
import java.util.List;

/**
 * this is the class of player basic, with the standard method, this class will be decorated according
 * to the chosen card
 */
public class BasicPlayer implements Player {

    /**
     * number of the pawn each player has
     */
    private final int QUANTITY = 2;


    /**
     * nickname of the player
     */
    private String name;


    /**
     * color of the pawn the player has
     */
    private Color color;


    /**
     * Card chosen by the player
     */
    private Card godCard;


    /**
     * Pawns the player has to play with
     */
    private Pawn[] pawns;


    /**
     * counter of the num of move the player did during his turn
     */
    private int numMove;


    /**
     * counter of the num of build the player did during his turn
     */
    private int numBuild;


    /**
     * if the player can move up during the game, this attribute is true
     */
    private Boolean canMoveUp;




    // ======================================================================================


    public BasicPlayer() {
        this.name = "";
        this.color = null;
        this.godCard = null;
        this.pawns = new Pawn[QUANTITY];

        this.canMoveUp = true;

        this.numMove = 0;
        this.numBuild = 1;

    }

    // ONLY USED FOR TESTING
    public BasicPlayer(String name, Color color, Card godCard) {
        this.name = name;
        this.color = color;

        this.godCard = godCard;

        this.pawns = new Pawn[QUANTITY];

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
    public Pawn getPawnInCoordinates(int row, int column) {

        for ( Pawn p : pawns ) {
            if( p.getPosition().getRowPosition() == row && p.getPosition().getColumnPosition() == column ) {
                return p;
            }
        }

        return null;
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
    public void setCard(Card card) {
        this.godCard = card;
    }


    @Override
    public void setCanMoveUp(Boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }


    @Override
    public void setNumMove(int numMove) {
        this.numMove = numMove;
    }


    @Override
    public void setNumBuild(int numBuild) {
        this.numBuild = numBuild;
    }


    /**
     * this method resets the status of the player when his turn ends
     */
    @Override
    public void resetPlayerStatus() {

        this.canMoveUp = true;

        this.numMove = 0;
        this.numBuild = 1;

        for (Pawn p : pawns ) {
            p.resetPawnStatus();
        }

    }


    // ======================================================================================

    /**
     * the standard method that move designated pawn, it's the default move
     * @param gameBoard the game board where the pawn have to move to
     * @param designatedPawn the pawn that's designated to move
     * @return the Consequence created by the move
     */
    @Override
    public Consequence movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition ) {


        removePawn( gameBoard,  designatedPawn ); // remove the pawn from the game board

        /* save the old height of the pawn to compare it with the new height to declare if there is a winner */
        int oldPawnHeight = designatedPawn.getPosition().getHeight();

        /* change the position in the pawn and set the propriety of the pawn ( hasMove, hasGoneUp, ... ) */
        designatedPawn.moveTo(nextPosition);

        numMove++;
        numBuild--;

        placePawn( gameBoard, designatedPawn, nextPosition ); // place the pawn on the board in the new position

        /* this is the control for the victory moving from 2 to 3 level height,
         * only if it's not been forced to move in the position */
        if ( oldPawnHeight == 2 && nextPosition.getHeight() == 3
                && !designatedPawn.getForcedMove() && designatedPawn.getHasMoved() ) {
            return new VictoryConsequence(name);
        }

        return new NoConsequence();
    }


    /**
     * the standard method that return the cells where a pawn can move
     * @param gameBoard the game board where the pawn have to move on
     * @param designatedPawn the pawn that's designated to move
     * @return the list of cells available to be move
     */
    @Override
    public List<Cell> wherePawnCanMove( Board gameBoard, Pawn designatedPawn ) {

        List<Cell> availableCellsToMove = gameBoard.getCellAvailableToMove( designatedPawn );

        if(!this.canMoveUp) {
            availableCellsToMove.removeIf(c -> c.getHeight() - designatedPawn.getHeight() == 1);
        }


        return availableCellsToMove;
    }


    /**
     * this method forces the pawn and return void because there is no consequence (victory or block) if you force a pawn
     * @param designatedPawn the pawn forced
     * @param nextPosition the cell where the pawn is forced to
     */
    @Override
    public void forcePawn(Pawn designatedPawn, Cell nextPosition) {
        designatedPawn.forcePawn(nextPosition);
    }


    // ======================================================================================


    /**
     * the standard method that build a building by the designated pawn, it's the default build
     * @param designatedPawn the pawn that's designated to move
     * @param designatedCell the game board where the pawn have to move to
     * @param chosenLevel the level of the building
     * @param buildings the list of the four buildings
     * @return the Consequence created by the build
     */
    @Override
    public Consequence pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        designatedPawn.pawnBuild();

        Building designatedBuilding = buildings.get(0);

        for (Building b : buildings) {
            if ( b.getLevel() == chosenLevel ) {
                designatedBuilding = b;
            }
        }

        numBuild++;

        designatedCell.buildOnThisCell(designatedBuilding);


        if( !designatedBuilding.isAvailable()) {
            return new DestroyTowersConsequence();
        }

        return new NoConsequence();
    }


    /**
     * the standard method that return the cells where a pawn can build
     * @param gameBoard the game board where the pawn have to build on
     * @param designatedPawn the pawn that's designated to build
     * @return the list of cells available to be built
     */
    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        return gameBoard.getCellAvailableToBuild( designatedPawn );
    }

    /**
     * this method returns the list of possible building on the designatedCell
     * @param designatedCell the cell that i've chosen to build on
     * @return the list of possible building on the designated cell
     */
    @Override
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell) {
        return gameBoard.getPossibleBuildingOnCell( designatedCell );
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
        if( sex == Sex.MALE && this.getPawns()[0] == null) {
            this.pawns[0] = new Pawn(this.color, sex, startCell);
            placePawn( gameBoard, this.pawns[0], startCell);
        }
        else if (sex == Sex.FEMALE && this.getPawns()[1] == null){
            this.pawns[1] = new Pawn(this.color, sex, startCell);
            placePawn( gameBoard, this.pawns[1], startCell);
        }

    }


    /**
     * this method returns the cells only of the pawns of the player that can move
     * @param gameBoard the gameBoard where the pawns are on
     * @return List of the cells
     */
    @Override
    public List<Cell> getPawnsCoordinates(Board gameBoard) {

        List<Cell> retPawnsCells = new ArrayList<>();
        List<Cell> availableCellsToMove;

        for (Pawn pawn : pawns) {

            availableCellsToMove = wherePawnCanMove(gameBoard, pawn);

            if (availableCellsToMove.size() != 0) {
                retPawnsCells.add(pawn.getPosition());
            }

            availableCellsToMove.clear();
        }

        return retPawnsCells;
    }

    /**
     * return which are the possible actions that i can do with the selected pawn
     * if i can't do any move with the pawn i lose the game, it's the basic possible action,
     * it will by decorated according with the selected God card
     * @param gameBoard the board of the game
     * @param designatedPawn the pawn selected to generate the possible actions
     * @return the list of possible actions
     */
    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> availableActions = new ArrayList<>();


        if (numMove == 0) {
            if ( wherePawnCanMove(gameBoard, designatedPawn).size() > 0 )
                availableActions.add(new MoveAction());

        }

        if (numBuild == 0) {
            if ( wherePawnCanBuild(gameBoard, designatedPawn).size() > 0 )
                availableActions.add(new BuildAction());
        }

        if (numMove == 1 && numBuild == 1)
            availableActions.add(new FinishAction());

        return availableActions;

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
