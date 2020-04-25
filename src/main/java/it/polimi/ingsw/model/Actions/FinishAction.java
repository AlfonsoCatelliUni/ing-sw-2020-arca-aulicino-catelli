package it.polimi.ingsw.model.Actions;


/**
 * this action is used to return the player the set of possible actions
 * and it's used to indicate that the player have chosen to end his turn
 */
public class FinishAction implements Action {


    private String actionID;


    // ======================================================================================


    public FinishAction() {
        this.actionID = "End turn";
    }


    // ======================================================================================


    public String getActionID(){
        return this.actionID;
    }

}
