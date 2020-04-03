package it.polimi.ingsw.model.Actions;

import it.polimi.ingsw.model.GameConsequenceHandler;

public class BlockConsequence implements Consequence{


    public BlockConsequence() {

    }


    @Override
    public void accept(GameConsequenceHandler game) {
        game.doConsequence(this);
    }


}
