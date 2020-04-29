package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

public class CanBuildUnderItselfEffect extends EffectDecorator {


    public CanBuildUnderItselfEffect(Effect e) {
        super(e);
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * this method calculates all the possible cells where the designated pawn can build,
     * adding the cell where the pawn itself is located, because with this decorator
     * you can build under your own pawn
     * @param gameBoard the board
     * @param designatedPawn the pawn that have to build
     * @return the list of cells
     */
    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        List<Cell> cellsAvailableToBuild = super.wherePawnCanBuild(gameBoard, designatedPawn);

        if(designatedPawn.getHeight() <= 2) {
            cellsAvailableToBuild.add(designatedPawn.getPosition());
        }

        return cellsAvailableToBuild;
    }

    @Override
    public Effect clone() {
        return new CanBuildUnderItselfEffect(effect.clone());
    }

    @Override
    public Effect addEffect(Effect e) {
        return new CanBuildUnderItselfEffect(e);
    }
}
