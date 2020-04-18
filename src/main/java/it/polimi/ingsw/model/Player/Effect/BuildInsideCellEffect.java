package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveAndBuildState;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

public class BuildInsideCellEffect extends EffectDecorator {


    public BuildInsideCellEffect(Effect e) {
        super(e);
        this.effect.changeState(new MoveState(this));
    }


    // ======================================================================================


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellToBuild;

        availableCellToBuild = super.wherePawnCanBuild(gameBoard, designatedPawn);

        availableCellToBuild.removeIf(Cell::isPerimeter);

        return availableCellToBuild;

    }


}
