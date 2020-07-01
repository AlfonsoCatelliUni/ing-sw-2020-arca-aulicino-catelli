package it.polimi.ingsw.server;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.model.Player.*;

import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PreGameLobby {

    private final String regex = "^[aA-zZ]\\w{5,29}$";


    private int numberOfPlayers;


    /**
     * Closed indicates if this PreGameLobby is closed to new connections
     */
    private Boolean closed;


    /**
     * this is the list of the nicknames of the player in the current Lobby
     */
    private List<String> playersNicknames;


    /**
     * the deck of all cards
     */
    private List<Card> allCards;


    /**
     * the players have to choose their card from this
     * restricted number of picked cards
     */
    private List<Card> pickedCards;


    /**
     * this map the player to his chosen card
     */
    private Map<String, Card> playerCardMap;


    private final Object lockClose;


    private final Object lockNicknames;


    // ======================================================================================


    public PreGameLobby() {

        //players init
        this.numberOfPlayers = -1;
        this.closed = false;

        this.playersNicknames = new ArrayList<>();

        //cards init
        this.pickedCards = new ArrayList<>();
        this.playerCardMap = new HashMap<>();

        this.allCards = JsonHandler.deserializeCardList();

        this.lockClose = new Object();
        this.lockNicknames = new Object();
    }


    // ======================================================================================


    public Boolean isClosed() {

        synchronized (lockClose) {
            return this.closed;
        }
    }


    public void setNumberOfPlayers(int numberOfPlayers) {

            this.numberOfPlayers = numberOfPlayers;
            if (playersNicknames.size() == numberOfPlayers)
                closeLobby();

    }


    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }


    public List<String> getConnectedPlayers() {

        synchronized (lockNicknames) {
            return this.playersNicknames;
        }
    }


    public Map<String, Card> getPlayerCardMap() {
        return playerCardMap;
    }


    public Card getCardOfPlayer(String p){
        return playerCardMap.get(p);
    }


    public List<Card> getPickedCards() {
        return this.pickedCards;
    }


    // ======================================================================================





    /**
     * synchronized because there could be conflicts with the timer
     */
    public void closeLobby() {

        synchronized (lockClose) {
            if (!closed) {
                this.closed = true;
                //pickCards(allCards);

            }
        }
    }


    public void addPlayer(String nickname) {

        if (!isNicknameAvailable(nickname)) {
            throw new RuntimeException("This Lobby already contains this player");
        }

        synchronized (lockNicknames) {
            playersNicknames.add(nickname);
        }

    }


    public void addCard (String nickname, String cardName) {

        Card chosenCard = pickedCards.stream().filter(c -> c.getName().equals(cardName)).findAny().orElse(null);

        if(chosenCard == null) {
            throw new RuntimeException("Invalid Chosen Card!");
        }
        if(playerCardMap.containsKey(nickname) || !playersNicknames.contains(nickname)) {
            throw new RuntimeException("Invalid Nickname!");
        }

        playerCardMap.put( nickname, chosenCard );
        pickedCards.remove(chosenCard);

    }


    protected void pickCards(List<Card> cards) {

        int cardsQuantity = playersNicknames.size();
        Random random = new Random();
        List<Integer> cardsIndex = new ArrayList<>();

        Integer num = random.nextInt(cards.size());

        while(cardsIndex.size() < cardsQuantity) {

            if(!cardsIndex.contains(num)) {
                cardsIndex.add(num);
            }

            num = random.nextInt(cards.size());
        }

        for (Integer i : cardsIndex ) {
            pickedCards.add(allCards.get(i));
        }
    }


    public  void deletePlayerInformation(String nickname) {

        try {
            synchronized (lockNicknames) {
                playersNicknames.removeIf(n -> n.equals(nickname));
                playerCardMap.remove(nickname);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }


    public void clearLobby(){
        this.playersNicknames.clear();
        this.numberOfPlayers = -1;
        this.pickedCards.clear();
        this.playerCardMap.clear();
        this.closed = false;


    }


    // ======================================================================================


    public Boolean isNicknameAvailable(String nickname) {
        synchronized (lockNicknames) {
            return !playersNicknames.contains(nickname);
        }
    }


    public Boolean isNicknameValid(String nickname) {
        return Pattern.matches(regex, nickname);
    }


    public synchronized Boolean isCardAvailable(String cardName) {

        for ( Card c : pickedCards ) {
            if(c.getName().equals(cardName)) {
                return true;
            }
        }

        return false;
    }


    public void setPickedCards(List<String> pickedCardsName) {
        Card chosenCars;

        for(String name : pickedCardsName) {
            chosenCars = getAllCardsForPlayers().stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
            pickedCards.add(chosenCars);
        }

    }


    // ======================================================================================


    // ONLY USED FOR TESTING
    public List<Card> getAllCards() {
        return allCards;
    }


    public List<Card> getAllCardsForPlayers() {

        if( numberOfPlayers == 2 ) {
            return allCards;
        }
        else if( numberOfPlayers == 3 ) {
            List<Card> cardsList = new ArrayList<>();

            for (Card c : allCards) {
                if(c.isAvailable3P()) {
                    cardsList.add(c);
                }
            }

            return cardsList;
        }

        return null;
    }


    public Boolean areValidCards( List<String> cardsName ) {

        List<String> availableCards = getAllCardsForPlayers().stream().map(Card::getName).collect(Collectors.toList());

        return availableCards.containsAll(cardsName);
    }



}
