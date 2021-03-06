package it.polimi.ingsw.client;


public class FormattedPlayerInfo {

    private String nickname;

    private String color;

    private Couple<String, String> card;


    // ======================================================================================


    public FormattedPlayerInfo(String nickname, String color, Couple<String, String> card) {
        this.nickname = nickname;
        this.color = color;
        this.card = card;
    }


    // ======================================================================================


    public String getNickname() {
        return nickname;
    }


    public String getColor() {
        return color;
    }


    public Couple<String, String> getCard() {
        return card;
    }


    // ======================================================================================


    public static FormattedPlayerInfo create(String nickname, String color, Couple<String, String> card) {
        return new FormattedPlayerInfo(nickname, color, card);
    }

}
