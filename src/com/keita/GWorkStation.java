package com.keita;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GWorkStation {
    private Scanner sc = new Scanner(System.in);

    private List<Player> players;
    private String dashBoard[][] = new String[3][3];
    private String playerTurn = "X";
    private boolean found;

    public GWorkStation() {
        this.players = new ArrayList<>();
    }

    // ================= All public methods ====================
    public void startGame() {
        if (dashBoard[0][0] == null) {
            addPlayer(players);
            addValueInDashBoard(dashBoard);
            drawDashBoard();
            makeAMove(dashBoard, enterMove(players, playerTurn), playerTurn);
        }

        while (!winnerFound(dashBoard, players, playerTurn)) {
            drawDashBoard();
            makeAMove(dashBoard, enterMove(players, playerTurn), playerTurn);

//            if (fullSize(dashBoard))
//                drawDashBoard();
        }
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

            if (winnerByColumn(dashBoard, players, playerTurn))
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

    // Still working need more work *** Need to go through the column and
    // get their value and compare it with the player for a win
    private boolean winnerByColumn(String[][] dashBoard, List<Player> players, String playerTurn) {
        int columnPosition = 0;
        for (int i = 0; i < players.size(); i++) {
            int counter = 0;
            for (int col = 0; col < players.size(); col++) {
                for (int c = 0; c < dashBoard.length; c++) {
                    for (int r = 0; r < dashBoard[c].length; r++) {
                        if (dashBoard[c][col].equals(playerTurn)) {
                            System.out.println("Column " + c + " position " + col + " turn " + playerTurn);
                            counter++;
                        }
                        break;
                    }
                }
            }
            columnPosition++;

            if (counter == 3) {
                winner(players);
                return true;
            }

        }

        return false;
    }

    private void winner(List<Player> players) {
        if (playerTurn.equals("X")) {
            drawDashBoard();
            System.out.println("\nGame over. " + players.get(0).getFullName() + " won");
        } else {
            drawDashBoard();
            System.out.println("\nGame over. " + players.get(1).getFullName() + " won");
        }
        this.playerTurn = (playerTurn.equals("X")) ? "O" : "X";
        this.found = false;
    }
}
