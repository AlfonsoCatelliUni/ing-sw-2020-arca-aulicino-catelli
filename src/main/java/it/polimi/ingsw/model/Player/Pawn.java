package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;

/**
 * this is the Worker, each player has two pawns to play with
 */
public class Pawn {


    /**
     * color of the constructor, taken from the enum Color
     */
    private Color color;


    /**
     * sex of the constructor, taken from the enum Sex
     */
    private Sex sex;


    /**
     * pawn positioned in this cell
     */
    private Cell position;


    /**
     * height of the constructor in the [row, column] position
     */
    private int height;


    /**
     * has the player moved?
     */
    private Boolean hasMoved;


    /**
     * has the pawn built a construction ?
     */
    private Boolean hasBuilt;


    /**
     * has the pawn gone up? (zPosition + 1)
     */
    private Boolean goneUp;


    /**
     * has the pawn been forced to move by an enemy?
     */
    private Boolean forcedMove;


    // ======================================================================================


    /**
     * Standard constructor
     * @param color the right enum of the color of the pawn
     * @param sex the right enum of the sex of the pawn
     * @param startCell the initial cell of the pawn in the Board
     */
    public Pawn(Color color, Sex sex, Cell startCell) {
        this.color = color;
        this.sex = sex;

        this.position = startCell;
        this.height = startCell.getHeight();

        this.hasMoved = false;
        this.hasBuilt = false;
        this.goneUp = false;
        this.forcedMove = false;


    }


    // ======================================================================================


    /**
     * can i have the color of this pawn ?
     * @return the color of the pawn
     */
    public Color getColor() {
        return color;
    }


    /**
     * can i have the sex of the pawn ?
     * @return the sex of this pawn
     */
    public Sex getSex() {
        return sex;
    }


    /**
     * can i have the position of this pawn ?
     * @return the cell that represent the position of the pawn
     */
    public Cell getPosition() {
        return position;
    }


    /**
     * can i have the height level of this pawn ?
     * @return the height of the cell where the pawn actually is
     */
    public int getHeight() {
        return height;
    }


    /**
     * has the pawn been moved ?
     * @return true if it's been moved
     */
    public Boolean getHasMoved() {
        return hasMoved;
    }


    /**
     * has the pawn built a construction ?
     * @return true if it has built a construction
     */
    public Boolean getHasBuilt() {
        return hasBuilt;
    }


    /**
     * has the pawn gone up ?
     * @return true if it's gone up
     */
    public Boolean getGoneUp() {
        return goneUp;
    }


    /**
     * has the pawn been forced to move ?
     * @return true if it's been forced to move
     */
    public Boolean getForcedMove() {
        return forcedMove;
    }


    // ======================================================================================


    // ONLY USED FOR TESTING
    public void setHeight(int height) {
        this.height = height;
    }


    // ======================================================================================


    /**
     * actually move the pawn, changing its state
     * @param nextPosition is the cell where the pawn will move to
     * @return the cell where the pawn moved to
     */
    public Cell moveTo(Cell nextPosition) {

        this.hasMoved = true;
        this.forcedMove = false;

        /* potendo scendere di 1 o piu livelli devo controllare che
         * la poszione successiva sia ad un livello minore dell'attuale */
        if( nextPosition.getHeight() < this.height ) {
            this.goneUp = false;
        }
        else if(nextPosition.getHeight() == this.height + 1) { //salito
            this.goneUp = true;
        }

        this.height = nextPosition.getHeight();
        this.position = nextPosition;


        return nextPosition;
    }


    /**
     * the pawn has built in this turn
     */
    public void pawnBuild() {
        this.hasBuilt = true;
    }


    /**
     * this method forces the pawn and return void because there is no consequence (victory or block) if you force a pawn
     * @param nextPosition the cell where the pawn is forced to
     */
    public void forcePawn(Cell nextPosition) {

        this.hasMoved = false;
        this.forcedMove = true;

        this.position = nextPosition;
        this.height = nextPosition.getHeight();
    }


    /**
     * this method resets the default value of the pawn when player ends turn
     */
    public void resetPawnStatus() {

        this.hasMoved = false;
        this.hasBuilt = false;

        this.goneUp = false;
        this.forcedMove = false;

    }



}
