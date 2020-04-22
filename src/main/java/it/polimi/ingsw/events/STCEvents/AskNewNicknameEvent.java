package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.io.Serializable;

public class AskNewNicknameEvent extends ServerToClientEvent {


    public String nickname;


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


//    public String getNickname() {
//        return this.nickname;
//    }


}
