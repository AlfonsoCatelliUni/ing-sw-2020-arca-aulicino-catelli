package it.polimi.ingsw.model.Actions;

public class DestroyAction extends GeneralAction implements Action {


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


    @Override
    public Boolean isDestroyAction() {
        return true;
    }


}
