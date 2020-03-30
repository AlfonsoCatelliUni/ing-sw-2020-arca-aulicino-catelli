package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Pawn;

import java.util.ArrayList;

public class SwitchPlayer extends PlayerDecorator {


    public SwitchPlayer(BasicPlayer player) {
        super(player);
    }


    // ======================================================================================


    @Override
    public ArrayList<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {

        ArrayList<Cell> availableCellsToMove = super.wherePawnCanMove(gameBoard, designatedPawn);

        ArrayList<Cell> neighboringCells = gameBoard.getNeighboring( designatedPawn.getPosition() );

        for (Cell c : neighboringCells ) {
            if ( !availableCellsToMove.contains(c) && c.getBuilderHere() &&
                    c.getPawnInThisCell().getColor() != designatedPawn.getColor() &&
                    c.getHeight() - designatedPawn.getzPosition() <= 1 &&
                    super.player.wherePawnCanBuild(gameBoard, c.getPawnInThisCell()).size() > 0 ) {
                availableCellsToMove.add(c);
            }
        }


        if( !super.player.getCanMoveUp() ) {
            for ( Cell c : availableCellsToMove ) {
                if( c.getHeight() - designatedPawn.getzPosition() == 1 ) {
                    availableCellsToMove.remove(c);
                }
            }
        }

        return availableCellsToMove;

    }


    @Override
    public int movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

        int moveRetEncoded = 0;


        if( nextPosition.getBuilderHere() ) {

            Pawn opponentPawn = nextPosition.getPawnInThisCell();
            Cell myPawnCell = designatedPawn.getPosition();

            removePawn(gameBoard, nextPosition.getPawnInThisCell());

            moveRetEncoded = super.movePawn(gameBoard, designatedPawn, nextPosition);

            super.forcePawn(opponentPawn, myPawnCell);

            placePawn(gameBoard, opponentPawn, myPawnCell);

        }
        else {
            moveRetEncoded = super.movePawn(gameBoard, designatedPawn, nextPosition);
        }

        return moveRetEncoded;
    }


    // ======================================================================================


    @Override
    public ArrayList<String> getPossibleAction(Board gameBoard, Pawn designatedPawn) {

        ArrayList<String> availableActions = new ArrayList<>();

        if( super.player.getNumMove() == 0 && wherePawnCanMove(gameBoard, designatedPawn).size() != 0) {
            availableActions.add("move");
        }
        else if( super.player.getNumBuild() == 0 ) {
            availableActions.add("build");
        }
        else if( super.player.getNumBuild() == 1 && super.player.getNumMove() == 1 ) {
            availableActions.add("finish");
        }


        return availableActions;
    }


}
