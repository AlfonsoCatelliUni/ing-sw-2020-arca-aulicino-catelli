package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class AskInitPawnsEvent extends ServerToClientEvent {


    public String nickname;

    public boolean isValid;


    // ======================================================================================


    public AskInitPawnsEvent(String nickname, boolean isValid) {
        this.nickname = nickname;
        this.isValid = isValid;
    }

    // ======================================================================================

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    // ======================================================================================
}
