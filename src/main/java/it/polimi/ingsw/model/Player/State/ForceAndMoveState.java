package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.ForceAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class ForceAndMoveState implements StateInterface {


    private Player player;


    private List<Action> actions;


    // ======================================================================================


    public ForceAndMoveState(Player player) {
        this.player = player;

        this.actions = new ArrayList<>();
        this.actions.add(new ForceAction());
        this.actions.add(new MoveAction());
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>(actions);

        if(player.wherePawnCanMove(gameBoard, designatedPawn).size() == 0) {
            possibleActions.removeIf(a -> a.getClass().equals(MoveAction.class));
        }
        //TODO : cambiare in effect quando gli altri committeranno
        if(player.wherePawnCanForce(gameBoard, designatedPawn).size() == 0) {
            possibleActions.removeIf(a -> a.getClass().equals(ForceAction.class));
        }

        return possibleActions;
    }


}
