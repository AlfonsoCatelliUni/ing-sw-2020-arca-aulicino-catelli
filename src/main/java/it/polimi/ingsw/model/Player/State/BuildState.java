package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class BuildState implements StateInterface {


    private final Effect effect;


    private final List<Action> actions;


    // ======================================================================================


    public BuildState(Effect effect) {
        this.effect = effect;

        this.actions = new ArrayList<>();
        this.actions.add(new BuildAction());
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>(actions);

        if(effect.wherePawnCanBuild(gameBoard, designatedPawn).size() == 0) {
            possibleActions.removeIf(a -> a.getClass().equals(BuildAction.class));
        }

        return possibleActions;
    }


}
