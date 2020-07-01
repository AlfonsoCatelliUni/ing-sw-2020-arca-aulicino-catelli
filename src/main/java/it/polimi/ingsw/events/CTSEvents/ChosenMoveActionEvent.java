package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.manager.ClientToServerManager;
import it.polimi.ingsw.events.ClientToServerEvent;

public class ChosenMoveActionEvent extends ClientToServerEvent {


    public String playerNickname;


    public String action;


    public int pawnRow;


    public int pawnColumn;


    // ======================================================================================


    public ChosenMoveActionEvent(String playerNickname, String action, int pawnRow, int pawnColumn) {
        this.playerNickname = playerNickname;
        this.action = action;

        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }


    // ======================================================================================

}
