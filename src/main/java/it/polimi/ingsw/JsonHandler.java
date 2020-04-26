package it.polimi.ingsw;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Player.Card;
import it.polimi.ingsw.model.Player.Effect.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonHandler {


    public static String buildingPath = "/json_building.json";


    public static String cardPath = "/json_card.json";


    public static JsonParser myJsonParser = new JsonParser();



    // ======================================================================================


    public static List<Building> deserializeBuildingList() {
        List<Building> buildings = new ArrayList<>();

        JsonArray buildingsJson = myJsonParser.parse(new BufferedReader(new InputStreamReader(JsonDeserializer.class.getResourceAsStream(buildingPath)))).getAsJsonArray();

        for(int i = 0; i < buildingsJson.size(); i++){
            Building build = deserializeBuilding(buildingsJson.get(i).getAsJsonObject());
            buildings.add(build);
        }

        return buildings;
    }


    private static Building deserializeBuilding(JsonObject buildingJson) {

        int level = buildingJson.get("level").getAsInt();
        int quantity = buildingJson.get("quantity").getAsInt();

        return new Building(level, quantity);
    }


    public static List<Card> deserializeCardList() {
        List<Card> cards = new ArrayList<>();

        JsonArray cardsJson = myJsonParser.parse(new BufferedReader(new InputStreamReader(JsonDeserializer.class.getResourceAsStream(cardPath)))).getAsJsonArray();

        for(int i = 0; i < cardsJson.size(); i++){
            Card card = deserializeCard(cardsJson.get(i).getAsJsonObject());
            cards.add(card);
        }

        return cards;
    }


    /**
     * this map each card with the corresponding decorator class
     */
    private static Card deserializeCard(JsonObject cardJson) {


        Map<String, Effect> effectsClassMap;
        effectsClassMap = fillMap();

        String name = cardJson.get("name").getAsString();
        boolean available3P = cardJson.get("available3P").getAsBoolean();
        String effect = cardJson.get("effect").getAsString();
        Effect baseEffect = effectsClassMap.get(name);


        return new Card(name, available3P, effect, baseEffect);
    }


    /**
     * build the map that connects the name of the card to its correct decorator class
     * @return the map of card-playerType
     */
    private static Map<String, Effect> fillMap() {

        Map<String, Effect> playerDecoratorMap = new HashMap<>();

        //Basic Gods
        playerDecoratorMap.put("Apollo", new CanSwitchOpponentEffect( new SwitchEffect( new BasicEffect())));
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



}
