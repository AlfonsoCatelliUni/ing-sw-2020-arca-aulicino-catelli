package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenPawnToUseEvent extends ClientToServerEvent {

    public String playerNickname;

    public int pawnRow;

    public int pawnColumn;


    public ChosenPawnToUseEvent(String playerNickname, int malePawnRow, int malePawnColumn) {
        this.playerNickname = playerNickname;
        this.pawnRow = malePawnRow;
        this.pawnColumn = malePawnColumn;
    }

    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);

    }
}
