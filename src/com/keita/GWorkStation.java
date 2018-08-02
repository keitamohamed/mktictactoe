package com.keita;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GWorkStation {
    private Scanner sc = new Scanner(System.in);

    private List<Player> players;
    Player player;
    private String dashBoard[][] = new String[3][3];
    private String playerTurn = "X";
    private boolean found;
    private int gameWon;

    public GWorkStation() {
        this.players = new ArrayList<>();
        player = new Player();
    }

    // ================= All public methods ====================
    public boolean startGame(boolean runGame) {
        if (dashBoard[0][0] == null) {
            addPlayer(players);
            addValueInDashBoard(dashBoard);
            drawDashBoard();
            makeAMove(dashBoard, enterMove(players, playerTurn), playerTurn);
        }

        while (!winnerFound(dashBoard, players, playerTurn)) {
            drawDashBoard();
            makeAMove(dashBoard, enterMove(players, playerTurn), playerTurn);
        }

        System.out.println("Would you like to re-match? (yes/no): ");
        String reMatch = sc.nextLine();
        if (reMatch.equalsIgnoreCase("no")) {
            endProgram(players);
            runGame = false;
        }
        else {
            addValueInDashBoard(dashBoard);
            drawDashBoard();
            makeAMove(dashBoard, enterMove(players, playerTurn), playerTurn);
        }

        return runGame;
    }

    // ================= All private methods ==========================
    private void makeAMove(String[][] dashBoard, String move, String playerTurn) {

        while (!checkValidation(dashBoard, move)) {
            drawDashBoard();
            System.out.println("\n\tInvalid move. Move to a space (between E1 to E9): ");
            move = sc.nextLine().toUpperCase();
        }
        upDateDashBoard(dashBoard, move);
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

        System.out.println("\n\t\t" + ("MK: Tic Tac Toe Game\t\n" +
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
        for (int i = 0; i < dashBoard.length; i++)
            for (int j = 0; j < dashBoard[i].length; j++) {
                dashBoard[i][j] = "E" + (row);
                row++;
            }
    }

    private void upDateDashBoard(String[][] dashBoard, String move) {
        for (int i = 0; i < dashBoard.length; i++)
            for (int j = 0; j < dashBoard[i].length; j++)
                if (dashBoard[i][j].equals(move)) {
                    dashBoard[i][j] = this.playerTurn;
                    break;
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

        for (String[] column : dashBoard) {
            found = true;
            for (String row : column) {
                if (!row.equals(playerTurn))
                    found = false;
            }

            if (found) {
                winner(players);
                return true;
            }

            if (verticalLineTest(dashBoard, players, playerTurn))
                return true;
            if (ninetyDegreeAngle(dashBoard, players, playerTurn))
                return true;
        }

        this.playerTurn = (playerTurn.equals("X")) ? "O" : "X";
        return false;
    }

    private String enterMove(List<Player> players, String playerTurn) {
        System.out.println("\n\t" + getPlayerName(players, playerTurn) + " make a move to an empty space: ");
        return sc.nextLine().toUpperCase();
    }

    private String getPlayerName(List<Player> players, String playerTurn) {
        if (playerTurn.equals("X"))
            return players.get(0).getFullName();
        return players.get(1).getFullName();
    }


    private boolean verticalLineTest(String[][] dashBoard, List<Player> players, String playerTurn) {

        for (int column = 0; column < dashBoard.length; column++) {
            boolean equal = true;
            for (int i = 0; i < dashBoard.length; i++) {
                if (!dashBoard[i][column].equals(playerTurn)) {
                    equal = false;
                    break;
                }
            }

            if (equal) {
                winner(players);
                return true;
            }
        }
        return false;
    }

    private boolean ninetyDegreeAngle(String[][] dashBoard, List<Player> players, String playerTurn) {
        int size = 0;
        for (int column = 0; column < dashBoard.length - 1; column++) {
            boolean equal = true;
            for (int i = 0; i < dashBoard.length; i++) {
                if (!dashBoard[i][size].equals(playerTurn)) {
                    equal = false;
                    break;
                }

                if (column > 0) {
                    size--;
                }
                else {
                    size++;
                }
            }

            if (!equal && column >= 1) {
                size = dashBoard.length - 1;
            }else  {
                size = dashBoard.length - 1;
            }

            if (equal) {
                winner(players);
                return true;
            }
        }
        return false;
    }

    private void winner(List<Player> players) {
        if (playerTurn.equals("X")) {
            drawDashBoard();
            gameWon = players.get(0).getGameWon() + 1;
            players.get(0).setGameWon(gameWon);
            System.out.println("\nGame over. " + players.get(0).getFullName() + " won");
            player.drawRecord(players.get(0), players.get(players.size() - 1));
        } else {
            drawDashBoard();
            gameWon = players.get(players.size() - 1).getGameWon() + 1;
            players.get(players.size() - 1).setGameWon(gameWon);
            System.out.println("\nGame over. " + players.get(1).getFullName() + " won");
            player.drawRecord(players.get(0), players.get(players.size() - 1));
        }
        this.playerTurn = (playerTurn.equals("X")) ? "O" : "X";
        this.found = false;
        this.gameWon = 0;
    }

    // End game: Message for the players
    private void endProgram(List<Player> players) {
        int count = 0;
        for (Player p : players) {
            System.out.print(p.getFullName());
            if (count == 0) {
                System.out.print(" and ");
            }
            count++;
        }
        System.out.println(" your have a wonderful day!");
    }
}
