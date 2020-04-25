package it.polimi.ingsw.model.Actions;

/**
 * this action is used to return the player the set of possible actions
 * and it's used to indicate that the player have chosen to force action
 */
public class ForceAction implements Action{


    private String actionID;


    // ======================================================================================


    public ForceAction() {
        this.actionID = "Force";
    }


    // ======================================================================================


    @Override
    public String getActionID() {
        return actionID;
    }


}
