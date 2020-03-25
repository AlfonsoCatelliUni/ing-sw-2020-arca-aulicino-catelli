package it.polimi.ingsw.model.BoardPack;

import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Pawn;

public class Cell {

    /* x = column , y = row */


    /**
     * x = column
     */
    private int columnPosition;


    /**
     * y = row
     */
    private int rowPosition;


    /**
     * height of the cell :
     *      0 : ground level (initial value)
     *      1 : 1 block
     *      2 : 2 blocks
     *      3 : 3 blocks
     *      4 : complete tower
     */
    private int zPosition;


    /**
     * true if there is a Builder is this cell
     */
    private Boolean isBuilderHere;


    /**
     * indicates the type of the highest Building
     */
    private Building roof;


    /**
     * the pawn in this cell
     */
    private Pawn pawnInThisCell;


    // ======================================================================================


    /**
     * this is the constructor used to build the initial board
     * @param rowPosition the row coordinate to initiate this cell
     * @param columnPosition the columns coordinate to initiate this cell
     */
    public Cell(int rowPosition, int columnPosition) {
        this.columnPosition = columnPosition;
        this.rowPosition = rowPosition;
        this.zPosition = 0;
        this.isBuilderHere = false;
        this.roof = new Building(0, 30);
        this.pawnInThisCell = null;
        this.zPosition = 0;
    }


    public Cell() {
        this.columnPosition = 0;
        this.rowPosition = 0;
        this.zPosition = 0;
        this.isBuilderHere = false;
        this.roof = new Building(0, 30);
        this.pawnInThisCell = null;
        this.zPosition = 0;
    }


    // ======================================================================================


    /**
     * used only for testing
     * @param zPosition the new value of z position
     */
    public void setHeight(int zPosition) {
        this.zPosition = zPosition;
    }


    /**
     * can i get the x (column) value of this cell ?
     * @return the column (x) coordinate
     */
    public int getColumnPosition() {
        return columnPosition;
    }


    /**
     * can i get the y (row) value of this cell ?
     * @return the row (y) coordinate
     */
    public int getRowPosition() {
        return rowPosition;
    }


    /**
     * what is the height of the tower in this cell ?
     * @return the value of the z coordinate
     */
    public int getHeight() {
        return zPosition;
    }


    /**
     * there is a pawn in this cell ?
     * @return true is there is a pawn
     */
    public Boolean getBuilderHere() {
        return isBuilderHere;
    }


    /**
     * what is the type of the highest building in this cell ?
     * @return the type of the roof in this cell
     */
    public Building getRoof() {
        return roof;
    }


    /**
     * can i get the pawn collocated in this cell ?
     * @return the reference to the pawn
     */
    public Pawn getPawnInThisCell() {
        return pawnInThisCell;
    }


    /**
     * is the cell free ?
     * @return true if there aren't collocated here a dome or a pawn
     */
    public Boolean getIsFree(){
        return !getRoof().getIsDome() && !getBuilderHere();
    }


    /**
     * set null the reference at the Pawn and false the indicator of builderHere
     */
    public void freeCell() {
        this.pawnInThisCell = null;
        this.isBuilderHere = false;
    }


    /**
     * set the new type of building and increment the z position
     * @param designatedBuilding the new type of building
     */
    public void buildOnThisCell(Building designatedBuilding) {

        this.zPosition++;
        this.roof = designatedBuilding;
        designatedBuilding.increaseQuantity();

    }


    /**
     * occupy the cell with the passed builder
     * @param pawn the builder that is now in this cell
     */
    public void placePawnHere(Pawn pawn) {

        if (!this.isBuilderHere && this.pawnInThisCell == null){
            this.isBuilderHere = true;
            this.pawnInThisCell = pawn;
        }

    }


}
