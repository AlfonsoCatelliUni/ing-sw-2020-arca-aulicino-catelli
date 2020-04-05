package it.polimi.ingsw.model.Actions;

import it.polimi.ingsw.model.GameConsequenceHandler;

public class BlockConsequence implements Consequence{


    private String blockerNickname;


    // ======================================================================================


    public BlockConsequence(String blockerNickname) {
        this.blockerNickname = blockerNickname;
    }


    // ======================================================================================


    @Override
    public void accept(GameConsequenceHandler game) {
        game.doConsequence(this);
    }


    public String getBlockerNickname() {
        return this.blockerNickname;
    }


}
