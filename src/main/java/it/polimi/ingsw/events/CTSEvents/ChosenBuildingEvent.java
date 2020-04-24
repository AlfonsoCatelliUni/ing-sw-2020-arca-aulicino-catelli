package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenBuildingEvent extends ClientToServerEvent {

    public String playerNickname;


    public int levelBuilding;


    public int pawnRow;


    public int pawnColumn;


    public int buildRow;


    public int buildColumn;


    public ChosenBuildingEvent(String playerNickname, int levelBuilding, int pawnRow, int pawnColumn, int buildRow, int buildColumn) {
        this.playerNickname = playerNickname;
        this.levelBuilding = levelBuilding;
        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;
        this.buildRow = buildRow;
        this.buildColumn = buildColumn;
    }

    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
    }
}
