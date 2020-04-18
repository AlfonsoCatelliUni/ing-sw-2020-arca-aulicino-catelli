package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.ArrayList;
import java.util.List;

public class BuildOnSameCellEffect extends EffectDecorator{


    private Cell cellBefore;


    // ======================================================================================


    public BuildOnSameCellEffect(Effect e, Cell cellBefore) {
        super(e);
        this.cellBefore = cellBefore;
        this.effect.changeState(new MoveState(this));
    }


    // ======================================================================================


    /**
     * this method adds the cell where the player built in the first build action
     * to the possibles cells where to build again
     * The added cell will be the only cell where tha player could build again
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the pawn used in the current turn
     * @return a list of cells where the player can build
     */
    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellToBuild = new ArrayList<>();

        availableCellToBuild.add(cellBefore);

        return availableCellToBuild;
    }


}
