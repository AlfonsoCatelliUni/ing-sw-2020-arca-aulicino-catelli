package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class NewConnectionEvent extends ClientToServerEvent {


    public Integer ID;

    public String nickname;


    // ======================================================================================


    public NewConnectionEvent(Integer ID, String newNickname) {
        this.ID = ID;
        this.nickname = newNickname;
    }


    public NewConnectionEvent(String newNickname) {
        this(-1, newNickname);
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


}
