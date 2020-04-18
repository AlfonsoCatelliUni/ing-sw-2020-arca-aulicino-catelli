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

    @Override
    public List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn) {
        return wherePawnCanBuild(gameBoard, designatedPawn).stream().filter(cell -> cell.getHeight()>0).collect(Collectors.toList());

    }


}
