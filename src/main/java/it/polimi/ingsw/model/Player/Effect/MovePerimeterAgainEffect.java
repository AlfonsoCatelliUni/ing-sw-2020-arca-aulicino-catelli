package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;
import it.polimi.ingsw.model.Player.State.MoveAndBuildState;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

/**
 * this decorator gives the possibility to move again each time the pawn moves onto perimeter space
 */
public class MovePerimeterAgainEffect extends EffectDecorator {


    public MovePerimeterAgainEffect(Effect e) {
        super(e);
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * This method is similar to basic, but the player can move again each time pawn moves onto perimeter space
     * is so, after the player moved, the action "build" and also "move" are available
     * For the end of the turn, it's only the action "finish"
     */
    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {
        Consequence consequence = super.move(gameBoard, designatedPawn, nextPosition);

        Cell position = designatedPawn.getPosition();

        if (position.isPerimeter())
            this.effect.changeState(new MoveAndBuildState(this));
        else
            this.effect.changeState(new BuildState(this));

        return consequence;

    }

    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        if(super.effect.getState().getClass().equals(BuildState.class) || super.effect.getState().getClass().equals(MoveAndBuildState.class))
            super.effect.changeState(new FinishState(this));

        return super.effect.build(designatedPawn, designatedCell, chosenLevel, buildings);

    }


    @Override
    public Effect clone() {
        return new MovePerimeterAgainEffect(effect.clone());

    }

    @Override
    public Effect addEffect(Effect e) {
        return new MovePerimeterAgainEffect(e);
    }
}
