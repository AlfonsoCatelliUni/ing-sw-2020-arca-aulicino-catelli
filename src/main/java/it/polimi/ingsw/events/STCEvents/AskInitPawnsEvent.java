package it.polimi.ingsw.events.STCEvents;

import it.polimi.ingsw.events.ServerToClientEvent;
import it.polimi.ingsw.events.manager.ServerToClientManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AskInitPawnsEvent extends ServerToClientEvent {


    public String nickname;

    public boolean isValid;

    public List<Point> info;


    // ======================================================================================


    public AskInitPawnsEvent(String nickname, boolean isValid, List<Point> info) {
        this.nickname = nickname;
        this.isValid = isValid;
        this.info = info;
    }

    public AskInitPawnsEvent(String nickname, boolean isValid){
        this.nickname = nickname;
        this.isValid = isValid;
        this.info = new ArrayList<>();
    }

    // ======================================================================================

    @Override
    public void accept(ServerToClientManager visitor) {
        visitor.manageEvent(this);
    }

    // ======================================================================================


    //USED IN TESTING
    @Override
    public String toString() {
        return "AskInitPawnsEvent{" + "\n" +
                "nickname='" + nickname + '\'' + ",\n" +
                "isValid=" + isValid + ",\n" +
                "info=" + info + "\n" +
                '}';
    }
}
