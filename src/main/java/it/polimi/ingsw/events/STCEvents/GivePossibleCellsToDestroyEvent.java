package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.awt.*;
import java.util.List;


public class GivePossibleCellsToDestroyEvent extends ServerToClientEvent {

    public String nickname;

    public List <Point> cells;

    public boolean isValid;


    public GivePossibleCellsToDestroyEvent(String nickname, List<Point> cells, boolean isValid) {
        this.nickname = nickname;
        this.cells = cells;
        this.isValid = isValid;
    }

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);

    }

    @Override
    public String toString() {
        return "GivePossibleCellsToDestroyEvent{" + "\n" +
                "nickname='" + nickname + '\'' + ",\n" +
                "cells=" + cells + ",\n" +
                "isValid=" + isValid + "\n" +
                '}';
    }
}
