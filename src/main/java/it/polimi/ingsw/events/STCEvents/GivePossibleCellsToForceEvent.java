package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.awt.*;
import java.util.List;


public class GivePossibleCellsToForceEvent extends ServerToClientEvent {

    public String nickname;

    public List<Point> cells;

    public boolean isValid;


    // ======================================================================================


    public GivePossibleCellsToForceEvent(String nickname, List<Point> cells, boolean isValid) {
        this.nickname = nickname;
        this.cells = cells;
        this.isValid = isValid;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    //USED IN TESTING
    @Override
    public String toString() {
        return "GivePossibleCellsToForceEvent{" + "\n" +
                "nickname='" + nickname + '\'' + ",\n" +
                "cells=" + cells + ",\n" +
                "isValid=" + isValid + "\n" +
                '}';
    }
}
