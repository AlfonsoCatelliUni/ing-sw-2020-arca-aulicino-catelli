package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class BuildOnSameCellEffect extends EffectDecorator{


    private Cell cellBefore;


    // ======================================================================================


    public BuildOnSameCellEffect(Effect e, Cell cellBefore) {
        super(e);
        this.cellBefore = cellBefore;
    }


    // ======================================================================================


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellToBuild = new ArrayList<>();

        availableCellToBuild.add(cellBefore);

        return availableCellToBuild;
    }


}
