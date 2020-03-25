package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.Sex;

import java.util.ArrayList;

public class BasicPlayer implements Player {


    private String name;


    private Color color;


    private Card godCard;


    private String nameGodCard;


    private Pawn[] pawns;


//    private Boolean canBuildBeforeMove;


    private Boolean canBuildAgain;


    private Boolean canMoveAgain;


    // ======================================================================================


    public BasicPlayer() {
        this.name = "";
        this.color = null;
        this.godCard = null;
        this.pawns = new Pawn[2];
    }


    public BasicPlayer(String name, Color color, String nameGodCard) {
        this.name = name;
        this.color = color;
        this.nameGodCard = nameGodCard;
        this.godCard = null;
        this.pawns = new Pawn[2];
    }


    // ======================================================================================


    public String getName() {
        return name;
    }


    public Color getColor() {
        return color;
    }


    public Card getGodCard() {
        return godCard;
    }


    public Pawn[] getPawns() {
        return pawns;
    }


    // ======================================================================================

    //TODO : implements all of this methods


    @Override
    public void initPawn(Board gameBoard, Color color, Sex sex, Cell startCell ) {

        /* control if the pawn is already present */
        if( sex == Sex.MALE) {
            this.pawns[0] = new Pawn(color, sex, startCell);
            placePawn( gameBoard, this.pawns[0], startCell);
        }
        else if (sex == Sex.FEMALE){
            this.pawns[1] = new Pawn(color, sex, startCell);
            placePawn( gameBoard, this.pawns[1], startCell);
        }

    }


    @Override
    public void movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {

    }


    @Override
    public void pawnBuild(Pawn designatedPawn, Cell designatedCell, int chosenLevel, ArrayList<Building> buildings) {

    }


    @Override
    public void forcePawn(Pawn designatedPawn, Cell nextPosition) {

    }


    @Override
    public ArrayList<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        return null;
    }


    @Override
    public ArrayList<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        return null;
    }


    @Override
    public ArrayList<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell, ArrayList<Building> buildings) {
        return null;
    }


    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {

        int rowPosition = designatedPawn.getPosition().getRowPosition();
        int columnPosition = designatedPawn.getPosition().getColumnPosition();

        gameBoard.getCell(rowPosition, columnPosition).freeCell();
    }


    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {

        int rowPosition = designatedCell.getRowPosition();
        int columnPosition = designatedCell.getColumnPosition();

        gameBoard.getCell(rowPosition, columnPosition).placePawnHere(designatedPawn);

    }


    @Override
    public ArrayList<String> getPossibleAction() {
        return null;
    }


}
