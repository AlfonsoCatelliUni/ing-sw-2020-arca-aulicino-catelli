package it.polimi.ingsw.model.Actions;

public class ForceAction implements Action{


    private String actionID;


    // ======================================================================================


    public ForceAction() {
        this.actionID = "force";
    }


    // ======================================================================================


    @Override
    public String getActionID() {
        return actionID;
    }



}
