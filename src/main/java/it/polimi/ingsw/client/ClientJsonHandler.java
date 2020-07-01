package it.polimi.ingsw.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON Handler to parse the parameters of all events sent from the server to a client
 * this class is tested into the ControllerTest
 */
public class ClientJsonHandler {

    /**
     * the json parser
     */
    public static JsonParser parser = new JsonParser();


    /**
     * generate a list of cells information, the information contain the row and column
     * @param cellsString the json formatted string
     * @return a list of cells information
     */
    public static List<Couple<Integer, Integer>> generateCellsList(String cellsString) {

        List<Couple<Integer, Integer>> cells = new ArrayList<>();
        JsonArray cellsJon = parser.parse(cellsString).getAsJsonArray();

        for(int i = 0; i < cellsJon.size(); i++){
            Couple<Integer, Integer> cell = generateSingleCell(cellsJon.get(i).getAsJsonObject());
            cells.add(cell);
        }

        return cells;
    }

    /**
     * generate a single cell information
     * @param cellJson the json object that will be parsed
     * @return a single cell information
     */
    private static Couple<Integer, Integer> generateSingleCell(JsonObject cellJson) {

        Integer row = cellJson.get("row").getAsInt();
        Integer column = cellJson.get("column").getAsInt();

        return Couple.create(row, column);
    }



    /**
     * generate a list of cells information, the information contain the name and effect
     * @param cardsString the json formatted string
     * @return a list of cards information
     */
    public static List<Couple<String, String>> generateCardsList(String cardsString) {

        List<Couple<String, String>> cards = new ArrayList<>();
        JsonArray cardsJson = parser.parse(cardsString).getAsJsonArray();

        for(int i = 0; i < cardsJson.size(); i++){
            Couple<String, String> card = generateSingleCard(cardsJson.get(i).getAsJsonObject());
            cards.add(card);
        }

        return cards;
    }

    /**
     * generate a single card information
     * @param cardJson the json object that will be parsed
     * @return a single card information
     */
    private static Couple<String, String> generateSingleCard(JsonObject cardJson) {

        String name = cardJson.get("name").getAsString();
        String effect = cardJson.get("effect").getAsString();

        return Couple.create(name, effect);
    }



    /**
     * generate a list of buildings information, the information contain level and if it's a dome or note
     * @param buildingsString the json formatted string
     * @return a list of cards information
     */
    public static List<Couple<Integer, Boolean>> generateBuildingsList(String buildingsString) {

        List<Couple<Integer, Boolean>> buildings = new ArrayList<>();

        JsonArray buildingsJson = parser.parse(buildingsString).getAsJsonArray();

        for(int i = 0; i < buildingsJson.size(); i++){
            Couple<Integer, Boolean> build = generateSingleBuilding(buildingsJson.get(i).getAsJsonObject());
            buildings.add(build);
        }

        return buildings;
    }

    /**
     * generate a single building information
     * @param buildingJson the json object that will be parsed
     * @return a single building information
     */
    private static Couple<Integer, Boolean> generateSingleBuilding(JsonObject buildingJson) {

        Integer level = buildingJson.get("level").getAsInt();
        Boolean isDome = buildingJson.get("is_dome").getAsBoolean();

        return Couple.create(level, isDome);
    }



    /**
     * generate a list of actions information, the information contain the name of the actions
     * @param actionsString the json formatted string
     * @return a list of actions information
     */
    public static List<String> generateActionsList(String actionsString) {

        List<String> actions = new ArrayList<>();
        JsonArray actionsJson = parser.parse(actionsString).getAsJsonArray();

        for(int i = 0; i < actionsJson.size(); i++){
            String action = generateSingleAction(actionsJson.get(i).getAsJsonObject());
            actions.add(action);
        }

        return actions;
    }

    /**
     * generate a single actions information
     * @param actionJson the json object that will be parsed
     * @return a single action information
     */
    private static String generateSingleAction(JsonObject actionJson) {
        return actionJson.get("action_id").getAsString();
    }



    /**
     * generate a list of players information
     * @param playersString the json formatted string
     * @return a list of players information
     */
    public static List<FormattedPlayerInfo> generatePlayersList(String playersString) {

        List<FormattedPlayerInfo> players = new ArrayList<>();
        JsonArray playersJson = parser.parse(playersString).getAsJsonArray();

        for(int i = 0; i < playersJson.size(); i++){
            FormattedPlayerInfo player = generateSinglePlayer(playersJson.get(i).getAsJsonObject());
            players.add(player);
        }

        return players;
    }

    /**
     * generate a single player information
     * @param playerJson the json object that will be parsed
     * @return a single player information
     */
    private static FormattedPlayerInfo generateSinglePlayer(JsonObject playerJson) {

        String nickname = playerJson.get("nickname").getAsString();
        String color = playerJson.get("color").getAsString();
        String card = playerJson.get("card").getAsString();
        String effect = playerJson.get("effect").getAsString();

        return FormattedPlayerInfo.create(nickname, color, Couple.create(card, effect));
    }


}
