package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.DestroyAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.List;

public class DestroyAndFinishState extends StateEffect {


    // ======================================================================================


    public DestroyAndFinishState(Effect effect) {
        super(effect);
    }


    // ======================================================================================


    /**
     * checks if in the current turn the designatedPawn can do a FinishAction, a DestroyAction
     * or even both
     * @param gameBoard is the board where the game is played
     * @param designatedPawn is the pawn chosen by the current player
     * @return a list of possible actions that can be done by the designatedPawn
     */
    @Override
    public List<Action> GetPossibleActions(Board gameBoard, Pawn designatedPawn) {

        Pawn notMovedPawn = null;

        List<Pawn> pawns = gameBoard.getPawnsByColor(designatedPawn.getColor());

        for (Pawn p : pawns ) {
            if(!p.getHasMoved()) {
                notMovedPawn = p;
            }
        }

        List<Action> possibleActions = new ArrayList<>();
        possibleActions.add(new FinishAction());

        if(super.effect.wherePawnCanDestroy(gameBoard, notMovedPawn).size() > 0) {
            possibleActions.add(new DestroyAction());
        }

        return possibleActions;
    }

}
