package it.polimi.ingsw.model.Actions;

public class ForceAction extends GeneralAction implements Action{


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


    @Override
    public Boolean isForceAction() {
        return true;
    }


}
