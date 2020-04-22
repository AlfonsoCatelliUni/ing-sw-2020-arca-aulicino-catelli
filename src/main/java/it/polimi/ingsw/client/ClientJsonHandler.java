package it.polimi.ingsw.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.model.Board.Building;
import it.polimi.ingsw.model.Player.Card;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON Handler to parse the parameters of all events sent from the server to a client
 * this class is tested into the ControllerTest
 */
public class ClientJsonHandler {

    public static JsonParser parser = new JsonParser();


    public static List<Couple<Integer, Integer>> generateCellsList(String cellsString) {

        List<Couple<Integer, Integer>> cells = new ArrayList<>();
        JsonArray cellsJon = parser.parse(cellsString).getAsJsonArray();

        for(int i = 0; i < cellsJon.size(); i++){
            Couple<Integer, Integer> cell = generateSingleCell(cellsJon.get(i).getAsJsonObject());
            cells.add(cell);
        }

        return cells;
    }

    private static Couple<Integer, Integer> generateSingleCell(JsonObject cellJson) {

        Integer row = cellJson.get("row").getAsInt();
        Integer column = cellJson.get("column").getAsInt();

        return Couple.create(row, column);
    }



    public static List<Couple<String, String>> generateCardsList(String cardsString) {

        List<Couple<String, String>> cards = new ArrayList<>();
        JsonArray cardsJson = parser.parse(cardsString).getAsJsonArray();

        for(int i = 0; i < cardsJson.size(); i++){
            Couple<String, String> card = generateSingleCard(cardsJson.get(i).getAsJsonObject());
            cards.add(card);
        }

        return cards;
    }

    private static Couple<String, String> generateSingleCard(JsonObject cardJson) {

        String name = cardJson.get("name").getAsString();
        String effect = cardJson.get("effect").getAsString();

        return Couple.create(name, effect);
    }



    public static List<Couple<Integer, Boolean>> generateBuildingsList(String buildingsString) {

        List<Couple<Integer, Boolean>> buildings = new ArrayList<>();

        JsonArray buildingsJson = parser.parse(buildingsString).getAsJsonArray();

        for(int i = 0; i < buildingsJson.size(); i++){
            Couple<Integer, Boolean> build = generateSingleBuilding(buildingsJson.get(i).getAsJsonObject());
            buildings.add(build);
        }

        return buildings;
    }

    private static Couple<Integer, Boolean> generateSingleBuilding(JsonObject buildingJson) {

        Integer level = buildingJson.get("level").getAsInt();
        Boolean isDome = buildingJson.get("is_dome").getAsBoolean();

        return Couple.create(level, isDome);
    }



    public static List<String> generateActionsList(String actionsString) {

        List<String> actions = new ArrayList<>();
        JsonArray actionsJson = parser.parse(actionsString).getAsJsonArray();

        for(int i = 0; i < actionsJson.size(); i++){
            String action = generateSingleAction(actionsJson.get(i).getAsJsonObject());
            actions.add(action);
        }

        return actions;
    }

    private static String generateSingleAction(JsonObject actionJson) {
        return actionJson.get("action_id").getAsString();
    }



    public static List<FormattedPlayerInfo> generatePlayersList(String playersString) {

        List<FormattedPlayerInfo> players = new ArrayList<>();
        JsonArray playersJson = parser.parse(playersString).getAsJsonArray();

        for(int i = 0; i < playersJson.size(); i++){
            FormattedPlayerInfo player = generateSinglePlayer(playersJson.get(i).getAsJsonObject());
            players.add(player);
        }

        return players;
    }

    private static FormattedPlayerInfo generateSinglePlayer(JsonObject playerJson) {

        String nickname = playerJson.get("nickname").getAsString();
        String color = playerJson.get("color").getAsString();
        String card = playerJson.get("card").getAsString();
        String effect = playerJson.get("effect").getAsString();

        return FormattedPlayerInfo.create(nickname, color, Couple.create(card, effect));
    }


}
