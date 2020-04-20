package it.polimi.ingsw.model.Actions;


/**
 * this action is used to return the player the set of possible actions
 * and it's used to indicate that the player have chosen to do a move action
 */
public class MoveAction implements Action {


    private String actionID;


    // ======================================================================================


    public MoveAction() {
        this.actionID = "move";
    }


    // ======================================================================================


    @Override
    public String getActionID() {
        return this.actionID;
    }


}
