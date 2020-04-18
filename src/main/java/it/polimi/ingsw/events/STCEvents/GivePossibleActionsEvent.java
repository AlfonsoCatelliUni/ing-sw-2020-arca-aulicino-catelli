package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Actions.Action;

import java.util.List;

public class GivePossibleActionsEvent implements ServerToClientEvent {


    private String receiverNickname;


    private List<Action> actions;


    // ======================================================================================


    public GivePossibleActionsEvent(String receiverNickname, List<Action> actions) {
        this.receiverNickname = receiverNickname;
        this.actions = actions;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


    public String getReceiverNickname() {
        return this.receiverNickname;
    }


    public List<Action> getActions() {
        return this.actions;
    }


}
