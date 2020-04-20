package it.polimi.ingsw.model.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    Card card;

    @BeforeEach
    void setUp() {
        card = new Card("testCard", true, "effect");
    }

    @Test
    void getName() {
        assertEquals("testCard", card.getName());
    }

    @Test
    void isAvailable3P() {
        assertTrue(card.isAvailable3P());
    }

    @Test
    void getEffect() {
        assertEquals("effect", card.getEffect());
    }
}