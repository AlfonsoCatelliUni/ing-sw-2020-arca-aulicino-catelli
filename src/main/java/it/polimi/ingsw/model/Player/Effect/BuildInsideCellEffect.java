package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.List;

public class BuildInsideCellEffect extends EffectDecorator {
    public BuildInsideCellEffect(Effect e) {
        super(e);
    }


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        List<Cell> availableCellToBuild;
            availableCellToBuild = super.wherePawnCanBuild(gameBoard, designatedPawn);
            availableCellToBuild.removeIf(Cell::isPerimeter);

        return availableCellToBuild;

    }


}
