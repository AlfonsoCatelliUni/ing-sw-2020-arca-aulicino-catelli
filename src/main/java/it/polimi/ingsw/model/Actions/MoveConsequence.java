package it.polimi.ingsw.model.Actions;

public class MoveConsequence {

    private boolean checkResult;

    private boolean victoryByMove;

    private boolean blockOpponent;



    public MoveConsequence(boolean checkResult, boolean victoryByMove, boolean blockOpponent) {
        this.checkResult = checkResult;
        this.victoryByMove = victoryByMove;
        this.blockOpponent = blockOpponent;
    }

    public MoveConsequence(boolean checkResult) {
        this.checkResult = checkResult;
        this.victoryByMove = false;
        this.blockOpponent = false;
    }

    public boolean isVictoryByMove() {
        return victoryByMove;
    }

    public boolean isBlockOpponent() {
        return blockOpponent;
    }

    public boolean isCheckResult() {
        return checkResult;
    }


}
