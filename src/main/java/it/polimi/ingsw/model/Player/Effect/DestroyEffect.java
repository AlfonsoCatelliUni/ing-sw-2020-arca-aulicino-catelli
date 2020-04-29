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
        this.effect.getState().setEffect(this);
    }


    // ======================================================================================


    /**
     * this method destroys the roof of the chosen cell.
     * This method will not destroy a dome because the wherePawnCanDestroy do not return cells with a dome in them
     * Same for the cells with height == 0
     * The block is only removed, so it is reusable
     * @param buildings is the list of possible buildings
     * @param designatedCell is the cell where the block will be destroyed
     */
    @Override
    public void destroy(Cell designatedCell, List<Building> buildings) {

        //find the correct building that stay under the actual roof
        for(Building b : buildings) {
            if(b.getLevel() + 1 == designatedCell.getHeight()) {
                designatedCell.destroyRoof(b); //destroy and place the choosen building
                break;
            }
        }

        //if you have destroyed a block the you can only finish the game
        if (super.effect.getState().getClass().equals(DestroyAndFinishState.class)) {
            changeState(new FinishState(this));
        }
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
        return new DestroyEffect(effect.clone());
    }

    @Override
    public Effect addEffect(Effect e) {
        return new DestroyEffect(e);
    }
}
