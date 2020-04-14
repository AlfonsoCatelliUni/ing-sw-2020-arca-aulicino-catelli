package it.polimi.ingsw.model.Actions;

import it.polimi.ingsw.model.GameConsequenceHandler;


/**
 * this consequence is thrown when a player build the last available block
 * of a certain type, in this case the game have to destroy all the complete
 * tower to recover some pieces
 */
public class DestroyTowersConsequence implements Consequence {


    // ======================================================================================


    public DestroyTowersConsequence() {
    }

    // ======================================================================================


    @Override
    public void accept(GameConsequenceHandler game) {
        game.doConsequence(this);
    }


}
