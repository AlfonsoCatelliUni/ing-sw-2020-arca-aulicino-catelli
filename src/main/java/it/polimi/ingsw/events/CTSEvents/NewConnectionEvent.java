package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class NewConnectionEvent implements ClientToServerEvent {

    private String newNickname;


    // ======================================================================================


    public NewConnectionEvent(String newNickname) {
        this.newNickname = newNickname;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


    public String getNewNickname() {
        return this.newNickname;
    }


}
