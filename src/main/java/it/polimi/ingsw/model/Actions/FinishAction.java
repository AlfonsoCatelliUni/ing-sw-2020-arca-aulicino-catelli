package it.polimi.ingsw.model.Actions;


/**
 * this action is used to return the player the set of possible actions
 * and it's used to indicate that the player have chose to do a finish action
 */
public class FinishAction implements Action {


    private String actionID;


    private int pawnRow;


    private int pawnColumn;


    private int actionRow;


    private int actionColumn;


    // ======================================================================================


    public FinishAction() {
        this.actionID = "end turn";

        this.pawnRow = -1;
        this.pawnColumn = -1;

        this.actionRow = -1;
        this.actionColumn = -1;
    }


    // ======================================================================================


    public String getActionID(){
        return this.actionID;
    }


    public int getPawnRow() {
        return pawnRow;
    }


    public int getPawnColumn() {
        return pawnColumn;
    }


    public int getActionRow() {
        return actionRow;
    }


    public int getActionColumn() {
        return actionColumn;
    }

}
