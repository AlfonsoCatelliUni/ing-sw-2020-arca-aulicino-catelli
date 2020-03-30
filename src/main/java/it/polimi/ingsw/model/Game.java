package it.polimi.ingsw.model;

import it.polimi.ingsw.model.BoardPack.Board;
import it.polimi.ingsw.model.BoardPack.Building;
import it.polimi.ingsw.model.Player.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {


    private Board gameBoard;


    private List<Player> players;


    //private ArrayList<Object> objM;


    private Player currentPlayer;


    private List<Building> buildings;


    private List<Card> cards;


    private int indexCurrentPlayer;


    // ======================================================================================


    public Game(List<String> playersNickname, List<Color> colors, List<String> godsName, List<Card> godsCards, Map<String, Player> effectClassMap) {

        this.gameBoard = new Board();
        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.indexCurrentPlayer = 0;


        for (int i = 0; i < playersNickname.size(); i++) {

            players.add(effectClassMap.get(playersNickname.get(i)));

            players.get(i).setName(playersNickname.get(i));
            players.get(i).setColor(colors.get(i));
            players.get(i).setNameGod(godsName.get(i));

            players.get(i).initPawn(gameBoard, Sex.MALE, gameBoard.getCell(i, i));
            players.get(i).initPawn(gameBoard, Sex.FEMALE, gameBoard.getCell(i, i+1));

        }

        currentPlayer = players.get(indexCurrentPlayer);


    }


    //TODO : finish the standard Game constructor
    public Game(){
        this.gameBoard = new Board();

    }


    // ======================================================================================








}
