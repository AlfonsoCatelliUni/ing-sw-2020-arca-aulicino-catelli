package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Pawn;

import java.util.ArrayList;
import java.util.List;

public class DoubleBuildPlayer extends PlayerDecorator {
    
    
    private int typeOfSecondBuild;

    private Cell cellBefore;


    public DoubleBuildPlayer(BasicPlayer player, int typeOfSecondBuild) {
        super(player);
        this.typeOfSecondBuild = typeOfSecondBuild;
        cellBefore = null;
    }


    @Override
    public List<String> getPossibleAction(Board gameBoard, Pawn designatedPawn)  {

        List<String> possibleActions = new ArrayList<>();

        if(super.player.getNumBuild() == 0) {
            possibleActions.add("build");
        }
        else if (super.player.getNumMove() == 0) {
            possibleActions.add("move");
        }
        /* devo fare i controlli per la seconda costruzione */
        else if ( super.player.getNumMove() == 1 && super.player.getNumBuild() == 1 ) {

            List<Cell> availableCellToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);

            /* can build but not on the same place */
            if(typeOfSecondBuild == 0) {
                availableCellToBuild.remove(cellBefore);
                if( availableCellToBuild.size() > 0 ) {
                    possibleActions.add("build");
                }
            }

            /* can build only on the previous cell */
            else if ( typeOfSecondBuild == 1) {
                if( !cellBefore.getRoof().getIsDome() && cellBefore.getRoof().getLevel() != 3 ) {
                    possibleActions.add("build");
                }
            }

            possibleActions.add("finish");
        }
        else if( super.player.getNumBuild() == 2 && super.player.getNumMove() == 1 ) {
            possibleActions.add("finish");
        }

        return possibleActions;
    }


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {

        List<Cell> availableCellToBuild = new ArrayList<>();

        if( player.getNumBuild() == 0 ) {
            availableCellToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);
        }
        else if ( player.getNumBuild() == 1 ) {

            availableCellToBuild = player.wherePawnCanBuild(gameBoard, designatedPawn);

            /* can build but not on the same place */
            if(typeOfSecondBuild == 0) {
                availableCellToBuild.remove(cellBefore);
            }

            /* can build only on the previous cell */
            else if ( typeOfSecondBuild == 1) {
                availableCellToBuild.clear();
                if( !cellBefore.getRoof().getIsDome() && cellBefore.getRoof().getLevel() != 3 ) {
                    availableCellToBuild.add(cellBefore);
                }
            }


        }

        return availableCellToBuild;
    }


    @Override
    public void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, ArrayList<Building> buildings) {
        super.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);

        this.cellBefore = designatedCell;

    }

    // only for test
    public void setCellBefore(Cell cellBefore) {
        this.cellBefore = cellBefore;
    }
}
