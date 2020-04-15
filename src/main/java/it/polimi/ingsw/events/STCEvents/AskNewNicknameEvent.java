package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

public class AskNewNicknameEvent implements ServerToClientEvent {


    private String nickname;


    // ======================================================================================


    public AskNewNicknameEvent(String nickname) {
        this.nickname = nickname;
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


}
