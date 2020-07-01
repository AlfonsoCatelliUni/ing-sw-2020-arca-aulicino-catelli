package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.util.ArrayList;
import java.util.List;

public class LosingByNoActionEvent extends ServerToClientEvent {


    public String nickname;

    public String sadMessage;

    public List<String> actionsAfterLosing;


    // ======================================================================================


    public LosingByNoActionEvent(String nickname, String sadMessage) {
        this.nickname = nickname;
        this.sadMessage = sadMessage;

        this.actionsAfterLosing = new ArrayList<>();
        this.actionsAfterLosing.add("Spectate Match");
        this.actionsAfterLosing.add("Leave");
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    //USED IN TESTING
    @Override
    public String toString() {
        return "LosingByNoActionEvent{" + "\n" +
                "nickname='" + nickname + '\'' + ",\n" +
                "sadMessage='" + sadMessage + '\'' + "\n" +
                '}';
    }
}

