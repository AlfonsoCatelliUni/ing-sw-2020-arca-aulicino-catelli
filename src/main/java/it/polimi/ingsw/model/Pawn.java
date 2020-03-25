package it.polimi.ingsw.model;

import it.polimi.ingsw.model.BoardPack.Cell;

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
    private int zPosition;


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
        this.zPosition = starCell.getHeight();
        this.hasMoved = false;
        this.goneUp = false;
        this.forcedMove = false;
    }


    public Pawn(){
        this.color = null;
        this.sex = null;
        this.position = new Cell();
        this.zPosition = 0;
        this.hasMoved = false;
        this.goneUp = false;
        this.forcedMove = false;
    }


    // ======================================================================================








}
