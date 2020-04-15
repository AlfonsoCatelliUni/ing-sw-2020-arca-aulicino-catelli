package it.polimi.ingsw.model.Actions;

public class DestroyAction implements Action {

    private String actionID = "destroy";



    @Override
    public String getActionID() {
        return this.actionID;
    }
}
