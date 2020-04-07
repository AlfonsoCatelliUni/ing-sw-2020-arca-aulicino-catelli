package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;

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


    public Pawn(Color color, Sex sex, Cell starCell) {
        this.color = color;
        this.sex = sex;

        this.position = starCell;
        this.height = starCell.getHeight();

        this.hasMoved = false;
        this.hasBuilt = false;
        this.goneUp = false;
        this.forcedMove = false;


    }


    public Pawn() {
        this.color = null;
        this.sex = null;

        this.position = new Cell();
        this.height = 0;

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


    public void setHeight(int height) {
        this.height = height;
    }


    public void resetPawnStatus() {

        this.hasMoved = false;
        this.hasBuilt = false;

        this.goneUp = false;
        this.forcedMove = false;

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


    public void forcePawn(Cell nextPosition) {

        this.hasMoved = false;
        this.forcedMove = true;

        this.position = nextPosition;
        this.height = nextPosition.getHeight();
    }



}
