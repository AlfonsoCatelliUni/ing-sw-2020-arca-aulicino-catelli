package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * this decorator gives the possibility to build twice, but player can not build for the second time on the perimeter cells
 */
public class MoreBuildInsidePlayer extends PlayerDecorator  {


    public MoreBuildInsidePlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    /**
     * this method is the same of basic method but if player built once, he may build another time
     * @param gameBoard Board where to check the possible actions
     * @param designatedPawn the pawn subject of the action
     * @return list of names of actions
     */
    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn)  {

        List<Action> availableActions;

        availableActions = super.player.getPossibleActions(gameBoard, designatedPawn);

        if ( super.player.getNumMove() == 1 && super.player.getNumBuild() == 1 ) {
            if ( wherePawnCanBuild(gameBoard, designatedPawn).size() > 0 )
                availableActions.add(new BuildAction());
        }

        if  (super.player.getNumBuild() == 2 && super.player.getNumMove() == 1)
            availableActions.add(new FinishAction());

        return availableActions;
    }


    /**
     * this method returns the cells where a pawn can build but if it's the second time to build, it removes the perimeter cells
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
            availableCellToBuild.removeIf(Cell::isPerimeter);
        }

        return availableCellToBuild;

    }


}