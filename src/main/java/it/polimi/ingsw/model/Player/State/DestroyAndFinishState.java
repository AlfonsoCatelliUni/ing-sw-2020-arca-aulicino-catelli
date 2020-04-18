package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.DestroyAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class DestroyAndFinishState implements StateInterface {


    private final Effect effect;


    // ======================================================================================


    public DestroyAndFinishState(Effect effect) {
        this.effect = effect;
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>();
        possibleActions.add(new FinishAction());

        if(effect.wherePawnCanDestroy(gameBoard, designatedPawn).size() > 0) {
            possibleActions.add(new DestroyAction());
        }

        return possibleActions;
    }


}
