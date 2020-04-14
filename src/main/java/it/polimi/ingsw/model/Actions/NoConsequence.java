package it.polimi.ingsw.model.Actions;

import it.polimi.ingsw.model.GameConsequenceHandler;


/**
 * this consequence is thrown where the player have done a normal action
 */
public class NoConsequence implements Consequence {

    @Override
    public void accept(GameConsequenceHandler game) {
        game.doConsequence(this);
    }

}
