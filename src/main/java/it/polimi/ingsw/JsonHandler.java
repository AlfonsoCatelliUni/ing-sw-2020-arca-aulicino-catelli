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


    private static Map<String, Effect> playerDecoratorMap = fillMap();


    // ======================================================================================


    /**
     * build the map that connects the name of the card to its correct decorator class
     * @return the map of card-playerType
     */
    private static Map<String, Effect> fillMap() {

        Map<String, Effect> newMap = new HashMap<>();

        //Basic Gods
        newMap.put("Apollo", new CanSwitchOpponentEffect( new SwitchEffect( new BasicEffect())));
        newMap.put("Artemis", new MoreMoveEffect(new BasicEffect()));
        newMap.put("Athena", new BlockOpponentEffect(new BasicEffect()));

        newMap.put("Atlas", new DomeBuildEffect(new BasicEffect()));
        newMap.put("Demeter", new MoreBuildOnSameEffect(new BasicEffect()));
        newMap.put("Hephaestus", new MoreBuildNotOnSameEffect(new BasicEffect()));

        newMap.put("Minotaur", new CanPushOpponentEffect(new PushEffect(new BasicEffect())));
        newMap.put("Pan", new DownTwoEffect(new BasicEffect()));
        newMap.put("Prometheus", new BuildBeforeEffect(new BasicEffect()));


        //Advanced Gods
        newMap.put("Ares",new CanDestroyEffect(new DestroyEffect(new BasicEffect())) );
        newMap.put("Charon", new CanForceEffect(new BasicEffect()));
        newMap.put("Hestia", new MoreBuildInsideEffect(new BasicEffect()));
        newMap.put("Triton", new MovePerimeterAgainEffect(new BasicEffect()));
        newMap.put("Zeus", new CanBuildUnderItselfEffect(new BasicEffect()));


        return newMap;
    }


    //MARK : generate All Buildings from the JSON file in Resources ======================================================================================


    public static List<Building> deserializeBuildingList() {
        List<Building> buildings = new ArrayList<>();

        JsonArray buildingsJson = parser.parse(new BufferedReader(new InputStreamReader(JsonDeserializer.class.getResourceAsStream(buildingPath)))).getAsJsonArray();

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


    //MARK : generate All Cards from the JSON file in Resources ======================================================================================


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
     */
    private static Card deserializeCard(JsonObject cardJson) {

        String name = cardJson.get("name").getAsString();
        boolean available3P = cardJson.get("available3P").getAsBoolean();
        String effect = cardJson.get("effect").getAsString();
        Effect baseEffect = playerDecoratorMap.get(name);


        return new Card(name, available3P, effect, baseEffect);
    }


    //MARK : Section to Generate All the Information from the Update ======================================================================================


    public static List<FormattedCellInfo> generateCellsList(String update) {
        List<FormattedCellInfo> cellsInfo = new ArrayList<>();

        JsonArray cellsJson = parser.parse(update).getAsJsonObject().get("gameBoard").getAsJsonArray();
        for(int i = 0; i < cellsJson.size(); i++){
            FormattedCellInfo cell = generateSingleCellInfo(cellsJson.get(i).getAsJsonObject());
            cellsInfo.add(cell);
        }

        return cellsInfo;
    }

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
