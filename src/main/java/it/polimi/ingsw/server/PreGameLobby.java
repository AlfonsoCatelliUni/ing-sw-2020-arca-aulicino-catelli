package it.polimi.ingsw.server;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.model.Player.*;

import java.util.*;

public class PreGameLobby {


    /**
     * this is the list of the nicknames of the player in the current Lobby
     */
    List<String> playersNicknames;


    /**
     * this map each card with the corresponding decorator class
     */
    Map<String, Player> effectsClassMap;


    /**
     * the deck of all cards
     */
    List<Card> allCards;


    /**
     * the players have to choose their card from this
     * restricted number of picked cards
     */
    List<Card> pickedCards;


    /**
     * this map the player to his chosen card
     */
    Map<String, Card> playerCardMap;


    private final static int MAXPLAYERS = 3;


    private final static int MINPLAYERS = 2;


    // ======================================================================================


    PreGameLobby(){

        this.playersNicknames = new ArrayList<>();
        this.effectsClassMap = new HashMap<>();
        this.effectsClassMap = fillMap();

        this.allCards = JsonHandler.deserializeCardList();
        pickedCards = fillGodsDeck(allCards);

    }


    // ======================================================================================


    /**
     * build the map that connects the name of the card to its correct decorator class
     * @return the map of card-playerType
     */
    protected Map<String, Player> fillMap(){

        Map<String, Player> effectClassMap = new HashMap<>();

        effectClassMap.put("Apollo", new SwitchPlayer(new BasicPlayer()));
        effectClassMap.put("Artemis", new DoubleMovePlayer(new BasicPlayer()));
        effectClassMap.put("Athena", new BlockOpponentPlayer(new BasicPlayer()));

        effectClassMap.put("Atlas", new DomeBuildPlayer(new BasicPlayer()));
        effectClassMap.put("Demeter", new NotSameBuildAfterPlayer(new BasicPlayer()));
        effectClassMap.put("Hephaestus", new SameBuildAfterPlayer(new BasicPlayer()));

        effectClassMap.put("Minotaur", new PushPlayer(new BasicPlayer()));
        effectClassMap.put("Pan", new DownTwoPlayer(new BasicPlayer()));
        effectClassMap.put("Prometheus", new BuildBeforePlayer(new BasicPlayer()));

        return effectClassMap;
    }


    public void addPlayer(String nickname, String cardName){

        addNameAndCard(nickname,cardName);

        if (playersNicknames.size() == MAXPLAYERS - 1)
            closeWaitingRoom();

    }


    private void addNameAndCard (String nickname, String cardName){

        if(playersNicknames.contains(nickname))
            throw new RuntimeException("This Lobby already contains this player");

        playersNicknames.add(nickname);

        playerCardMap.put( nickname, pickedCards.stream().filter(c -> c.getName().equals(cardName)).findAny().orElse(null) );

    }


    protected List<Card> fillGodsDeck(List<Card> cards) {

        int cardsQuantity = playersNicknames.size();
        Random random = new Random();
        List<Card> godsDeck = new ArrayList<>();


        for (int i = 0; i < cardsQuantity; i++) {

            int num = random.nextInt(cards.size());

            if (cardsQuantity == 3 && cards.get(i).isAvailable3P()) {
                godsDeck.add(this.allCards.get(num));
            }
            else {
                i--;
            }

        }

        return godsDeck;
    }


    // non deve essere messo qui questo metodo
    public void showCards(List<Card> cards){

        for (Card c : cards)
            System.out.println(c.getName() + c.getEffect() + "\n");

    }


    private void closeWaitingRoom() {
    }



}
