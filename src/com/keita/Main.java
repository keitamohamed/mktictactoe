package com.keita;

public class Main {

    public static void main(String[] args) {

        boolean runGame = true;
        GWorkStation wStation = new GWorkStation();

        while (runGame) {
            runGame = wStation.startGame(runGame);
        }

        System.out.println("");

    }
}
