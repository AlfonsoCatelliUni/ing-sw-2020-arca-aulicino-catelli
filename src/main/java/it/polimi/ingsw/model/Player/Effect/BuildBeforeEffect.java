package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.BuildState;
import it.polimi.ingsw.model.Player.State.MoveAndBuildState;


import java.util.List;

public class BuildBeforeEffect extends EffectDecorator {

    public BuildBeforeEffect(Effect e) {
        super(e);
        changeState(new MoveAndBuildState(this));
    }

    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        Consequence buildConsequence = super.effect.build(designatedPawn, designatedCell, chosenLevel, buildings);

        if(super.effect.getState().getClass().equals(MoveAndBuildState.class)) {
            super.effect.changeState(new MoveState(this));
                this.effect = new NotMoveUpEffect(effect);
        }
        else if(super.effect.getState().getClass().equals(BuildState.class)) {
            super.effect.changeState(new FinishState(this));
            effect = new BasicEffect();
        }
        else {
            throw new RuntimeException("Invalid State!");
        }

        return buildConsequence;
    }

}
