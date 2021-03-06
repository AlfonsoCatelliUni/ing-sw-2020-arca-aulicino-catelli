package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildAndFinishState;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.FinishState;

import java.util.List;

public class MoreBuildInsideEffect extends EffectDecorator {


    public MoreBuildInsideEffect(Effect e) {
        super(e);
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * this method is the same of basic method but if player built once, he may build another time
     */
    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        if (super.effect.getState().getClass().equals(BuildState.class)) {
            effect = new BuildInsideCellEffect(effect);
            changeState(new BuildAndFinishState(this));


        }

        else if(super.effect.getState().getClass().equals(BuildAndFinishState.class)) {
            changeState(new FinishState(this));
        }

        return super.build(designatedPawn, designatedCell, chosenLevel, buildings);
    }


    @Override
    public Effect clone() {
        return new MoreBuildInsideEffect(effect.clone());
    }

    @Override
    public Effect addEffect(Effect e) {
        return new MoreBuildInsideEffect(e);
    }
}


