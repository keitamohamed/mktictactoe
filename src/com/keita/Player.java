package com.keita;

public class Player {

    private String fullName;
    private int gameWon;

    public Player(){};

    public Player(String fullName) {
        this.fullName = fullName;
        this.gameWon = 0;
    }

    @Override
    public String toString() {
        return "Name: " + fullName + " and gameWon: " + gameWon;
    }

    public void drawRecord(Player player1, int gameDraw, Player player2) {
        System.out.println(player1.getFullName() + "\t|| Game Won: " + player1.gameWon  +
                "\t|| Game Draw: " + gameDraw + "\t|| Game Won: " + player2.gameWon +
        " \t|| " + player2.getFullName() + "\n");
    }

    public void setGameWon(int gameWon) {
        this.gameWon = gameWon;
    }

    public int getGameWon() {
        return gameWon;
    }

    public String getFullName() {
        return fullName;
    }

}
