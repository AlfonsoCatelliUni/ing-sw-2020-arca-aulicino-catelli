package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.State.DestroyAndFinishState;
import it.polimi.ingsw.model.Player.State.FinishState;

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

        //find the correct building that stays under the actual roof
        for(Building b : buildings) {

            if(designatedCell.getHeight() == 1) {
                designatedCell.destroyRoof(new Building(0,25));
                break;

            } else if(b.getLevel() + 1 == designatedCell.getHeight()) {
                designatedCell.destroyRoof(b); //destroy and place the chosen building
                break;
            }
        }

        //if you have destroyed a block then you can only finish the game
        if (super.effect.getState().getClass().equals(DestroyAndFinishState.class)) {
            changeState(new FinishState(this));
        }
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
