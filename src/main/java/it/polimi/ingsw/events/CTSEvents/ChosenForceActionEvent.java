package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenForceActionEvent extends ClientToServerEvent {

    public String playerNickname;

    public String action;

    public int pawnRow;

    public int pawnColumn;

    public ChosenForceActionEvent(String playerNickname, String action, int pawnRow, int pawnColumn) {
        this.playerNickname = playerNickname;
        this.action = action;
        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;
    }

    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }
}
