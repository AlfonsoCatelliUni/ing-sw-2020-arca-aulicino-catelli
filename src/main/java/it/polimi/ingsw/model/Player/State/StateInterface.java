package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.List;

public interface StateInterface {

    List<Action> checkPossibleActions(Board board, Pawn designatedPawn);

}
