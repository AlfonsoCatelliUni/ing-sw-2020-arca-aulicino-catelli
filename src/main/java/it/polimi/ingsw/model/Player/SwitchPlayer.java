package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;

import java.util.ArrayList;
import java.util.List;

public class SwitchPlayer extends PlayerDecorator {


    public SwitchPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    /**
     * this method is different from the BasicPlayer because it eliminates
     * the cells where you can switch with an opponent player but once you've
     * switched you cannot build in any cell
     * @param gameBoard the gameBoard
     * @param designatedPawn the pawn that i want to know the available cells to move
     * @return the list of available cells where the pawn can be moved
     */
    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);

        List<Cell> neighboringCells = gameBoard.getNeighboring( designatedPawn.getPosition() );

        for (Cell c : neighboringCells ) {
            if ( !availableCellsToMove.contains(c) && c.getBuilderHere() &&
                    c.getPawnInThisCell().getColor() != designatedPawn.getColor() &&
                    c.getHeight() - designatedPawn.getHeight() <= 1 &&
                    super.player.wherePawnCanBuild(gameBoard, c.getPawnInThisCell()).size() > 0 ) {
                availableCellsToMove.add(c);
            }
        }


        if( !super.player.getCanMoveUp() ) {
            availableCellsToMove.removeIf(c -> c.getHeight() - designatedPawn.getHeight() == 1);
        }

        return availableCellsToMove;

    }


    /**
     * this is the method that actually move the pawn, in case of switching with
     * an opponent pawn this method force the opponent pawn in the designatedPawn
     * starting cell
     * @param gameBoard the gameBoard
     * @param designatedPawn the pawn that i want to move
     * @param nextPosition the new position of the pawn
     * @return an encoded value that indicates if the pawn moved on a third level
     */
    @Override
    public Action movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        Action resultAction;


        if( nextPosition.getBuilderHere() ) {

            Pawn opponentPawn = nextPosition.getPawnInThisCell();
            Cell myPawnCell = designatedPawn.getPosition();

            removePawn(gameBoard, nextPosition.getPawnInThisCell());

            resultAction = super.movePawn(gameBoard, designatedPawn, nextPosition);

            super.forcePawn(opponentPawn, myPawnCell);

            placePawn(gameBoard, opponentPawn, myPawnCell);

        }
        else {
            resultAction = super.movePawn(gameBoard, designatedPawn, nextPosition);
        }

        return resultAction;
    }


    /**
     * this method is different from the BasicPlayer method because it eliminates the
     * possibility of moving if the pawn wants only can switch with an opponent pawn
     * but once it moved into the opponent cell it cannot build
     * @param gameBoard the game board
     * @param designatedPawn the pawn that I want to know the possible actions
     * @return the list of possible action with the chosen pawn
     */
    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> availableActions = new ArrayList<>();

        if( super.player.getNumMove() == 0 && wherePawnCanMove(gameBoard, designatedPawn).size() != 0) {
            availableActions.add(new MoveAction());
        }
        else if( super.player.getNumBuild() == 0 ) {
            availableActions.add(new BuildAction());
        }
        else if( super.player.getNumBuild() == 1 && super.player.getNumMove() == 1 ) {
            availableActions.add(new FinishAction());
        }


        return availableActions;
    }


}
