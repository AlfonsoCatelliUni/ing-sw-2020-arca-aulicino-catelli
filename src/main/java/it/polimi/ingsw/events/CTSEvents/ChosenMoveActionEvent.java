package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.controller.EventVisitor;
import it.polimi.ingsw.events.ClientToServerEvent;

public class ChosenMoveActionEvent implements ClientToServerEvent {


    private String playerNickname;


    public ChosenMoveActionEvent(String playerNickname) {
        this.playerNickname = playerNickname;
    }


    @Override
    public void accept(EventVisitor visitor) {
        visitor.receiveCTS(this);
    }


    public String getPlayerNickname() {
        return this.playerNickname;
    }


}
