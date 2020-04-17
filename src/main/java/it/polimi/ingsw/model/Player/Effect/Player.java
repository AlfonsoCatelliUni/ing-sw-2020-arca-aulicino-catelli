package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Sex;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;


    private Color color;


    private Card godCard;


    private List<Pawn> pawns;


    private String nameGodCard;


    private Boolean canMoveUp;


    private Effect effect;


    public Player(Effect effect) {
        this.effect = effect;
        this.pawns = new ArrayList<>(2);
    }

    public Effect getEffect() {
        return effect;
    }

    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn){
        return effect.getPossibleActions(gameBoard,designatedPawn);
    }

    public void initPawn(Board gameBoard, Cell startCell ) {

        /* control if the pawn is already present */
        if(pawns.size() == 0) {
            this.pawns.add(new Pawn(this.color, Sex.MALE, startCell));
            placePawn( gameBoard, this.pawns.get(0), startCell);
        }
        else if (pawns.size() == 1){
            this.pawns.add(new Pawn(this.color, Sex.FEMALE, startCell));
            placePawn( gameBoard, this.pawns.get(1), startCell);
        }

    }

    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {

        int rowPosition = designatedCell.getRowPosition();
        int columnPosition = designatedCell.getColumnPosition();

        gameBoard.getCell(rowPosition, columnPosition).placePawnHere(designatedPawn);

    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {
        Consequence consequence = effect.move(gameBoard,designatedPawn,nextPosition);
        consequence.setNickname(name);
        return consequence;
    }

    public List<Cell> wherePawnCanMove (Board gameBoard, Pawn designatedPawn){
        return effect.wherePawnCanMove(gameBoard, designatedPawn);
    }

    public List<Cell> wherePawnCanBuild (Board gameBoard, Pawn designatedPawn){
        return effect.wherePawnCanBuild(gameBoard, designatedPawn);
    }


    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        return effect.build(designatedPawn, designatedCell, chosenLevel, buildings);
    }

}


