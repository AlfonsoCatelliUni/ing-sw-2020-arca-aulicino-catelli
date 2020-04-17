package it.polimi.ingsw.model.Consequence;

import it.polimi.ingsw.model.GameConsequenceHandler;


/**
 * this consequence is thrown when a player win the game in any possible method
 */
public class VictoryConsequence implements Consequence {


    /**
     * the nickname of the winner player
     */
    private String nickname;


    // ======================================================================================


    public VictoryConsequence(String nickname) {
        this.nickname = nickname;
    }


    public VictoryConsequence() {
        this.nickname = "";
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


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



}
