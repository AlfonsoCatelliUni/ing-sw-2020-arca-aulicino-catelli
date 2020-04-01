package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.BuildAction;
import it.polimi.ingsw.model.Actions.MoveAction;
import it.polimi.ingsw.model.Actions.MoveConsequence;
import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.BoardPack.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Sex;

import java.util.List;

public class PlayerDecorator implements Player {


    protected Player player;


    // ======================================================================================


    public PlayerDecorator ( BasicPlayer player ) {
        this.player = player;
    }


    // ======================================================================================


    @Override
    public String getName() {
        return this.player.getName();
    }


    @Override
    public Color getColor() {
        return this.player.getColor();
    }


    @Override
    public Card getGodCard() {
        return this.player.getGodCard();
    }


    @Override
    public int getNumMove() {
        return player.getNumMove();
    }


    @Override
    public int getNumBuild() {
        return player.getNumBuild();
    }


    @Override
    public Boolean getCanMoveUp() {
        return player.getCanMoveUp();
    }


    @Override
    public Pawn[] getPawns() {
        return player.getPawns();
    }


    @Override
    public Pawn getPawnInCoordinates(int row, int column) {
        return player.getPawnInCoordinates(row, column);
    }


    // ======================================================================================


    @Override
    public void setName(String name) {
        player.setName(name);
    }


    @Override
    public void setColor(Color color) {
        player.setColor(color);
    }


    @Override
    public void setCard(Card card) {
        player.setCard(card);
    }


    @Override
    public void setNameGod(String nameGod) {
        player.setNameGod(nameGod);
    }


    @Override
    public void setNumMove(int numMove) {
        player.setNumMove(numMove);
    }


    @Override
    public void setNumBuild(int numBuild) {
        player.setNumBuild(numBuild);
    }


    @Override
    public void setCanMoveUp(Boolean canMoveUp) {
        player.setCanMoveUp(canMoveUp);
    }


    @Override
    public void resetNumMove() {
        player.resetNumMove();
    }


    @Override
    public void resetNumBuild() {
        player.resetNumBuild();
    }


    // ======================================================================================


    @Override
    public void initPawn(Board gameBoard, Sex sex, Cell cell) {
        this.player.initPawn(gameBoard, sex, cell);
    }


    @Override
    public MoveConsequence movePawn(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {
        return this.player.movePawn(gameBoard, designatedPawn, nextPosition);
    }


    @Override
    public void pawnBuild( Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        this.player.pawnBuild(designatedPawn, designatedCell, chosenLevel, buildings);
    }


    @Override
    public void forcePawn(Pawn designatedPawn, Cell nextPosition) {
        this.player.forcePawn(designatedPawn, nextPosition);
    }


    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        return this.player.wherePawnCanMove(gameBoard, designatedPawn);
    }


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        return this.player.wherePawnCanBuild(gameBoard, designatedPawn);
    }


    @Override
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell, List<Building> buildings) {
        return this.player.getPossibleBuildingOnCell(gameBoard, designatedCell, buildings);
    }


    @Override
    public List<Cell> getPawnsCoordinates(Board gameBoard) {
        return player.getPawnsCoordinates(gameBoard);
    }


    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {
        this.player.removePawn(gameBoard, designatedPawn);
    }


    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {
        this.player.placePawn(gameBoard, designatedPawn, designatedCell);
    }


    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        return player.getPossibleActions(gameBoard, designatedPawn);
    }



}
