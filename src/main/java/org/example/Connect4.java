package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Connect4 {
    public static void main(String[] args) throws IOException {
        Board board = new Board();
        GameLogic gameLogic = new GameLogic();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Üdvözöllek a Connect4 játékban!");
        board.loadState("game_state.txt");

        while (true) {
            board.printBoard();
            System.out.print("Add meg az oszlop betűjét (a-g), ahová esni fog a korong: ");
            String column = scanner.nextLine();

            if (!board.placeDisc(column, 'Y')) {
                System.out.println("Helytelen lépés, próbáld újra!");
                continue;
            }

            if (gameLogic.checkWin(board, 'Y')) {
                board.printBoard();
                System.out.println("Gratulálok, nyertél!");
                break;
            }

            System.out.println("Gép lép...");
            gameLogic.computerMove(board);

            if (gameLogic.checkWin(board, 'R')) {
                board.printBoard();
                System.out.println("Sajnálom, vesztettél!");
                break;
            }
        }

        board.saveState("game_state.txt");
    }
}
