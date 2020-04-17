package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildAndFinishState;
import it.polimi.ingsw.model.Player.State.BuildState;


import java.util.List;

public class MoreBuildOnSameEffect extends EffectDecorator {

    public MoreBuildOnSameEffect(Effect e) {
        super(e);
    }

    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        if (super.effect.getState().getClass().equals(BuildState.class)) {
        //    changeState(new BuildAndFinishState(this));
        //    effect = new OnSameCell(effect, designatedCell);

        }

        else if(super.effect.getState().getClass().equals(BuildAndFinishState.class)) {
        //    changeState(new FinishStateTest(this));
            effect = new BasicEffect();
        }

        Consequence buildConsequence = super.build(designatedPawn, designatedCell, chosenLevel, buildings);

        return buildConsequence;
    }

}




