package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.JsonHandler;
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


    /**
     * the list of the four different buildings, in order to check how many pieces are available to place to the board
     */
    private final List<Building> buildings;


    // ======================================================================================


    /**
     * builds the board for the game with empty cells
     */
    public Board() {

        /* build an empty board, passing only the coordinate parameters */
        for (int r = 0; r < ROW; r++){
            for (int c = 0; c < COLUMN; c++){
                matrixBoard[r][c] = new Cell(r, c);
            }
        }

        this.buildings = JsonHandler.deserializeBuildingList();

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
     * @return list of the four different buildings
     */
    public List<Building> getBuildings(){
        return this.buildings;
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

            if ( c.getIsFree() && getPossibleBuildingOnCell(c).size() > 0){
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
     * @return the list of possible building on the designated cell
     */
    public List<Building> getPossibleBuildingOnCell(Cell designatedCell) {

        List<Building> possibleBuilding = new ArrayList<>();

        for (Building b : buildings) {
            if( b.getLevel() == designatedCell.getRoof().getLevel() + 1 && b.isAvailable() ) {
                possibleBuilding.add(b);
            }
        }

        return possibleBuilding;
    }


    /**
     * destroy the actual roof, placing the correct roof
     * @param row row coordinate of the cell that i want to lower the tower
     * @param column column coordinate of the cell that i want to lower the tower
     */
    public void destroyRoofInThisCell(int row, int column) {

        Cell designatedCell = getCell(row, column);

        Building newRoof = new Building(0, 30);

        for (Building b : buildings ) {
            if( b.getLevel() == designatedCell.getHeight() - 1 ) {
                newRoof = b;
            }
        }

        designatedCell.destroyRoof(newRoof);
    }

    // ======================================================================================


    /**
     * how much complete towers are placed on the board right now ?
     * @return the number of domes placed on the board
     */
    public int getNumberOfCompleteTowers() {

        int numDome = 0;

        for (int r = 0; r < ROW; r++){
            for (int c = 0; c < COLUMN; c++){

                if( matrixBoard[r][c].getRoof().getIsDome() && matrixBoard[r][c].getHeight() == 4)
                    numDome++;

            }
        }

        return numDome;
    }


    /**
     * this method finds in the board if there are some complete towers and replace them with a dome, destroying a
     * tower causes the decreasing of placed quantity of the buildings
     */
    public void destroyTowers() {

        for(int row = 0; row < ROW; row++){
            for(int column = 0; column < COLUMN; column++){

                if(matrixBoard[row][column].getHeight() == 4){
                    matrixBoard[row][column].setHeight(1);

                    buildings.stream().filter(b -> !b.getIsDome()).forEach(Building::decreasePlacedQuantity);
                }
            }

        }

    }



}
