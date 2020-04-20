package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;
import java.util.List;

public abstract class StateEffect implements StateEffectInterface {


    protected Effect effect;


    // ======================================================================================


    public StateEffect(Effect effect){
        this.effect = effect;
    }


    // ======================================================================================

    @Override
    public abstract List<Action> GetPossibleActions(Board board, Pawn designatedPawn);

    @Override
    public void setEffect(Effect effect){
        this.effect = effect;
    }

}
