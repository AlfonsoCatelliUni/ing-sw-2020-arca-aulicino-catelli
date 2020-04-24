package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreGameLobbyTest {


    private PreGameLobby preGameLobby;

    private List<String> nicknames;


    @BeforeEach
    void setUp() {

        preGameLobby = new PreGameLobby();

        nicknames = new ArrayList<>();
        nicknames.add("Alfonso");
        nicknames.add("Massi");
        nicknames.add("Giamma");


    }


    @Test
    void addPlayer() {

        List<String> connectedPlayer;
        nicknames.add("Alfonso");
        nicknames.add("Massi");

        //first nickname
        Boolean isFreeNickname = preGameLobby.isNicknameAvailable(nicknames.get(0));

        assertTrue(isFreeNickname);
        preGameLobby.addPlayer(nicknames.get(0));

        connectedPlayer = preGameLobby.getConnectedPlayers();
        assertEquals(1, connectedPlayer.size());

        for(int i = 0; i < connectedPlayer.size(); i++)
            assertEquals(nicknames.get(i), connectedPlayer.get(i));


        //verifying the same nickname added before
        isFreeNickname = preGameLobby.isNicknameAvailable(nicknames.get(0));

        assertFalse(isFreeNickname);


        //new nickname, now verify it
        isFreeNickname = preGameLobby.isNicknameAvailable(nicknames.get(1));
        assertTrue(isFreeNickname);
        preGameLobby.addPlayer(nicknames.get(1));

        connectedPlayer = preGameLobby.getConnectedPlayers();
        assertEquals(2, connectedPlayer.size());

        for(int i = 0; i < connectedPlayer.size(); i++)
            assertEquals(nicknames.get(i), connectedPlayer.get(i));


        //if in a particular case it's added a player with the same name, than throws an exception
        assertThrows(RuntimeException.class, () -> {
            preGameLobby.addPlayer(nicknames.get(0));
        });


    }


    @Test
    void isNicknameAvailable() {

        //first nickname
        Boolean isFreeNickname = preGameLobby.isNicknameAvailable(nicknames.get(0));

        assertTrue(isFreeNickname);
        preGameLobby.addPlayer(nicknames.get(0));


        //verifying the same nickname added before
        isFreeNickname = preGameLobby.isNicknameAvailable(nicknames.get(0));

        assertFalse(isFreeNickname);


        //new nickname, now verify it
        isFreeNickname = preGameLobby.isNicknameAvailable(nicknames.get(1));
        assertTrue(isFreeNickname);
        preGameLobby.addPlayer(nicknames.get(1));

    }


    @Test
    void addCard() {

        preGameLobby.setNumberOfPlayers(2);
        preGameLobby.addPlayer(nicknames.get(0));
        preGameLobby.addPlayer(nicknames.get(1));


        String card = preGameLobby.getPickedCards().get(0).getName();

        preGameLobby.addCard("Alfonso", card);

        assertEquals(card, preGameLobby.getPlayerCardMap().get("Alfonso").getName());

        for (Card c : preGameLobby.getPickedCards()){
            assertNotEquals(card, c.getName());
        }

        assertThrows(RuntimeException.class, () -> {
            preGameLobby.addCard("Alfonso","CIAO");
        });

        assertThrows(RuntimeException.class, () -> {
            preGameLobby.addCard("Alfonso",preGameLobby.getPickedCards().get(1).getName());
        });


    }


    @Test
    void isCardAvailable() {

        preGameLobby.addPlayer(nicknames.get(0));
        preGameLobby.addPlayer(nicknames.get(1));
        preGameLobby.addPlayer(nicknames.get(2));

        List<Card> pickedCard = new ArrayList<>(preGameLobby.getPickedCards());

        assertEquals(3, pickedCard.size());

        for (Card c : pickedCard ) {
            System.out.println( c.getName() );
        }

        //first card to the first nickname
        Boolean isCardValid = preGameLobby.isCardAvailable(pickedCard.get(0).getName());
        assertEquals(true, isCardValid);

        preGameLobby.addCard(nicknames.get(0), pickedCard.get(0).getName());


        //the first card is no longer available
        isCardValid = preGameLobby.isCardAvailable(pickedCard.get(0).getName());
        assertEquals(false, isCardValid);


        //the second card to the second nickname
        isCardValid = preGameLobby.isCardAvailable(pickedCard.get(1).getName());
        assertEquals(true, isCardValid);

        preGameLobby.addCard(nicknames.get(1), pickedCard.get(1).getName());


        //the second card to the second nickname
        isCardValid = preGameLobby.isCardAvailable(pickedCard.get(2).getName());
        assertEquals(true, isCardValid);

        //exception in caso of same nickname
        assertThrows(RuntimeException.class, () -> {
            preGameLobby.addCard(nicknames.get(1), pickedCard.get(2).getName());
        });

        preGameLobby.addCard(nicknames.get(2), pickedCard.get(2).getName());


    }


    @Test
    void setNumberOfPlayers() {
    }


    @Test
    void pickCards() {





    }




}