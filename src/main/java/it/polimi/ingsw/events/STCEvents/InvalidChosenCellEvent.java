package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;
import it.polimi.ingsw.model.Board.Cell;

import java.util.List;

public class InvalidChosenCellEvent extends ServerToClientEvent {


    public String nickname;


    public List<Cell> availableCells;


    // ======================================================================================


    public InvalidChosenCellEvent(String nickname, List<Cell> availableCells) {
        this.nickname = nickname;
        this.availableCells = availableCells;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }


//    public String getNickname() {
//        return this.nickname;
//    }
//
//
//    public List<Cell> getAvailableCells() {
//        return this.availableCells;
//    }


}
