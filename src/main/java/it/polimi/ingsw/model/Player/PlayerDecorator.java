package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;

import java.util.ArrayList;

public class PlayerDecorator implements Player {


    protected Player player;


    // ======================================================================================


    public PlayerDecorator ( BasicPlayer player ) {
        this.player = player;
    }


    // ======================================================================================


    @Override
    public void movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {
        player.movePawn(gameBoard, designatedPawn, nextPosition);
    }


    @Override
    public void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, ArrayList<Building> buildings) {
        player.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);
    }


    @Override
    public void forcePawn(Pawn designatedPawn, Cell nextPosition) {
        player.forcePawn(designatedPawn, nextPosition);
    }


    @Override
    public void initPawn(Board gameBoard, Color color, Sex sex, Cell cell) {
        player.initPawn(gameBoard, color, sex, cell);
    }


    @Override
    public ArrayList<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        return player.wherePawnCanMove(gameBoard, designatedPawn);
    }


    @Override
    public ArrayList<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        return player.wherePawnCanBuild(gameBoard, designatedPawn);
    }


    @Override
    public ArrayList<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell, ArrayList<Building> buildings) {
        return player.getPossibleBuildingOnCell(gameBoard, designatedCell, buildings);
    }


    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {
        player.removePawn(gameBoard, designatedPawn);
    }


    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {
        player.placePawn(gameBoard, designatedPawn, designatedCell);
    }


    @Override
    public ArrayList<String> getPossibleAction() {
        return player.getPossibleAction();
    }


    @Override
    public String getName() {
        return player.getName();
    }


    @Override
    public Color getColor() {
        return player.getColor();
    }


}
