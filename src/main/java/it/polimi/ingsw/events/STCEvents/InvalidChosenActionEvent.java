package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Actions.Action;

import java.util.List;

public class InvalidChosenActionEvent extends ServerToClientEvent {


    public String nickname;


    public List<Action> possibleActions;


    // ======================================================================================


    public InvalidChosenActionEvent(String nickname, List<Action> possibleActions) {
        this.nickname = nickname;
        this.possibleActions = possibleActions;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


    public String getNickname() {
        return this.nickname;
    }


    public List<Action> getPossibleActions() {
        return this.possibleActions;
    }



}
