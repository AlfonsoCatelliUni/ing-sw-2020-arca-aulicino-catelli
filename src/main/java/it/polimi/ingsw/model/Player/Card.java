package it.polimi.ingsw.model.Player;

public class Card {


    /**
     * identify the card
     */
    private String name;


    /**
     * is it for a 2 players match?
     */
    private Boolean available2P;


    /**
     * is it for a 3 player match?
     */
    private Boolean available3P;


    /**
     * effects of the card
     */
    private String effect;


    // ======================================================================================


    public Card(String name, Boolean available2P, Boolean available3P, String effect) {
        this.name = name;
        this.available2P = available2P;
        this.available3P = available3P;
        this.effect = effect;
    }


    // ======================================================================================


    public String getName() {
        return name;
    }


    /**
     * @return if true, card can be used during 2 players match
     */
    public Boolean isAvailable2P() {
        return available2P;
    }


    /**
     * @return if true, card can be used during 3 players match
     */
    public Boolean isAvailable3P() {
        return available3P;
    }


    public String getEffect() {
        return effect;
    }


}
