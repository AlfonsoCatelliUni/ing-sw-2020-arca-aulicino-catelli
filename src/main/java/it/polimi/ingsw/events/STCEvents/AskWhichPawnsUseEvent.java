package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.awt.*;
import java.util.List;


public class AskWhichPawnsUseEvent extends ServerToClientEvent {

    public String nickname;

    public boolean isValid;

    public List<Point> info;


    // ======================================================================================


    public AskWhichPawnsUseEvent(String nickname, boolean isValid, List<Point> info) {
        this.nickname = nickname;
        this.isValid = isValid;
        this.info = info;
    }


    // ======================================================================================


    @Override
    public void accept(ServerToClientManager visitor) {visitor.manageEvent(this);}

    @Override
    public String toString() {
        return "AskWhichPawnsUseEvent{" + "\n" +
                "nickname='" + nickname + '\'' + ",\n" +
                "isValid=" + isValid + ",\n" +
                "info=" + info + "\n" +
                '}';
    }
}
