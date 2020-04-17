package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class BuildState implements StateInterface {


    private final Player player;


    private final List<Action> actions;


    // ======================================================================================


    public BuildState(Player player) {
        this.player = player;

        this.actions = new ArrayList<>();
        this.actions.add(new BuildAction());
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>(actions);

        //TODO : cambiare in effect quando gli altri committeranno
        if(player.wherePawnCanBuild(gameBoard, designatedPawn).size() == 0) {
            possibleActions.removeIf(a -> a.getClass().equals(BuildAction.class));
        }

        return possibleActions;
    }


}
