package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Player.*;

import java.util.*;

public class PreGameLobby {

    List<String> playersNicknames;

    Map<String, Player> effectsClassMap;

    List<Card> cards;

    Map<String, String> PlayerToCard;

    private final static int MAXPLAYERS = 3;

    private final static int MINPLAYERS = 2;


    PreGameLobby(){
        this.playersNicknames = new ArrayList<>();
        this.effectsClassMap = new HashMap<>();
        this.effectsClassMap = fillMap();
        //this.cards = JsonHandler.deserializeCardList
    }


    void addPlayer(String nickname, String cardName){

        addNameAndCard(nickname,cardName);

        if (playersNicknames.size()>= MAXPLAYERS)
            closeWaitingRoom();
    }


    private void addNameAndCard (String nickname, String cardName){
        if(playersNicknames.contains(nickname))
            throw new RuntimeException("This Lobby already contains this player");

        playersNicknames.add(nickname);

        PlayerToCard.put(nickname, cardName);

    }


    protected Map<String, Player> fillMap(){

        Map<String, Player> effectClassMap = new HashMap<>();

        effectClassMap.put("Apollo", new SwitchPlayer(new BasicPlayer()));
        effectClassMap.put("Artemis", new DoubleMovePlayer(new BasicPlayer()));
        effectClassMap.put("Athena", new BlockOpponentPlayer(new BasicPlayer()));
        effectClassMap.put("Atlas", new DomeBuildPlayer(new BasicPlayer()));
        effectClassMap.put("Demeter", new DoubleBuildPlayer(new BasicPlayer(), 0));
        effectClassMap.put("Hephaestus", new DoubleBuildPlayer(new BasicPlayer(), 1));
        effectClassMap.put("Minotaur", new PushPlayer(new BasicPlayer()));
        effectClassMap.put("Pan", new DownTwoPlayer(new BasicPlayer()));
        effectClassMap.put("Prometheus", new BuildBeforePlayer(new BasicPlayer()));

        return effectClassMap;
    }


    protected List<Card> fillGodsDeck(List<Card> cards){
        int cardsQuantity = playersNicknames.size();
        Random random = new Random();
        List<Card> godsDeck = new ArrayList<>();

        for (int i = 0; i < cardsQuantity; i++) {
            int num = random.nextInt(cards.size()-1);
            if (cardsQuantity == 3 && cards.get(i).isAvailable3P())
                godsDeck.add(this.cards.get(num));
            else i--;
        }
        return godsDeck;
    }


    // non deve essere messo qui questo metodo
    public void showCards(List<Card> cards){
        for (Card c : cards)
            System.out.println(c.getName() + c.getEffect() + "\n");
    }



    private void closeWaitingRoom(){
    }

}
