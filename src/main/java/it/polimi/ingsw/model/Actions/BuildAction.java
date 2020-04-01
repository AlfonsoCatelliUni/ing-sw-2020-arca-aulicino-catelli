package it.polimi.ingsw.model.Actions;

public class BuildAction implements Action {


    private int pawnRow;


    private int pawnColumn;


    private int actionRow;


    private int actionColumn;


    private int levelBuilding;


    // ======================================================================================


    public BuildAction() {
        this.pawnRow = -1;
        this.pawnColumn = -1;

        this.actionRow = -1;
        this.actionColumn = -1;

        this.levelBuilding = -1;
    }


    // ======================================================================================


    public int getPawnRow() {
        return pawnRow;
    }


    public int getPawnColumn() {
        return pawnColumn;
    }


    public int getActionRow() {
        return actionRow;
    }


    public int getActionColumn() {
        return actionColumn;
    }


    public int getLevelBuilding() {
        return levelBuilding;
    }


    @Override
    public void doAction() {

    }
}
