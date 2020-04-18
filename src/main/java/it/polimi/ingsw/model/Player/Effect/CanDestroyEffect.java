package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.List;
import java.util.stream.Collectors;

public class CanDestroyEffect extends EffectDecorator {
    public CanDestroyEffect(Effect e) {
        super(e);
    }

    /**
     * this method controls where the not-moved pawn can destroy
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the not-movedPawn
     * @return the list of cells where the pawn can destroy a block
     */
    @Override
    public List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn) {
        return wherePawnCanBuild(gameBoard, designatedPawn).stream().filter(cell -> cell.getHeight()>0).collect(Collectors.toList());

    }


}
