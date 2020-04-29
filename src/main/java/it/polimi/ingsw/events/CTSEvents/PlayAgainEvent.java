package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class PlayAgainEvent extends ClientToServerEvent {


    public String nickname;


    public PlayAgainEvent(String nickname) {
        this.nickname = nickname;
    }


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }
}
