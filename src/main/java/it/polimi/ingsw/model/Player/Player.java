package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Actions.Action;
import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Board.Cell;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Consequence.Consequence;
import it.polimi.ingsw.model.Player.Effect.BasicEffect;
import it.polimi.ingsw.model.Player.Effect.Effect;
import it.polimi.ingsw.model.Sex;

import java.util.ArrayList;
import java.util.List;


public class Player {

    private final int QUANTITY = 2;


    private String name;


    private Color color;


    private Card card;


    private List<Pawn> pawns;


    private Effect effect;


    // ======================================================================================


    public Player(String name, Color color, Card card, Effect effect) {
        this.name = name;
        this.color = color;

        this.card = card;
        this.effect = effect;

        this.pawns = new ArrayList<>(QUANTITY);
    }


    public Player() {
        this("", Color.BLUE, new Card("card", true, "effect"), new BasicEffect());
    }


    // ======================================================================================


    public String getName() {
        return this.name;
    }


    public Color getColor() {
        return this.color;
    }


    public Card getCard() {
        return this.card;
    }


    public Effect getEffect() {
        return this.effect;
    }


    public List<Pawn> getPawns() {
        return pawns;
    }


    public Pawn getPawnInCoordinates(int row, int column) {
        return pawns.stream().filter(p -> p.getPosition().getRowPosition() == row && p.getPosition().getColumnPosition() == column).findAny().orElse(null);
    }


    // ======================================================================================


    public void setName(String name) {
        this.name = name;
    }


    public void setColor(Color color) {
        this.color = color;
    }


    public void setCard(Card card) {
        this.card = card;
    }


    public void setEffect(Effect effect) {
        this.effect = effect;
    }


    /**
     * this method resets the status of the player when his turn ends
     */
    public void resetPlayerStatus() {
        for (Pawn p : pawns ) {
            p.resetPawnStatus();
        }
    }


    // ======================================================================================


    /**
     * this player method only calls getPossibleAction of its effect
     * @param gameBoard the game board
     * @param designatedPawn the pawn that wants to know its actions
     * @return the list of possible actions
     */
    public List<Action> getPossibleActions(Board gameBoard, Pawn designatedPawn) {
        return effect.getPossibleActions(gameBoard,designatedPawn);
    }


    /**
     * this player method only calls wherePawnCanMove of its effect
     * @param gameBoard the game board
     * @param designatedPawn the pawn that I want to move
     * @return the list of cells where the pawn can be moved
     */
    public List<Cell> wherePawnCanMove (Board gameBoard, Pawn designatedPawn){
        return effect.wherePawnCanMove(gameBoard, designatedPawn);
    }


    /**
     * this player method only calls wherePawnCanBuild of its effect
     * @param gameBoard the game board
     * @param designatedPawn the pawn that wants to build
     * @return the list of cells where the pawn can build
     */
    public List<Cell> wherePawnCanBuild (Board gameBoard, Pawn designatedPawn) {
        return effect.wherePawnCanBuild(gameBoard, designatedPawn);
    }


    /**
     * this player method only calls getPossibleBuildingOnCell of its effect
     * @param gameBoard the game board
     * @param designatedCell the designated cell to be built on
     * @return the list of possible buildings on this cell
     */
    public List<Building> getPossibleBuildingOnCell(Board gameBoard, Cell designatedCell) {
        return effect.getPossibleBuildingOnCell(gameBoard, designatedCell);
    }


    /**
     * this player method only calls wherePawnCanForce of its effect
     * @param gameBoard the game board
     * @param designatedPawn the pawn that I want to force
     * @return the list of cells
     */
    public List<Cell> getOpponentsNeighboring(Board gameBoard, Pawn designatedPawn) {
        return this.effect.getOpponentsNeighboring(gameBoard, designatedPawn);
    }


    /**
     * this player method only calls wherePawnCanDestroy of its effect
     * @param gameBoard the game board
     * @param designatedPawn the pawn that wants to destroy
     * @return the list of cells
     */
    public List<Cell> wherePawnCanDestroy(Board gameBoard, Pawn designatedPawn) {
        return this.effect.wherePawnCanDestroy(gameBoard, designatedPawn);
    }


    // ======================================================================================


    /**
     * this method calls the move method of its effect and add the name of the player
     * at the returning consequence
     * @param gameBoard the game board
     * @param designatedPawn the pawn that I'm moving
     * @param nextPosition the next position of the pawn
     * @return a {@link Consequence} based on the type of move
     */
    public Consequence move(Board gameBoard, Pawn designatedPawn, Cell nextPosition) {
        Consequence consequence = effect.move(gameBoard,designatedPawn,nextPosition);
        consequence.setNickname(name);

        return consequence;
    }


    /**
     * this method calls the build method of its effect and add
     * the name of the player ar the returning consequence
     * @param designatedPawn the pawn that is building
     * @param designatedCell the cell where the pawn is building
     * @param chosenLevel the level of the chosen building
     * @param buildings the list of all possible buildings
     * @return a {@link Consequence} based on the type of build
     */
    public Consequence build(Pawn designatedPawn, Cell designatedCell, int chosenLevel, List<Building> buildings) {
        Consequence consequence = effect.build(designatedPawn, designatedCell, chosenLevel, buildings);
        consequence.setNickname(name);

        return consequence;
    }


    /**
     * this method only calls the force of its effect
     * @param designatedPawn the pawn that I'm forcing
     * @param designatedCell its next cell
     */
    public void force(Pawn designatedPawn, Cell designatedCell) {
        this.effect.force(designatedPawn, designatedCell);
    }


    /**
     * this method only calls the destroy of its effect
     * @param designatedCell the cell where I want to destroy a block
     * @param buildings the list of all buildings in the game
     */
    public void destroy(Cell designatedCell, List<Building> buildings) {
        this.effect.destroy(designatedCell, buildings);
    }


    // ======================================================================================


    /**
     * this method initiate one male pawn and one female pawn and place them on the board
     * @param gameBoard the board where we have to place the pawns
     * @param startCell the cell where to place the pawn initiated
     */
    public void initPawn(Board gameBoard, Cell startCell ) {

        if(pawns.size() == 0) {
            this.pawns.add(new Pawn(this.color, Sex.MALE, startCell));
            placePawn( gameBoard, this.pawns.get(0), startCell);
        }
        else if(pawns.size() == 1) {
            this.pawns.add(new Pawn(this.color, Sex.FEMALE, startCell));
            placePawn( gameBoard, this.pawns.get(1), startCell);
        }

    }


    /**
     * place the pawn on the board
     * @param gameBoard the board where to place the pawn
     * @param designatedPawn the pawn that we have to place
     * @param designatedCell the cell where place the pawn
     */
    public void placePawn(Board gameBoard, Pawn designatedPawn, Cell designatedCell) {
        effect.placePawn(gameBoard, designatedPawn, designatedCell);

    }


    /**
     * remove a pawn from the board
     * @param gameBoard we have to remove the pawn form this board
     * @param designatedPawn the pawn to remove
     */
    public void removePawn(Board gameBoard, Pawn designatedPawn) {
        effect.removePawn(gameBoard, designatedPawn);
    }


}


