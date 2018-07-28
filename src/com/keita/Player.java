package com.keita;

public class Player {

    private String fullName;
    private int gameWon;

    public Player(String fullName) {
        this.fullName = fullName;
        this.gameWon = 0;
    }

    @Override
    public String toString() {
        return "Name: " + fullName + " and gameWon: " + gameWon;
    }

    public void setGameWon(int gameWon) {
        this.gameWon = gameWon;
    }

    public String getFullName() {
        return fullName;
    }
}
