package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class MoveAndBuildState implements StateInterface {


    private final Effect effect;

    // ======================================================================================


    public MoveAndBuildState(Effect effect) {
        this.effect = effect;
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>();

        if(effect.wherePawnCanMove(gameBoard, designatedPawn).size() > 0) {
            possibleActions.add(new MoveAction());
        }
        if(effect.wherePawnCanBuild(gameBoard, designatedPawn).size() > 0) {
            possibleActions.add(new BuildAction());
        }

        return possibleActions;
    }

}
