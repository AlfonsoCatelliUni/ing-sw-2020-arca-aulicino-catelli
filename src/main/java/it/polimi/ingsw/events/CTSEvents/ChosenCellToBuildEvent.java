package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenCellToBuildEvent extends ClientToServerEvent {


    public String playerNickname;


    public String actionID;


    public int pawnRow;

    public int pawnColumn;


    public int nextRow;

    public int nextColumn;

    public int chosenLevel;


    // ======================================================================================


    public ChosenCellToBuildEvent(String playerNickname, int pawnRow, int pawnColumn, int nextRow, int nextColumn, int chosenLevel) {
        this.playerNickname = playerNickname;
        this.actionID = "build";

        this.pawnRow = pawnRow;
        this.pawnColumn = pawnColumn;

        this.nextRow = nextRow;
        this.nextColumn = nextColumn;

        this.chosenLevel = chosenLevel;
    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.receiveEvent(this);
    }


    // ======================================================================================


//    public String getActionID() {
//        return this.actionID;
//    }
//
//
//    public String getPlayerNickname() {
//        return this.playerNickname;
//    }
//
//
//    public int getPawnRow() {
//        return this.pawnRow;
//    }
//
//
//    public int getPawnColumn() {
//        return this.pawnColumn;
//    }
//
//
//    public int getNextRow() {
//        return this.nextRow;
//    }
//
//
//    public int getNextColumn() {
//        return this.nextColumn;
//    }
//
//    public int getChosenLevel() {
//        return this.chosenLevel;
//    }
}
