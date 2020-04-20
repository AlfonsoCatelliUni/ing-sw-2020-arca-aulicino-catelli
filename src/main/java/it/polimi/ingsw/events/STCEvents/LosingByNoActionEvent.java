package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class LosingByNoActionEvent implements ServerToClientEvent {


    private String nickname;


    private String sadMessage;


    // ======================================================================================


    public LosingByNoActionEvent(String nickname, String sadMessage) {
        this.nickname = nickname;
        this.sadMessage = sadMessage;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


}
