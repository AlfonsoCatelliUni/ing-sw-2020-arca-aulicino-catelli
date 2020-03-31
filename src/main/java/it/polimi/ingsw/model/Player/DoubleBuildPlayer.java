package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Pawn;

import java.util.ArrayList;
import java.util.List;

public class DoubleBuildPlayer extends PlayerDecorator {
    
    
    private int typeOfSecondBuild;


    private Cell cellBefore;


    // ======================================================================================


    public DoubleBuildPlayer(BasicPlayer player, int typeOfSecondBuild) {
        super(player);
        this.typeOfSecondBuild = typeOfSecondBuild;
        cellBefore = null;
    }


    // ======================================================================================

    /**
     * this method is the same of basic method but it adds a second possibility to build after the first, it checks if it's possible to build the twice
     * @param gameBoard Board where to check the possible actions
     * @param designatedPawn the pawn subject of the action
     * @return list of names of actions
     */

    @Override
    public List<String> getPossibleAction(Board gameBoard, Pawn designatedPawn)  {

        List<String> possibleActions = new ArrayList<>();

        if(super.player.getNumBuild() == 0) {
            possibleActions.add("build");
        }
        else if (super.player.getNumMove() == 0) {
            possibleActions.add("move");
        }

        else if ( super.player.getNumMove() == 1 && super.player.getNumBuild() == 1 ) {

            List<Cell> availableCellToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);

            /* can build but not on the same place */
            if(typeOfSecondBuild == 0) {
                availableCellToBuild.remove(cellBefore);
                if( availableCellToBuild.size() > 0 ) {
                    possibleActions.add("build");
                }
            }

            /* can build only on the previous cell */
            else if ( typeOfSecondBuild == 1) {
                if( !cellBefore.getRoof().getIsDome() && cellBefore.getRoof().getLevel() != 3 ) {
                    possibleActions.add("build");
                }
            }

            possibleActions.add("finish");
        }
        else if( super.player.getNumBuild() == 2 && super.player.getNumMove() == 1 ) {
            possibleActions.add("finish");
        }

        return possibleActions;
    }


    /**
     * this method returns the cells where a pawn can build but if it's the second time to build and if typeOfSecondBuild is 0, it removes the Cell where pawn built before, if typeOfBuild is 1 it returns ONLY that cell
     * @param gameBoard the game board where the pawn can build on
     * @param designatedPawn the pawn that's designated to build
     * @return the list of cells available to be built
     */

    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellToBuild = new ArrayList<>();

        if( player.getNumBuild() == 0 ) {
            availableCellToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);
        }
        else if ( player.getNumBuild() == 1 ) {

            availableCellToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);

            /* can build but not on the same place */
            if(typeOfSecondBuild == 0) {
                availableCellToBuild.remove(cellBefore);
            }

            /* can build only on the previous cell */
            else if ( typeOfSecondBuild == 1) {
                availableCellToBuild.clear();
                if( !cellBefore.getRoof().getIsDome() && cellBefore.getRoof().getLevel() != 3 ) {
                    availableCellToBuild.add(cellBefore);
                }
            }


        }

        return availableCellToBuild;
    }

    /**
     * This is the same of basic method but it stores the value of the cell of the first build
     * @param designatedPawn the pawn that's designated to build
     * @param designatedCell the cell where to build
     * @param chosenLevel the level of the building to build
     * @param buildings list of possibile buildings to build
     */

    @Override
    public void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        super.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);

        this.cellBefore = designatedCell;

    }


    // used ONLY FOR TESTING
    public void setCellBefore(Cell cellBefore) {
        this.cellBefore = cellBefore;
    }

    // used ONLY FOR TESTING
    public Cell getCellBefore() { return this.cellBefore; }



}
