package it.polimi.ingsw.model.Actions;

public class FinishAction implements Action {


    private int pawnRow;


    private int pawnColumn;


    private int actionRow;


    private int actionColumn;


    // ======================================================================================


    public FinishAction() {
        this.pawnRow = -1;
        this.pawnColumn = -1;

        this.actionRow = -1;
        this.actionColumn = -1;
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


    @Override
    public void doAction() {

    }
}
