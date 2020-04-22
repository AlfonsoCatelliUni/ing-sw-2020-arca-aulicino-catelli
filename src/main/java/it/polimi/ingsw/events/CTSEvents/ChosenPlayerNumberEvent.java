package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

import java.io.Serializable;

public class ChosenPlayerNumberEvent extends ClientToServerEvent {


    public String nickname;


    public Integer number;


    // ======================================================================================


    public ChosenPlayerNumberEvent(String nickname, Integer number) {
        this.number = number;
        this.nickname = nickname;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


    public Integer getNumber() {
        return this.number;
    }



}
