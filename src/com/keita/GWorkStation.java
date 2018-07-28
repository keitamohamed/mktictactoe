package com.keita;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GWorkStation {
    private Scanner sc = new Scanner(System.in);

    private List<Player> players;
    private String dashBoard[][] = new String[3][3];
    private String playerTurn = "X";

    public GWorkStation() {
        this.players = new ArrayList<>();
    }

    // ================= All public methods ====================
    public void startGame() {
        if (dashBoard[0][0] == null) {
            addPlayer(players);
            addValueInDashBoard(dashBoard);
            drawDashBoard();
            makeAMove(dashBoard, enterMove(players, playerTurn));
        }
        while (!fullSize(dashBoard) && !winnerFound(dashBoard, players, playerTurn)) {
            drawDashBoard();
            makeAMove(dashBoard, enterMove(players, playerTurn));
            if (fullSize(dashBoard))
                drawDashBoard();
        }
    }

// ================= All private methods ==========================
    private void makeAMove(String[][] dashBoard, String move) {

        while (!checkValidation(dashBoard, move)) {
            drawDashBoard();
            System.out.println("\n\tInvalid move. Move to a space (between E1 to E9): ");
            move = sc.nextLine().toUpperCase();
        }
        upDateDashBoard(dashBoard, move, playerTurn);
    }

    private boolean checkValidation(String[][] dashBoard, String move) {

        while (move.toUpperCase().equals("X") || move.toUpperCase().equals("O")) {
            System.out.println("\tSpace not empty. Make a move to an empty space: ");
            move = sc.nextLine().toUpperCase();
        }

        for (String[] column : dashBoard)
            for (String row : column)
                if (row.equals(move))
                    return true;
        return false;
    }

    private void drawDashBoard() {

        System.out.println("\n\t\t" + ("Keita Tic Tac Toe Game\t\n" +
                "\t================================").toUpperCase());
        for (String[] aDashBoard : dashBoard) {
            for (String row : aDashBoard) {
                System.out.print("\t" + row + "    ||  ");
            }
            System.out.println("\n\t================================");
        }
    }

    private void addValueInDashBoard(String[][] dashBoard) {
        int row = 1;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                dashBoard[i][j] = "E" + (row);
                row++;
            }
    }

    private void upDateDashBoard(String[][] dashBoard, String move, String playerTurn) {

        for (int i = 0; i < dashBoard.length; i++)
            for (int j = 0; j< dashBoard[i].length; j++)
                if (dashBoard[i][j].equals(move)) {
                    dashBoard[i][j] = this.playerTurn;
                    this.playerTurn = (playerTurn.equals("X")) ? "O" : "X";
                    return;
                }
    }

    private void addPlayer(List<Player> players) {
        int counter = 1;
        while (counter <= 2) {
            System.out.println("Enter name for player #" + counter + ": ");
            String name = sc.nextLine();
            players.add(new Player(name));
            counter++;
        }
    }

    private boolean winnerFound(String[][] dashBoard, List<Player> players, String playerTurn) {
        boolean winnerFound = false;
        for (int i = 0; i < dashBoard.length; i++) {
            for (int j = 0; j < dashBoard[i].length; j++) {
                if (dashBoard[i][j].equals(playerTurn))
                    winnerFound = true;
                else {
                    winnerFound = false;
                    break;
                }
            }
        }

        if (winnerFound)
            if (playerTurn.equals("X"))
                System.out.println(players.get(0).getFullName() + " is winner");
            else
                System.out.println(players.get(1).getFullName() + " is winner");
        return winnerFound;
    }

    private String enterMove(List<Player> players, String playerTurn) {
        System.out.println("\n\t" + getPlayerName(players, playerTurn)+ " make a move to an empty space: ");
        return sc.nextLine().toUpperCase();
    }

    private String getPlayerName(List<Player> players, String playerTurn) {
        if (playerTurn.equals("X"))
            return players.get(0).getFullName();
        return players.get(1).getFullName();
    }

    private boolean fullSize(String[][] dashBoard) {
        for (String[] column : dashBoard) {
            for (String row : column) {
                if (!row.equals("X") && !row.equals("O"))
                    return false;
            }
        }
        return true;
    }
}
