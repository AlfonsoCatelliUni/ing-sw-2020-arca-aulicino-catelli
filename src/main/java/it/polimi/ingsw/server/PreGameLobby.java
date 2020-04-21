package it.polimi.ingsw.server;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.model.Player.*;
import it.polimi.ingsw.model.Player.Effect.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PreGameLobby {


    private int numberOfPlayers;


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


    /**
     * this map the player to the points where its pawns are going to be placed once the game is started
     */
    private Map<String, List<Point>> playerPawnPoints;


    private final static int MAXPLAYERS = 3;


    private final static int MINPLAYERS = 2;


    // ======================================================================================


    public PreGameLobby() {

        this.playersNicknames = new ArrayList<>();
        this.effectsClassMap = new HashMap<>();
        this.effectsClassMap = fillMap();
        this.playerPawnPoints = new HashMap<>();
        this.pickedCards = new ArrayList<>();
        this.playerCardMap = new HashMap<>();

        this.allCards = JsonHandler.deserializeCardList();
    }


    // ======================================================================================


    public List<String> getConnectedPlayers() {
        return this.playersNicknames;
    }


    /**
     * build the map that connects the name of the card to its correct decorator class
     * @return the map of card-playerType
     */
    protected Map<String, Effect> fillMap() {

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


    public void addPlayer(String nickname) {

        //fare controllo prima di chiamare addPlayer
        if(!isNicknameAvailable(nickname)) {
            throw new RuntimeException("This Lobby already contains this player");
        }

        playersNicknames.add(nickname);

        if (playersNicknames.size() == MAXPLAYERS)
            closeWaitingRoom();

    }


    public Boolean isNicknameAvailable(String nickname) {
        return !playersNicknames.contains(nickname);
    }


    public void addCard (String nickname, String cardName) {

        Card chosenCard = pickedCards.stream().filter(c -> c.getName().equals(cardName)).findAny().orElse(null);

        if(chosenCard == null) {
            throw new RuntimeException("Invalid Chosen Card!");
        }
        if(playerCardMap.containsKey(nickname)) {
            throw new RuntimeException("Invalid Nickname!");
        }

        playerCardMap.put( nickname, chosenCard );
        pickedCards.remove(chosenCard);

    }


    public Boolean isCardAvailable(String cardName) {

        for ( Card c : pickedCards ) {
            if(c.getName().equals(cardName)) {
                return true;
            }
        }

        return false;
    }


    public List<Card> getPickedCards() {
        return this.pickedCards;
    }


    public void addNewPawnCoordinates(String nickname, int row, int column) {

        Point pawnPoint = new Point(row, column);

        List<Point> points =  playerPawnPoints.get(nickname);

        if(isSpotFree(pawnPoint) && points.size() < 2) {
            points.add(pawnPoint);
            playerPawnPoints.put(nickname, points);
        }
        else if(!isSpotFree(pawnPoint)) {
            throw new RuntimeException("A sleeping Snorlax is blocking the spot. You've to find the Poke Flute to wake him up!");
        }

    }


    public Boolean isSpotFree(int row, int column) {
        Point spot = new Point(row, column);
        return isSpotFree(spot);
    }


    private Boolean isSpotFree(Point spot){
        List<String> keys = new ArrayList<>(playerPawnPoints.keySet());

        for ( String k : keys ) {

            List<Point> pointList = playerPawnPoints.get(k);
            for (Point p : pointList ) {

                if(spot.equals(p)) {
                    return false;
                }

            }

        }

        return true;
    }


    protected void fillGodsDeck(List<Card> cards) {

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


    private void closeWaitingRoom() {
        fillGodsDeck(allCards);
    }


    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


}
