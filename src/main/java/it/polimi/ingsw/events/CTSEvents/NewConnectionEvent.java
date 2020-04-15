package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class NewConnectionEvent implements ClientToServerEvent {


    private Integer ID;


    private String nickname;


    // ======================================================================================


    public NewConnectionEvent(Integer ID, String newNickname) {
        this.ID = ID;
        this.nickname = newNickname;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================


    public Integer getID() {
        return this.ID;
    }


    public String getNickname() {
        return this.nickname;
    }


}
