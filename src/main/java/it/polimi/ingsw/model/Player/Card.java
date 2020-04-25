package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Player.Effect.Effect;

/**
 * the Card of the player, each player chooses one card, it's a description,
 * the player will be decorated according to the card chosen
 */
public class Card {


    /**
     * identify the card
     */
    private String name;


    /**
     * is it for a 3 player match?
     */
    private Boolean available3P;


    /**
     * effects of the card
     */
    private String effect;


    //private Effect eff;


    // ======================================================================================


    public Card(String name, Boolean available3P, String effect) {
        this.name = name;
        this.available3P = available3P;
        this.effect = effect;
    }


    // ======================================================================================


    public String getName() {
        return name;
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
