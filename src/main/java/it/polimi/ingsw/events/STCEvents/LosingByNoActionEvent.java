package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class LosingByNoActionEvent extends ServerToClientEvent {


    public String nickname;

    public String sadMessage;


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
