package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveAndBuildState;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

/**
 * this decorator gives the possibility to build twice, but player can not build for the second time on the perimeter cells
 */
public class BuildInsideCellEffect extends EffectDecorator {


    public BuildInsideCellEffect(Effect e) {
        super(e);
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * this method returns the cells where a pawn can build but if it's the second time to build, it removes the perimeter cells
     * @param gameBoard the game board where the pawn can build on
     * @param designatedPawn the pawn that's designated to build
     * @return the list of cells available to be built
     */
    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellToBuild;

        availableCellToBuild = super.wherePawnCanBuild(gameBoard, designatedPawn);

        availableCellToBuild.removeIf(Cell::isPerimeter);

        return availableCellToBuild;

    }

    @Override
    public Effect clone() {
        return new BuildInsideCellEffect(effect.clone());
    }

}
