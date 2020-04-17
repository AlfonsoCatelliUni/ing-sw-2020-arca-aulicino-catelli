package it.polimi.ingsw.model.Consequence;

import it.polimi.ingsw.model.GameConsequenceHandler;

/**
 * this consequence is thrown when I need to block one or more player
 * this players are now not able to move up their pawns
 */
public class BlockConsequence implements Consequence {


    /**
     * the nickname of the player that blocks the other
     */
    private String nickname;


    // ======================================================================================


    public BlockConsequence(String nickname) {
        this.nickname = nickname;
    }


    public BlockConsequence() {
        this("");
    }


    // ======================================================================================


    @Override
    public void accept(GameConsequenceHandler game) {
        game.doConsequence(this);
    }


    // ======================================================================================


    public String getNickname() {
        return this.nickname;
    }


    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
