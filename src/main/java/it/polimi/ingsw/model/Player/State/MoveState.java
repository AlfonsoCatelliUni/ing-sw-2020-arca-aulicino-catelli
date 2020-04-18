package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class MoveState implements StateInterface {


    private final Effect effect;

    // ======================================================================================


    public MoveState(Effect effect) {
        this.effect = effect;
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>();

        List<Cell> list = effect.wherePawnCanMove(gameBoard, designatedPawn);

        if(list.size() > 0) {
            possibleActions.add(new MoveAction());
        }

        return possibleActions;
    }


}
