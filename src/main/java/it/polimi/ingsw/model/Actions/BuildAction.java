package it.polimi.ingsw.model.Actions;


/**
 * this action is used to return the player the set of possible actions
 * and it's used to indicate that the player have chosen to do a build action
 */
public class BuildAction implements Action {


    private String actionID;


    // ======================================================================================


    public BuildAction() {
        this.actionID = "build";
    }


    // ======================================================================================


    public String getActionID(){
        return this.actionID;
    }

}
