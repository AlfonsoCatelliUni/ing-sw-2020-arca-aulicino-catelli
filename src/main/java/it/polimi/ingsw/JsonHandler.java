package it.polimi.ingsw;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.polimi.ingsw.client.FormattedCellInfo;
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


    public static JsonParser parser = new JsonParser();


    private static final Map<String, Effect> playerDecoratorMap = fillMap();


    // ======================================================================================


    /**
     * build the map that connects the name of the card to its correct decorator class
     * @return the map of card-playerType
     */
    private static Map<String, Effect> fillMap() {

        Map<String, Effect> effectsMap = new HashMap<>();

        //Basic Gods
        effectsMap.put("CanSwitchOpponentEffect", new CanSwitchOpponentEffect(new BasicEffect()));
        effectsMap.put("SwitchEffect", new SwitchEffect(new BasicEffect()));

        effectsMap.put("MoreMoveEffect", new MoreMoveEffect(new BasicEffect()));

        effectsMap.put("BlockOpponentEffect", new BlockOpponentEffect(new BasicEffect()));

        effectsMap.put("DomeBuildEffect", new DomeBuildEffect(new BasicEffect()));

        effectsMap.put("MoreBuildOnSameEffect", new MoreBuildOnSameEffect(new BasicEffect()));

        effectsMap.put("MoreBuildNotOnSameEffect", new MoreBuildNotOnSameEffect(new BasicEffect()));

        effectsMap.put("CanPushEffect", new CanPushOpponentEffect(new BasicEffect()));
        effectsMap.put("PushEffect", new PushEffect(new BasicEffect()));

        effectsMap.put("DownTwoEffect", new DownTwoEffect(new BasicEffect()));

        effectsMap.put("BuildBeforeEffect", new BuildBeforeEffect(new BasicEffect()));


        //Advanced Gods
        effectsMap.put("CanDestroyEffect",new CanDestroyEffect(new BasicEffect()));
        effectsMap.put("DestroyEffect", new DestroyEffect(new BasicEffect()));

        effectsMap.put("CanForceEffect", new CanForceEffect(new BasicEffect()));

        effectsMap.put("MoreBuildInsideEffect", new MoreBuildInsideEffect(new BasicEffect()));

        effectsMap.put("MovePerimeterAgainEffect", new MovePerimeterAgainEffect(new BasicEffect()));

        effectsMap.put("CanBuildUnderItselfEffect", new CanBuildUnderItselfEffect(new BasicEffect()));

        effectsMap.put("BuildUnderItselfEffect", new BuildUnderItselfEffect(new BasicEffect()));

        return effectsMap;
    }


    //MARK : generate All Buildings from the JSON file in Resources ======================================================================================


    /**
     * generate a list of buildings list from the json file
     * @return the list of buildings
     */
    public static List<Building> deserializeBuildingList() {
        List<Building> buildings = new ArrayList<>();

        JsonArray buildingsJson = parser.parse(new BufferedReader(new InputStreamReader(JsonDeserializer.class.getResourceAsStream(buildingPath)))).getAsJsonArray();

        for(int i = 0; i < buildingsJson.size(); i++){
            Building build = deserializeBuilding(buildingsJson.get(i).getAsJsonObject());
            buildings.add(build);
        }

        return buildings;
    }


    /**
     * this map each building with the corresponding decorator class
     * @param buildingJson a json object with the building information
     * @return a building created from the passed object
     */
    private static Building deserializeBuilding(JsonObject buildingJson) {

        int level = buildingJson.get("level").getAsInt();
        int quantity = buildingJson.get("quantity").getAsInt();

        return new Building(level, quantity);
    }


    //MARK : generate All Cards from the JSON file in Resources ======================================================================================

    /**
     * generate a list of cards list from the json file
     * @return the list of cards
     */
    public static List<Card> deserializeCardList() {
        List<Card> cards = new ArrayList<>();

        JsonArray cardsJson = parser.parse(new BufferedReader(new InputStreamReader(JsonDeserializer.class.getResourceAsStream(cardPath)))).getAsJsonArray();

        for(int i = 0; i < cardsJson.size(); i++){
            Card card = deserializeCard(cardsJson.get(i).getAsJsonObject());
            cards.add(card);
        }

        return cards;
    }

    /**
     * this map each card with the corresponding decorator class
     * @param cardJson the json with the information of the card
     * @return a card generated from the json
     */
    private static Card deserializeCard(JsonObject cardJson) {

        String name = cardJson.get("name").getAsString();
        boolean available3P = cardJson.get("available3P").getAsBoolean();
        String effectDescription = cardJson.get("effect").getAsString();
        JsonArray listEffects = cardJson.get("listEffects").getAsJsonArray();

        Effect baseEffect = new BasicEffect();

        for (int i = 0; i < listEffects.size(); i++){
            String effect = listEffects.get(i).getAsString();
            baseEffect = playerDecoratorMap.get(effect).addEffect(baseEffect);
        }

        return new Card(name, available3P, effectDescription, baseEffect);
    }


    //MARK : Section to Generate All the Information from the Update ======================================================================================


    /**
     * generate a list of FormattedCellInfo from a json string
     * @param update the json string that will be parsed
     * @return the list of cells info
     */
    public static List<FormattedCellInfo> generateCellsList(String update) {
        List<FormattedCellInfo> cellsInfo = new ArrayList<>();

        JsonArray cellsJson = parser.parse(update).getAsJsonObject().get("gameBoard").getAsJsonArray();
        for(int i = 0; i < cellsJson.size(); i++){
            FormattedCellInfo cell = generateSingleCellInfo(cellsJson.get(i).getAsJsonObject());
            cellsInfo.add(cell);
        }

        return cellsInfo;
    }

    /**
     * generate a single cell information
     * @param cellJson the json object that will be parsed
     * @return a single cell information
     */
    private static FormattedCellInfo generateSingleCellInfo(JsonObject cellJson) {

        Integer row = cellJson.get("row").getAsInt();
        Integer column = cellJson.get("column").getAsInt();
        Integer height = cellJson.get("height").getAsInt();

        JsonObject pawn = cellJson.get("pawn").getAsJsonObject();
        String pawnColor = pawn.get("color").getAsString();
        String pawnSex = pawn.get("sex").getAsString();

        JsonObject roof = cellJson.get("roof").getAsJsonObject();
        Integer roofLevel = roof.get("level").getAsInt();
        Boolean roofIsDome = roof.get("isDome").getAsBoolean();

        return FormattedCellInfo.create(row, column, height, pawnColor, pawnSex, roofLevel, roofIsDome);
    }



}
