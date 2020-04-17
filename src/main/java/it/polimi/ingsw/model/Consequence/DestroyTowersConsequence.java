package it.polimi.ingsw.model.Consequence;

import it.polimi.ingsw.model.GameConsequenceHandler;


/**
 * this consequence is thrown when a player build the last available block
 * of a certain type, in this case the game have to destroy all the complete
 * tower to recover some pieces
 */
public class DestroyTowersConsequence implements Consequence {


    /**
     * the nickname of the player that destroy the block
     */
    private String nickname;


    // ======================================================================================


    public DestroyTowersConsequence(String nickname) {
        this.nickname = nickname;
    }


    public DestroyTowersConsequence() {
        this("");
    }


    // ======================================================================================


    @Override
    public void accept(GameConsequenceHandler game) {
        game.doConsequence(this);
    }


    // ======================================================================================


    @Override
    public String getNickname() {
        return this.nickname;
    }


    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
