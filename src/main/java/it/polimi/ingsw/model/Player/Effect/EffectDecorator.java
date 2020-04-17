package it.polimi.ingsw.model.Player.Effect;



import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Actions.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.StateInterface;
import it.polimi.ingsw.model.Sex;


import java.util.List;

public class EffectDecorator implements Effect {

    protected Effect effect;


    public EffectDecorator (Effect e){
        this.effect = e;
    }


    @Override
    public StateInterface getState() {
        return effect.getState();
    }

    @Override
    public void changeState(StateInterface state) {
        effect.changeState(state);

    }

    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {
        return this.effect.move(gameBoard,designatedPawn,nextPosition);
    }

    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        return this.effect.wherePawnCanMove(gameBoard, designatedPawn);
    }

    @Override
    public void forcePawn(Pawn designatedPawn, Cell nextPosition) {

    }

    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        return null;
    }

    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        return this.effect.wherePawnCanBuild(gameBoard, designatedPawn);
    }

    @Override
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell) {
        return null;
    }

    @Override
    public void initPawn(Board gameBoard, Sex sex, Cell cell) {

    }

    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        return this.effect.getPossibleActions(gameBoard,designatedPawn);
    }

    @Override
    public List<Cell> getPawnsCoordinates(Board gameBoard) {
        return null;
    }

    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {

    }

    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {

    }
}
