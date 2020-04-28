package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Actions.Action;

import java.util.List;

public class GivePossibleActionsEvent extends ServerToClientEvent {


    public String receiverNickname;


    public List<String> actions;


    public boolean isValid;


    // ======================================================================================


    public GivePossibleActionsEvent(String receiverNickname, List<String> actions, boolean isValid) {
        this.receiverNickname = receiverNickname;
        this.actions = actions;
        this.isValid = isValid;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


//    public String getReceiverNickname() {
//        return this.receiverNickname;
//    }
//
//
//    public List<Action> getActions() {
//        return this.actions;
//    }


    @Override
    public String toString() {
        return "GivePossibleActionsEvent{" + "\n" +
                "receiverNickname='" + receiverNickname + '\'' + ",\n" +
                "actions=" + actions + ",\n" +
                "isValid=" + isValid + "\n" +
                '}';
    }
}
