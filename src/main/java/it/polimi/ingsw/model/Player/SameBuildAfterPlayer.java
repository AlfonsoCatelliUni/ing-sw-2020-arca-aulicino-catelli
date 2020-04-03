package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.FinishAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;

import java.util.ArrayList;
import java.util.List;

public class SameBuildAfterPlayer extends PlayerDecorator {


    private Cell cellBefore;


    public SameBuildAfterPlayer(BasicPlayer player) {
        super(player);
        cellBefore = null;
    }


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellToBuild = new ArrayList<>();

        if( player.getNumBuild() == 0 ) {
            availableCellToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);
        }
        else if ( player.getNumBuild() == 1 ) {

            if( !cellBefore.getRoof().getIsDome() && cellBefore.getRoof().getLevel() != 3 ) {
                availableCellToBuild.add(cellBefore);
            }


        }

        return availableCellToBuild;

    }

    //TODO : CAMBIARE LA LOGICA
    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {

        List<Action> possibleActions = new ArrayList<>();

        if(super.player.getNumBuild() == 0) {
            possibleActions.add(new BuildAction());
        }
        else if (super.player.getNumMove() == 0) {
            possibleActions.add(new MoveAction());
        }

        else if ( super.player.getNumMove() == 1 && super.player.getNumBuild() == 1 ) {

                if( !cellBefore.getRoof().getIsDome() && cellBefore.getRoof().getLevel() != 3 ) {
                    possibleActions.add(new BuildAction());
                }

                possibleActions.add(new FinishAction());
        }
        else if( super.player.getNumBuild() == 2 && super.player.getNumMove() == 1 ) {
            possibleActions.add(new FinishAction());
        }

        return possibleActions;
    }


    @Override
    public void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        super.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);

        this.cellBefore = designatedCell;

    }


}
