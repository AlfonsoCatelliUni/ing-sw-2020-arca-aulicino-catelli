package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

import java.io.Serializable;

public class ChosenPlayerNumberEvent implements ClientToServerEvent, Serializable {


    //private String nickname;


    private Integer number;


    // ======================================================================================


    public ChosenPlayerNumberEvent(Integer number) {
        this.number = number;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


//    public String getNickname() {
//        return this.nickname;
//    }


    public Integer getNumber() {
        return this.number;
    }



}
