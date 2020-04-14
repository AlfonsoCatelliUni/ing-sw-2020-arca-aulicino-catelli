package it.polimi.ingsw;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Player.Card;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JsonHandler {


    public static String buildingPath = "/json_building.json";


    public static String cardPath = "/json_card.json";


    public static JsonParser myJsonParser = new JsonParser();


    // ======================================================================================


    public static ArrayList<Building> deserializeBuildingList() {
        ArrayList<Building> buildings = new ArrayList<>();

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


    public static ArrayList<Card> deserializeCardList() {
        ArrayList<Card> cards = new ArrayList<>();

        JsonArray cardsJson = myJsonParser.parse(new BufferedReader(new InputStreamReader(JsonDeserializer.class.getResourceAsStream(cardPath)))).getAsJsonArray();

        for(int i = 0; i < cardsJson.size(); i++){
            Card card = deserializeCard(cardsJson.get(i).getAsJsonObject());
            cards.add(card);
        }

        return cards;
    }


    private static Card deserializeCard(JsonObject buildingJson) {

        String name = buildingJson.get("name").getAsString();
        boolean available2P = buildingJson.get("available2P").getAsBoolean();
        boolean available3P = buildingJson.get("available3P").getAsBoolean();
        String effect = buildingJson.get("effect").getAsString();

        return new Card(name, available3P, effect);
    }


}
