package it.polimi.ingsw.model.Consequence;

import it.polimi.ingsw.model.GameConsequenceHandler;


/**
 * this consequence is thrown where the player have done a normal action
 */
public class NoConsequence implements Consequence {


    /**
     * no consequence for this player :/ what a noob
     */
    private String nickname;


    // ======================================================================================


    public NoConsequence(String nickname) {
        this.nickname = nickname;
    }


    public NoConsequence() {
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
