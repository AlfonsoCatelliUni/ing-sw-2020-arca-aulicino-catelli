package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Player.Pawn;

/**
 * the cell that gameBoard has made with
 */
public class Cell {

    /* x = column , y = row */


    /**
     * y = row
     */
    private final int rowPosition;


    /**
     * x = column
     */
    private final int columnPosition;


    /**
     * height of the cell :
     *      0 : ground level (initial value)
     *      1 : 1 block
     *      2 : 2 blocks
     *      3 : 3 blocks
     *      4 : complete tower
     */
    private int height;


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
        this.height = 0;

        this.isBuilderHere = false;
        this.pawnInThisCell = null;

        this.roof = new Building(0, 25);
    }


    // ======================================================================================


    /**
     * USED ONLY FOR TESTING
     * @param height the new value of z position
     */
    public void setHeight(int height) {
        this.height = height;
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
        return height;
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


    // ======================================================================================


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

        this.height++;
        this.roof = designatedBuilding;
        designatedBuilding.increasePlacedQuantity();

    }


    /**
     * set the new type of building and decrement the z position
     * @param designatedBuilding the new type of building
     */
    public void destroyRoof(Building designatedBuilding) {

        this.height--;

        this.roof.decreasePlacedQuantity();
        this.roof = designatedBuilding;


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
