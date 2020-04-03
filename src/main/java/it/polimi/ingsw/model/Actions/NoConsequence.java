package it.polimi.ingsw.model.Actions;

import it.polimi.ingsw.model.GameConsequenceHandler;

public class NoConsequence implements Consequence {

    @Override
    public void accept(GameConsequenceHandler game) {
        game.doConsequence(this);
    }

}
