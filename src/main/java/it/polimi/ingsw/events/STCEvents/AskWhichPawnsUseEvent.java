package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class AskWhichPawnsUseEvent extends ServerToClientEvent {

    public String nickname;

    public boolean isValid;

    public String info;


    // ======================================================================================


    public AskWhichPawnsUseEvent(String nickname, boolean isValid, String info) {
        this.nickname = nickname;
        this.isValid = isValid;
        this.info = info;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {visitor.manageEvent(this);}
}
