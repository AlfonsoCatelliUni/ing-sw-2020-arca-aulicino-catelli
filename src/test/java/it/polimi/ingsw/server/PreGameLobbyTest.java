package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    void addCard() {

        String card = "";

        //Setting players and closing the waitingRoom
        preGameLobby.setNumberOfPlayers(2);
        preGameLobby.addPlayer(nicknames.get(0));
        preGameLobby.addPlayer(nicknames.get(1));


        //first test, normal bonding player-card
        card = preGameLobby.getPickedCards().get(0).getName();
        preGameLobby.addCard("Alfonso", card);

        assertEquals(card, preGameLobby.getPlayerCardMap().get("Alfonso").getName());
        for (Card c : preGameLobby.getPickedCards()){
            assertNotEquals(card, c.getName());
        }


        //second test, throw an exception because the card name is not present in pickedCards
        assertThrows(RuntimeException.class, () -> {
            preGameLobby.addCard("Alfonso","CIAO");
        });


        //third test, throws and exception because the player is already bond to a card
        assertThrows(RuntimeException.class, () -> {
            preGameLobby.addCard("Alfonso", preGameLobby.getPickedCards().get(1).getName());
        });


        //fourth test, throws and exception because the player is not in the waitingRoom
        assertThrows(RuntimeException.class, () -> {
            preGameLobby.addCard("BELLO", preGameLobby.getPickedCards().get(1).getName());
        });


        //fifth test, normal bond player-card but not the pickedCards are finished
        card = preGameLobby.getPickedCards().get(0).getName();
        preGameLobby.addCard("Massi", card);

        assertEquals(card, preGameLobby.getPlayerCardMap().get("Massi").getName());
        assertEquals(0, preGameLobby.getPickedCards().size());


    }


    //TODO : guardare
//    @Test
//    void isCardAvailable() {
//
//        preGameLobby.addPlayer(nicknames.get(0));
//        preGameLobby.addPlayer(nicknames.get(1));
//        preGameLobby.addPlayer(nicknames.get(2));
//
//        List<Card> pickedCard = new ArrayList<>(preGameLobby.getPickedCards());
//
//        //assertEquals(3, pickedCard.size());
//
//        for (Card c : pickedCard ) {
//            System.out.println( c.getName() );
//        }
//
//        //first card to the first nickname
//        Boolean isCardValid = preGameLobby.isCardAvailable(pickedCard.get(0).getName());
//        assertEquals(true, isCardValid);
//
//        preGameLobby.addCard(nicknames.get(0), pickedCard.get(0).getName());
//
//
//        //the first card is no longer available
//        isCardValid = preGameLobby.isCardAvailable(pickedCard.get(0).getName());
//        assertEquals(false, isCardValid);
//
//
//        //the second card to the second nickname
//        isCardValid = preGameLobby.isCardAvailable(pickedCard.get(1).getName());
//        assertEquals(true, isCardValid);
//
//        preGameLobby.addCard(nicknames.get(1), pickedCard.get(1).getName());
//
//
//        //the second card to the second nickname
//        isCardValid = preGameLobby.isCardAvailable(pickedCard.get(2).getName());
//        assertEquals(true, isCardValid);
//
//        //exception in caso of same nickname
//        assertThrows(RuntimeException.class, () -> {
//            preGameLobby.addCard(nicknames.get(1), pickedCard.get(2).getName());
//        });
//
//        preGameLobby.addCard(nicknames.get(2), pickedCard.get(2).getName());
//
//
//    }


    @Test
    void pickCards() {

        int numberOfPlayers = new Random().nextInt(2) + 2;
        String card = "";
        List<Card> pickedCards;

        //Setting random players and closing the waitingRoom
        preGameLobby.setNumberOfPlayers(numberOfPlayers);
        for(int i = 0; i < numberOfPlayers; i++) {
            preGameLobby.addPlayer(nicknames.get(i));
        }

        pickedCards = preGameLobby.getPickedCards();
        assertEquals(numberOfPlayers, pickedCards.size());
        for (Card c : pickedCards ) {
            assertTrue(preGameLobby.getAllCards().contains(c));
        }











    }


    @Test
    void isNicknameValid() {

        //At least 6 chars
        String nickname = "";
        assertFalse(preGameLobby.isNicknameValid(nickname));

        //ok
        nickname = "alfonso";
        assertTrue(preGameLobby.isNicknameValid(nickname));

        //can't starts with number
        nickname = "9iiiiiii";
        assertFalse(preGameLobby.isNicknameValid(nickname));

        //max 30 cahrs
        nickname = "oooooooooooooooooooooooooooooooooooooooooooooooo";
        assertFalse(preGameLobby.isNicknameValid(nickname));

        //ok, can contain capital letters
        nickname = "aFlfons3_";
        assertTrue(preGameLobby.isNicknameValid(nickname));

        //ok, can contain _
        nickname = "Alfonso_2_2_s";
        assertTrue(preGameLobby.isNicknameValid(nickname));

        //ok, can stars with _
        nickname = "_alfonso";
        assertTrue(preGameLobby.isNicknameValid(nickname));

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



}