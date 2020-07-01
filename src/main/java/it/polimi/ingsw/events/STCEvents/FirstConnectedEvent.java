package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;


public class FirstConnectedEvent extends ServerToClientEvent {


    public String nickname;


    public FirstConnectedEvent(String nickname) {
        this.nickname = nickname;
    }


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    //USED IN TESTING
    @Override
    public String toString() {
        return "FirstConnectedEvent{" + "\n" +
                "nickname='" + nickname + '\'' + "\n" +
                '}';
    }
}
