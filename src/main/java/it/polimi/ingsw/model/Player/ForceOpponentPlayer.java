package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;

import java.util.List;

public class ForceOpponentPlayer extends PlayerDecorator {
    public ForceOpponentPlayer(BasicPlayer player) {
        super(player);
    }


    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        // TODO : aggiungere forceAction all'inizio del turno
        return super.getPossibleActions(gameBoard, designatedPawn);
    }
}
