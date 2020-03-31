package it.polimi.ingsw.model.BoardPack;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class Board {

    /**
     * constant value that indicates how many rows are present in board
     */
    private static final int ROW = 5;


    /**
     * constant value that indicates how many columns are present in board
     */
    private static final int COLUMN = 5;


    /**
     * game board saved as a matrix of Cell class
     */
    private Cell[][] matrixBoard = new Cell[ROW][COLUMN];


    // ======================================================================================


    public Board() {

        /* build an empty board, passing only the coordinate parameters */
        for (int r = 0; r < ROW; r++){
            for (int c = 0; c < COLUMN; c++){
                matrixBoard[r][c] = new Cell(r, c);
            }
        }



    }


    // ======================================================================================


    /**
     * return the matrix that compose the game board
     * @return complete array of Cell
     */
    public Cell[][] getMatrixBoard() {
        return matrixBoard;
    }


    /**
     * get cell by coordinates
     * @param row x position
     * @param column y position
     * @return the cell in position (x, y) = (row, column)
     */
    public Cell getCell(int row, int column){
        return matrixBoard[row][column];
    }


    /**
     * gives you the pawn in the passed coordinates
     * @param row the y coordinate
     * @param column the x coordinate
     * @return the pawn in the cell with the specified coordinates
     */
    public Pawn getPawnByCoordinates(int row, int column) {
        return getCell(row, column).getPawnInThisCell();
    }


    /**
     * select all the cells that are neighbor in the pawn and are not taller than 1 level and free
     * @param chosenPawn the selected pawn
     * @return where the selected pawn can move
     */
    public List<Cell> getCellAvailableToMove(Pawn chosenPawn) {

        List<Cell> neighboringCell = getNeighboring( chosenPawn.getPosition() );
        List<Cell> retAvailableCellList = new ArrayList<>();

        for (Cell c : neighboringCell ) {
            if ( c.getHeight() - chosenPawn.getHeight() <= 1 && c.getIsFree()){
                retAvailableCellList.add(c);
            }
        }

        return retAvailableCellList;
    }


    /**
     * select all the cells that are neighbor in the pawn and that are free
     * @param chosenPawn the selected pawn
     * @return where the selected pawn can build
     */
    public List<Cell> getCellAvailableToBuild( Pawn chosenPawn) {

        List<Cell> neighboringCell = getNeighboring( chosenPawn.getPosition() );
        List<Cell> retAvailableCellList = new ArrayList<>();

        for (Cell c : neighboringCell ) {
            //se la cella è libera (no cupola, no muratore) allora la aggiungo
            if ( c.getIsFree() ){
                retAvailableCellList.add(c);
            }
        }

        return retAvailableCellList;
    }


    /**
     * get all the neighboring cells of the parameter
     * @param designatedCell the cell
     * @return a list of Cell
     */
    public List<Cell> getNeighboring( Cell designatedCell) {

        int desColumn = designatedCell.getColumnPosition();
        int desRow = designatedCell.getRowPosition();
        List<Cell> retCellList = new ArrayList<>();

        for( int i = -1; i<2; i++ ){
            for( int j = -1; j<2; j++ ){

                if(i != 0 || j != 0){

                    if(desRow+i >= 0 && desRow+i <= 4){
                        if(desColumn+j >= 0 && desColumn+j <= 4){
                            retCellList.add( getCell(desRow+i, desColumn+j) );
                        }
                    }

                } //end_if

            }
        }

        return retCellList;
    }


    /**
     * can i get the type of building that can be built on this cell ?
     * @param designatedCell the cell that i've chosen to build on
     * @param buildings the list of existing building
     * @return the list of possible building on the designated cell
     */
    public List<Building> getPossibleBuildingOnCell(Cell designatedCell, List<Building> buildings ) {

        List<Building> possibleBuilding = new ArrayList<>();

        for (Building b : buildings) {
            if( b.getLevel() == designatedCell.getRoof().getLevel() + 1 ) {
                possibleBuilding.add(b);
            }
        }

        return possibleBuilding;
    }


    // ======================================================================================


    /**
     * how much domes are placed on the board right now ?
     * @return the number of domes placed on the board
     */
    public int getNumberOfDome() {

        int numDome = 0;

        for (int r = 0; r < ROW; r++){
            for (int c = 0; c < COLUMN; c++){

                if( matrixBoard[r][c].getRoof().getIsDome() )
                    numDome++;

            }
        }

        return numDome;
    }


    /**
     * I want the information of this cell encoded in the following method "Height,Pawn" :
     *      - Height is the building level in this cell (0-1-2-3-4)
     *      - Pawn is the color's first letter, upper case if male, lower case if female (B-b, G-g, W-w)
     *      - Pawn is "x" if there is a dome in this cell
     *      - Pawn is "." if there is no builder in this cell
     * @param designatedCell the cell to encode
     * @return the encoded string
     */
    public String getStringCellInfo(Cell designatedCell) {

        String retString = String.valueOf(designatedCell.getHeight());

        if (designatedCell.getRoof().getIsDome()) {
            return retString + "x";
        }
        else if(!designatedCell.getBuilderHere()) {
            return retString + ".";
        }
        else {

            Color pawnColor = designatedCell.getPawnInThisCell().getColor();
            Sex pawnSex = designatedCell.getPawnInThisCell().getSex();

            if ( pawnColor == Color.BLUE ) {

                if ( pawnSex == Sex.MALE ) {
                    return retString + "B";
                }
                else if ( pawnSex == Sex.FEMALE ) {
                    return retString + "b";
                }

            }
            else if ( pawnColor == Color.GREY ) {

                if ( pawnSex == Sex.MALE ) {
                    return retString + "G";
                }
                else if ( pawnSex == Sex.FEMALE ) {
                    return retString + "g";
                }

            }
            else if ( pawnColor == Color.WHITE ){

                if ( pawnSex == Sex.MALE ) {
                    return retString + "W";
                }
                else if ( pawnSex == Sex.FEMALE ) {
                    return retString + "w";
                }

            }

        }

        return "error";
    }




}
