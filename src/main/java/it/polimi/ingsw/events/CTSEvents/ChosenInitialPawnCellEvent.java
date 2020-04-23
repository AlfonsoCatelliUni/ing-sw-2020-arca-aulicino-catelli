package it.polimi.ingsw.events.CTSEvents;

import it.polimi.ingsw.events.ClientToServerEvent;
import it.polimi.ingsw.events.manager.ClientToServerManager;

public class ChosenInitialPawnCellEvent extends ClientToServerEvent {


    public String playerNickname;


    public String actionID;


    public int malePawnRow;

    public int malePawnColumn;

    public int femalePawnRow;

    public int femalePawnColumn;


    // ======================================================================================


    public ChosenInitialPawnCellEvent(String playerNickname, int malePawnRow, int malePawnColumn, int femalePawnRow, int femalePawnColumn) {
        this.playerNickname = playerNickname;
        this.actionID = "init_pawn";

        this.malePawnRow = malePawnRow;
        this.malePawnColumn = malePawnColumn;

        this.femalePawnRow = femalePawnRow;
        this.femalePawnColumn = femalePawnColumn;


    }


    // ======================================================================================


    @Override
    public void accept(ClientToServerManager visitor) {
        visitor.manageEvent(this);
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


}
