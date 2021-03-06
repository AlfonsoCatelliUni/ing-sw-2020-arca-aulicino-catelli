package it.polimi.ingsw.model.Board;

public class Building {


    /**
     * type of building
     *      1 : level 1
     *      2 : level 2
     *      3 : level 3
     *      4 : dome
     */
    private final int level;


    /**
     * true if is a dome
     * we can identify if it's a dome by the level value
     * we keep this to better readability
     */
    private final Boolean isDome;


    /**
     * max quantity available for this specific type
     */
    private final int quantity;


    /**
     * number of piece placed for this specific type
     */
    private int placedNumber;


    // ======================================================================================


    public Building(int level, int quantity){
        this.level = level;

        this.isDome = level == 4;

        this.quantity = quantity;
        this.placedNumber = 0;
    }


    // ======================================================================================


    public int getLevel() {
        return level;
    }


    /**
     * how much of this type of building is available
     * @return the quantity available
     */
    public int getQuantity() {
        return quantity;
    }


    /**
     * is this building a dome ?
     * @return true if it's a dome
     */
    public Boolean getIsDome(){
        return isDome;
    }


    /**
     * there are remaining pieces of this building ?
     * @return how much pieces are available to be placed
     */
    public Boolean isAvailable() {
        return quantity - placedNumber != 0;
    }


    /**
     * @return the number of placed building of this type
     */
    public int getPlacedNumber() {
        return placedNumber;
    }


    // ======================================================================================


    //ONLY USED IN TESTING
    public void setPlacedNumber(int placedNumber) {
        this.placedNumber = placedNumber;
    }


    /**
     * when you place a building
     */
    public void increasePlacedQuantity(){
        if(placedNumber != quantity) {
            placedNumber++;
        }

    }


    /**
     * you can decrease the placed quantity of a piece
     * only if this piece is not a dome or if is not free
     */
    public void decreasePlacedQuantity(){

        if(placedNumber > 0) {
            placedNumber--;
        }


    }

}
