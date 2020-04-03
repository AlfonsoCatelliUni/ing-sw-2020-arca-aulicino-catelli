package it.polimi.ingsw.model.Actions;

import it.polimi.ingsw.model.GameConsequenceHandler;

public class VictoryConsequence implements Consequence {

    private String winnerNickname;

    public VictoryConsequence(String winnerNickname) {
        this.winnerNickname = winnerNickname;
    }


    @Override
    public void accept(GameConsequenceHandler game) {
        game.doConsequence(this);
    }


    public String getWinnerNickname() {
        return this.winnerNickname;
    }



}
