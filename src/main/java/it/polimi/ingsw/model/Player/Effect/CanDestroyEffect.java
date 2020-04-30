package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.DestroyAndFinishState;
import it.polimi.ingsw.model.Player.State.FinishState;

import java.util.List;
import java.util.stream.Collectors;

public class CanDestroyEffect extends EffectDecorator {


    public CanDestroyEffect(Effect e) {
        super(e);
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * this method controls where the not-moved pawn can destroy
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the not-movedPawn
     * @return the list of cells where the pawn can destroy a block
     */
    @Override
    public List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn) {

        return wherePawnCanBuild(gameBoard, designatedPawn).stream().filter(cell -> cell.getHeight() > 0).collect(Collectors.toList());

    }

    /**
     * this overrides the super method until the player has to finish the turn
     * With the finish action, the player could also have a destroy action to be used
     */
    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        Consequence consequence = super.build(designatedPawn, designatedCell, chosenLevel, buildings);

        if (super.effect.getState().getClass().equals(FinishState.class)) {
            changeState(new DestroyAndFinishState(this));
        }

        return consequence;
    }


    @Override
    public Effect clone() {
        return new CanDestroyEffect(effect.clone());
    }

    @Override
    public Effect addEffect(Effect e) {
        return new CanDestroyEffect(e);
    }
}
