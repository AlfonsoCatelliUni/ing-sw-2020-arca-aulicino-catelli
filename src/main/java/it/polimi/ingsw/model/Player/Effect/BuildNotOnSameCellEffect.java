package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.List;

public class BuildNotOnSameCellEffect extends EffectDecorator {


    private Cell cellBefore;


    // ======================================================================================


    public BuildNotOnSameCellEffect(Effect e, Cell cellBefore) {
        super(e);
        this.cellBefore = cellBefore;
    }


    // ======================================================================================


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellToBuild;

        availableCellToBuild = super.wherePawnCanBuild(gameBoard, designatedPawn);

        availableCellToBuild.removeIf(cell -> cell.equals(cellBefore));

        return availableCellToBuild;
    }


}

