package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.*;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * this decorator gives the possibility to build twice, but for the second build player has to build on the same cell
 */
public class SameBuildAfterPlayer extends PlayerDecorator {


    /**
     * the cell where player built for the first time
     */
    private Cell cellBefore;


    public SameBuildAfterPlayer(BasicPlayer player) {
        super(player);
        cellBefore = null;
    }


    /**
     * this method returns the cells where a pawn can build but if it's the second time to build, it returns ONLY that cell he built before on
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

            if( !cellBefore.getRoof().getIsDome() && cellBefore.getRoof().getLevel() != 3 ) {
                availableCellToBuild.add(cellBefore);
            }


        }

        return availableCellToBuild;

    }

    /**
     * this method is the same of basic method but if player built once, he may build another time
     * @param gameBoard Board where to check the possible actions
     * @param designatedPawn the pawn subject of the action
     * @return list of names of actions
     */
    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions;

        possibleActions = super.player.getPossibleActions(gameBoard, designatedPawn);

        if ( super.player.getNumMove() == 1 && super.player.getNumBuild() == 1 )
            if( !cellBefore.getRoof().getIsDome() && cellBefore.getRoof().getLevel() != 3 )
                    possibleActions.add(new BuildAction());

        else if( super.player.getNumBuild() == 2 && super.player.getNumMove() == 1 ) {
            possibleActions.add(new FinishAction());
        }

        return possibleActions;
    }


    /**
     * This is the same of basic method but it stores the value of the cell of the first build
     * @param designatedPawn the pawn that's designated to build
     * @param designatedCell the cell where to build
     * @param chosenLevel the level of the building to build
     * @param buildings list of possible buildings to build
     */
    @Override
    public Consequence pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        Consequence buildConsequence = super.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);

        this.cellBefore = designatedCell;

        return buildConsequence;

    }

    // used ONLY FOR TESTING
    public void setCellBefore(Cell cellBefore) {
        this.cellBefore = cellBefore;
    }


    // used ONLY FOR TESTING
    public Cell getCellBefore() { return this.cellBefore; }

}
