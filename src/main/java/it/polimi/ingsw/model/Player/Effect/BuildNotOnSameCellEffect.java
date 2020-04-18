package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

public class BuildNotOnSameCellEffect extends EffectDecorator {


    private Cell cellBefore;


    // ======================================================================================


    public BuildNotOnSameCellEffect(Effect e, Cell cellBefore) {
        super(e);
        this.cellBefore = cellBefore;
        this.effect.changeState(new MoveState(this));
    }


    // ======================================================================================


    /**
     * this method deletes the cell where the player built in the first build action
     * from the possible cells where to build again
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the pawn used in the current turn
     * @return a list of cells where the player can build
     */
    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellToBuild;

        availableCellToBuild = super.wherePawnCanBuild(gameBoard, designatedPawn);

        availableCellToBuild.removeIf(cell -> cell.equals(cellBefore));

        return availableCellToBuild;
    }


}

