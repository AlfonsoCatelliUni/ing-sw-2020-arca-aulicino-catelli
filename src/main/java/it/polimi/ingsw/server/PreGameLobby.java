package it.polimi.ingsw.server;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Player.*;
import it.polimi.ingsw.model.Player.Effect.*;

import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

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
     * this map each card with the corresponding decorator class
     */
    private Map<String, Effect> effectsClassMap;


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


    private final static int MAXPLAYERS = 3;


    private final static int MINPLAYERS = 2;


    // ======================================================================================


    public PreGameLobby() {

        //players init
        this.numberOfPlayers = -1;
        this.closed = false;

        this.playersNicknames = new ArrayList<>();

        //effect init
        this.effectsClassMap = new HashMap<>();
        this.effectsClassMap = fillMap();

        //cards init
        this.pickedCards = new ArrayList<>();
        this.playerCardMap = new HashMap<>();

        this.allCards = JsonHandler.deserializeCardList();
    }


    // ======================================================================================


    public Boolean getClosed() {
        return this.closed;
    }


    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }


    public List<String> getConnectedPlayers() {
        return this.playersNicknames;
    }


    public Map<String, Effect> getEffectsClassMap() {
        return effectsClassMap;
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
     * build the map that connects the name of the card to its correct decorator class
     * @return the map of card-playerType
     */
    private Map<String, Effect> fillMap() {

        Map<String, Effect> playerDecoratorMap = new HashMap<>();

        //Basic Gods
        playerDecoratorMap.put("Apollo", new CanSwitchOpponentEffect( new SwitchEffect ( new BasicEffect())));
        playerDecoratorMap.put("Artemis", new MoreMoveEffect(new BasicEffect()));
        playerDecoratorMap.put("Athena", new BlockOpponentEffect(new BasicEffect()));

        playerDecoratorMap.put("Atlas", new DomeBuildEffect(new BasicEffect()));
        playerDecoratorMap.put("Demeter", new MoreBuildOnSameEffect(new BasicEffect()));
        playerDecoratorMap.put("Hephaestus", new MoreBuildNotOnSameEffect(new BasicEffect()));

        playerDecoratorMap.put("Minotaur", new CanPushOpponentEffect(new PushEffect(new BasicEffect())));
        playerDecoratorMap.put("Pan", new DownTwoEffect(new BasicEffect()));
        playerDecoratorMap.put("Prometheus", new BuildBeforeEffect(new BasicEffect()));


        //Advanced Gods
        playerDecoratorMap.put("Ares",new CanDestroyEffect(new DestroyEffect(new BasicEffect())) );
        playerDecoratorMap.put("Charon", new CanForceEffect(new BasicEffect()));
        playerDecoratorMap.put("Hestia", new MoreBuildInsideEffect(new BasicEffect()));
        playerDecoratorMap.put("Triton", new MovePerimeterAgainEffect(new BasicEffect()));
        playerDecoratorMap.put("Zeus", new CanBuildUnderItselfEffect(new BasicEffect()));


        return playerDecoratorMap;
    }


    /**
     * synchronized because there could be conflicts with the timer
     */
    private synchronized void closeWaitingRoom() {

        if(!closed) {
            this.closed = true;
            pickCards(allCards);

        }

    }


    public void addPlayer(String nickname) {

        //fare controllo prima di chiamare addPlayer
        if(!isNicknameAvailable(nickname)) {
            throw new RuntimeException("This Lobby already contains this player");
        }

        playersNicknames.add(nickname);


        if (playersNicknames.size() == numberOfPlayers)
            closeWaitingRoom();

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


    public void deletePlayerInformation(String nickname) {

        try {
            playersNicknames.removeIf(n -> n.equals(nickname));
            playerCardMap.remove(nickname);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }


    // ======================================================================================


    public Boolean isNicknameAvailable(String nickname) {
        return !playersNicknames.contains(nickname);
    }


    public Boolean isNicknameValid(String nickname) {
        return Pattern.matches(regex, nickname);
    }


    public Boolean isCardAvailable(String cardName) {

        for ( Card c : pickedCards ) {
            if(c.getName().equals(cardName)) {
                return true;
            }
        }

        return false;
    }


    // ======================================================================================


    // ONLY USED FOR TESTING
    public List<Card> getAllCards() {
        return allCards;
    }


}
