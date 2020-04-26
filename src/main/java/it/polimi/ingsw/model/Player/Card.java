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
    private String effectDescription;


    /**
     * this is the base effect of the card where the player starts his turn
     */
    private Effect baseEffect;


    //private Effect eff;


    // ======================================================================================


    public Card(String name, Boolean available3P, String effectDescription) {
        this.name = name;
        this.available3P = available3P;
        this.effectDescription = effectDescription;
    }


    public Card(String name, Boolean available3P, String effectDescription, Effect baseEffect) {
        this.name = name;
        this.available3P = available3P;
        this.effectDescription = effectDescription;
        this.baseEffect = baseEffect;
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


    public String getEffectDescription() {
        return effectDescription;
    }


    public Effect getBaseEffect() {
        return baseEffect;
    }


    public void setBaseEffect(Effect baseEffect) {
        this.baseEffect = baseEffect;
    }
}
