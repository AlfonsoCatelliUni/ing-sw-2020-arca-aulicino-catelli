package it.polimi.ingsw.model.Player.Effect;

import it.polimi.ingsw.model.Board.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwitchEffectTest {

    private Board gameBoard;

    private Effect alfoEffect;
    private Card alfoCard;

    private Player alfoPlayer;
    private Player massiPlayer;
    private Player giammaPlayer;

    @BeforeEach
    void setUp() {
        gameBoard = new Board();

        alfoEffect = new BasicEffect();
        alfoEffect = new SwitchEffect(alfoEffect);
        alfoCard = new Card("switch_card", true, "switch_effect");

        alfoPlayer = new Player("alfonso", Color.BLUE, alfoCard, alfoEffect);
    }


    @Test
    void move() {
    }


}