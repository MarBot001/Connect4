package org.example;

import java.io.IOException;
import java.util.Scanner;
import java.sql.SQLException;

public class Connect4 {
    public static void main(String[] args) throws IOException {
        Board board = new Board();
        GameLogic gameLogic = new GameLogic();
        Scanner scanner = new Scanner(System.in);

        DatabaseManager dbManager = null;
        try {
            dbManager = new DatabaseManager();
        } catch (SQLException e) {
            System.err.println("Adatbázis kapcsolódás sikertelen!! " + e.getMessage());
            return;
        }

        System.out.println("Üdvözöllek a Connect4-PRO-1000 Edition játékban! A Te színed a sárga (S), az exklúzív módon okos AI színe a piros (P).");
        board.loadState("stategame.txt");

        while (true) {
            board.printBoard();
            System.out.print("Add meg az oszlop betűjét (a-g), ahová a gravitációs mező segítségével ledobod a korongod:");
            String column = scanner.nextLine();

            if (!board.placeDisc(column, 'S')) {
                System.out.println("Úgy látom már lépkedni se tudsz. Válassz már normális opciót!");
                continue;
            }

            if (gameLogic.checkWin(board, 'S')) {
                board.printBoard();
                System.out.println("Ezt sikerült megnyerned! A GPT modell nélküli AI nem volt elég okos... Javítást igényel szóval szólok a ChatGPT-nek!");

                try {
                    dbManager.updateWins("Játékos");
                } catch (SQLException e) {
                    System.err.println("Hiba a győzelem frissítésekor: " + e.getMessage());
                }
                try {
                    dbManager.printWinsTable();
                } catch (SQLException e) {
                    System.err.println("Hiba az adatbázis táblájának megjelenítésekor: " + e.getMessage());
                }

                break;
            }

            System.out.println("Az AI épp gondolkodik és talán lép is (már persze ha nem ChatGPT 3.5 a motorja...)...");
            gameLogic.computerMove(board);

            if (gameLogic.checkWin(board, 'P')) {
                board.printBoard();
                System.out.println("Ez bukó mint az Analízis ZH! :(");

                try {
                    dbManager.updateWins("AI");
                } catch (SQLException e) {
                    System.err.println("Hiba az AI győzelmének frissítésekor: " + e.getMessage());
                }
                try {
                    dbManager.printWinsTable();
                } catch (SQLException e) {
                    System.err.println("Hiba az adatbázis táblájának megjelenítésekor: " + e.getMessage());
                }

                break;
            }
        }

        board.saveState("stategame.txt");

        try {
            dbManager.close();
        } catch (SQLException e) {
            System.err.println("Hiba a kapcsolat lezárásakor: " + e.getMessage());
        }
    }
}
