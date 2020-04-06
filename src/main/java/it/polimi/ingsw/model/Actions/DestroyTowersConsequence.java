package it.polimi.ingsw.model.Actions;

import it.polimi.ingsw.model.GameConsequenceHandler;

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
