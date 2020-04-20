package it.polimi.ingsw.model.Actions;


/**
 * this action is used to return the player the set of possible actions
 * and it's used to indicate that the player have chosen to do a destroy action
 */
public class DestroyAction implements Action {


    private String actionID;


    // ======================================================================================


    public DestroyAction() {
        this.actionID = "destroy";
    }


    // ======================================================================================


    @Override
    public String getActionID() {
        return this.actionID;
    }


}
