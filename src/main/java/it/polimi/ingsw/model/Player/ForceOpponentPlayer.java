package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.ForceAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;

import java.util.ArrayList;
import java.util.List;

public class ForceOpponentPlayer extends PlayerDecorator {

    public ForceOpponentPlayer(BasicPlayer player) {
        super(player);
    }


    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        List<Action> availableActions;

        availableActions = super.getPossibleActions(gameBoard, designatedPawn);

        if (player.getNumBuild() == 1 && player.getNumMove() == 0 && wherePawnCanForce(gameBoard,designatedPawn).size() != 0)
            availableActions.add(new ForceAction());

        return availableActions;

    }


    @Override
    public List<Cell> wherePawnCanForce (Board gameBoard, Pawn designatedPawn){

        List<Cell> neighboringCell = gameBoard.getNeighboring( designatedPawn.getPosition() );
        List<Cell> opponentNeighboringCell = new ArrayList<>();

        for (Cell c : neighboringCell){
            if (c.getPawnInThisCell() != null && gameBoard.getSymmetrical( designatedPawn.getPosition(), c ) != null)
                opponentNeighboringCell.add(c);
        }
        return opponentNeighboringCell;
    }




}
