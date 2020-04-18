package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class BuildAndFinishState implements StateInterface {


    private final Effect effect;



    // ======================================================================================


    public BuildAndFinishState(Effect effect) {
        this.effect = effect;
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>();
        possibleActions.add(new FinishAction());

        if(effect.wherePawnCanBuild(gameBoard, designatedPawn).size() > 0) {
            possibleActions.add(new BuildAction());
        }

        return possibleActions;
    }


}
