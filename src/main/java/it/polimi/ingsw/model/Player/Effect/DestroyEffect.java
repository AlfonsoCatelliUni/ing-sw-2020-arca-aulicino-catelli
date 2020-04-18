package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.DestroyAndFinishState;
import it.polimi.ingsw.model.Player.State.FinishState;
import it.polimi.ingsw.model.Player.State.MoveState;

import java.util.List;

public class DestroyEffect extends EffectDecorator {


    public DestroyEffect(Effect e) {
        super(e);
        this.effect.changeState(new MoveState(this));
    }


    // ======================================================================================


    @Override
    public void destroy(Cell designatedCell, List<Building> buildings) {

        for(Building building : buildings) {

            if(building.getLevel() + 1 == designatedCell.getHeight()) {
                designatedCell.destroyRoof(building);
                break;
            }
        }
        if (super.effect.getState().getClass().equals(DestroyAndFinishState.class)) {
            changeState(new FinishState(this));
        }
    }


    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {

        Consequence consequence = super.build(designatedPawn, designatedCell, chosenLevel, buildings);

        if (super.effect.getState().getClass().equals(FinishState.class)) {
            changeState(new DestroyAndFinishState(this));
        }

        return consequence;
    }



}
