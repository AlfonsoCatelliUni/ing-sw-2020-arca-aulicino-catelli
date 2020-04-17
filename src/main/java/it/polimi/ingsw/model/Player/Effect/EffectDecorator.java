package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Player.Pawn;
import it.polimi.ingsw.model.Player.State.StateInterface;
import it.polimi.ingsw.model.Sex;


import java.util.List;

/**
 * this is the decorator of the effect, each type of decorator extends this class according with pattern decorator
 */
public class EffectDecorator implements Effect {


    protected Effect effect;


    // ======================================================================================
    // MARK : Constructor Section


    public EffectDecorator (Effect e){
        this.effect = e;
    }


    // ======================================================================================
    // MARK : Getter Section


    @Override
    public StateInterface getState() {
        return this.effect.getState();
    }


    // ======================================================================================
    // MARK : Setter Section


    @Override
    public void changeState(StateInterface state) {
        this.effect.changeState(state);
    }


    // ======================================================================================
    // MARK : Possibilities Control Section


    @Override
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        return this.effect.getPossibleActions(gameBoard,designatedPawn);
    }


    @Override
    public List<Cell> wherePawnCanMove(Board gameBoard, Pawn designatedPawn) {
        return this.effect.wherePawnCanMove(gameBoard, designatedPawn);
    }


    @Override
    public List<Cell> wherePawnCanBuild(Board gameBoard, Pawn designatedPawn) {
        return this.effect.wherePawnCanBuild(gameBoard, designatedPawn);
    }


    @Override
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell) {
        return this.effect.getPossibleBuildingOnCell(gameBoard, designatedCell);
    }


    @Override
    public List<Cell> wherePawnCanForce(Board gameBoard, Pawn designatedPawn) {
        return this.effect.wherePawnCanForce(gameBoard, designatedPawn);
    }


    @Override
    public List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn) {
        return this.effect.wherePawnCanDestroy(gameBoard, designatedPawn);
    }


    // ======================================================================================
    // MARK : Real Actions Section


    @Override
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {
        return this.effect.move(gameBoard,designatedPawn,nextPosition);
    }


    @Override
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        return this.effect.build(designatedPawn, designatedCell, chosenLevel, buildings);
    }


    @Override
    public void force(Pawn designatedPawn, Cell nextPosition) {
        this.effect.force(designatedPawn, nextPosition);
    }


    // ======================================================================================
    // MARK : Pawn Placing Section


    @Override
    public void removePawn(Board gameBoard, Pawn designatedPawn) {
        this.effect.removePawn(gameBoard, designatedPawn);
    }


    @Override
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {
        this.effect.placePawn(gameBoard, designatedPawn, designatedCell);
    }






}
