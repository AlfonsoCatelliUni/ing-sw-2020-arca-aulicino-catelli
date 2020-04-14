package it.polimi.ingsw.server;

import it.polimi.ingsw.JsonHandler;
import it.polimi.ingsw.model.Player.*;

import java.awt.*;
import java.util.*;
import java.util.List;

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


    /**
     * this map the player to the points where its pawns are going to be placed once the game is started
     */
    Map<String, List<Point>> playerPawnPoints;


    private final static int MAXPLAYERS = 3;


    private final static int MINPLAYERS = 2;


    // ======================================================================================


    PreGameLobby() {

        this.playersNicknames = new ArrayList<>();
        this.effectsClassMap = new HashMap<>();
        this.effectsClassMap = fillMap();
        this.playerPawnPoints = new HashMap<>();

        this.allCards = JsonHandler.deserializeCardList();
        pickedCards = fillGodsDeck(allCards);

    }


    // ======================================================================================


    /**
     * build the map that connects the name of the card to its correct decorator class
     * @return the map of card-playerType
     */
    protected Map<String, Player> fillMap() {

        Map<String, Player> playerDecoratorMap = new HashMap<>();

        playerDecoratorMap.put("Apollo", new SwitchPlayer(new BasicPlayer()));
        playerDecoratorMap.put("Artemis", new DoubleMovePlayer(new BasicPlayer()));
        playerDecoratorMap.put("Athena", new BlockOpponentPlayer(new BasicPlayer()));

        playerDecoratorMap.put("Atlas", new DomeBuildPlayer(new BasicPlayer()));
        playerDecoratorMap.put("Demeter", new NotSameBuildAfterPlayer(new BasicPlayer()));
        playerDecoratorMap.put("Hephaestus", new SameBuildAfterPlayer(new BasicPlayer()));

        playerDecoratorMap.put("Minotaur", new PushPlayer(new BasicPlayer()));
        playerDecoratorMap.put("Pan", new DownTwoPlayer(new BasicPlayer()));
        playerDecoratorMap.put("Prometheus", new BuildBeforePlayer(new BasicPlayer()));


        return playerDecoratorMap;
    }


    public void addPlayer(String nickname) throws RuntimeException {

        //TODO : non vogliamo che venga lanciata questa eccezione
        //fare controllo prima di chiamare addPlayer
        if(playersNicknames.contains(nickname))
            throw new RuntimeException("This Lobby already contains this player");

        playersNicknames.add(nickname);

        List<Point> defaultPoints = new ArrayList<>();
        defaultPoints.add(new Point(-1,-1));
        defaultPoints.add(new Point(-1,-1));
        playerPawnPoints.put(nickname, defaultPoints);

        if (playersNicknames.size() == MAXPLAYERS - 1)
            closeWaitingRoom();

    }


    private void addNameAndCard (String nickname, String cardName) {

        playerCardMap.put( nickname, pickedCards.stream().filter(c -> c.getName().equals(cardName)).findAny().orElse(null) );

    }


    private void addNewPawnCoordinates(String nickname, int row, int column) {

        //TODO : migliorare gestione casi limite, metodo fatto velocemente
        Point pawnPoint = new Point(row, column);
        Point defaultPoint = new Point(-1,-1);
        List<String> keys = new ArrayList<>(playerPawnPoints.keySet());

        for ( String k : keys ) {
            List<Point> pointList = playerPawnPoints.get(k);

            for (Point p : pointList ) {
                if(pawnPoint.equals(p)) {
                    //Brutta cosa, manda messaggio di non validità
                    //Attendi altro punto

                }
                else if(defaultPoint.equals(p)) {
                    p = pawnPoint;
                }
            }

        }

        //questo non servirebbe più
        List<Point> points = new ArrayList<>();
        points = playerPawnPoints.get(nickname);
        points.add(pawnPoint);
        playerPawnPoints.put(nickname, points);

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


    private void closeWaitingRoom() {


    }



}
