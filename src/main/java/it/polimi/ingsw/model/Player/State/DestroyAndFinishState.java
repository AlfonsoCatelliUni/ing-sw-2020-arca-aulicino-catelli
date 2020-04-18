package it.polimi.ingsw.model.Player.State;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.DestroyAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.GeneralAction;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Player.Pawn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DestroyAndFinishState implements StateInterface {


    private final Effect effect;


    // ======================================================================================


    public DestroyAndFinishState(Effect effect) {
        this.effect = effect;
    }


    // ======================================================================================


    public List<Action> checkPossibleActions(Board gameBoard, Pawn designatedPawn) {

        /*
        Pawn nMovedPawn = null;

         for(int i = 0; i < gameBoard.getMatrixBoard().length; i++) {
            for (int j = 0; j < gameBoard.getMatrixBoard().length; j++) {
                if (gameBoard.getCell(i, j).getBuilderHere()) {
                    if (gameBoard.getCell(i, j).getPawnInThisCell().getColor() == designatedPawn.getColor() && gameBoard.getCell(i, j).getPawnInThisCell().getSex() != designatedPawn.getSex()) {
                        nMovedPawn = gameBoard.getCell(i, j).getPawnInThisCell();
                    }
                }
            }
        }

        */

        List<Cell> matrixBoard = gameBoard.matrix();

        Cell designatedCell = matrixBoard.stream()
                .filter(Cell::getBuilderHere)
                .filter(cell -> cell.getPawnInThisCell().getColor().equals(designatedPawn.getColor()))
                .filter(cell -> !(cell.getPawnInThisCell().getSex().equals(designatedPawn.getSex())))
                .findFirst().orElse(null);

        Pawn notMovedPawn = null;

        if(designatedCell != null)
            notMovedPawn = designatedCell.getPawnInThisCell();


        List<Action> possibleActions = new ArrayList<>();
        possibleActions.add(new FinishAction());

        if(effect.wherePawnCanDestroy(gameBoard, notMovedPawn).size() > 0) {
            possibleActions.add(new DestroyAction());
        }

        return possibleActions;
    }


}
