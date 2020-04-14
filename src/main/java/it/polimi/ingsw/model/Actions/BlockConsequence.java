package it.polimi.ingsw.model.Actions;

import it.polimi.ingsw.model.GameConsequenceHandler;

/**
 * this consequence is thrown when I need to block one or more player
 * this players are now not able to move up their pawns
 */
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
