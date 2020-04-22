package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class AskInitPawnsEvent extends ServerToClientEvent {


    public String nickname;


    // ======================================================================================


    public AskInitPawnsEvent(String nickname) {
        this.nickname = nickname;
    }

    // ======================================================================================

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    // ======================================================================================
}
