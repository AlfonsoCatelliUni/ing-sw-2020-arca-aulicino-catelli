package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenFirstPlayerEvent extends ClientToServerEvent {


    public String challengerNickname;

    public String firstPlayerNickname;


    public ChosenFirstPlayerEvent(String challengerNickname, String firstPlayerNickname) {
        this.challengerNickname = challengerNickname;
        this.firstPlayerNickname = firstPlayerNickname;
    }


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


}
