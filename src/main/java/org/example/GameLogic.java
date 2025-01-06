package org.example;

import java.util.Random;

public class GameLogic {

    private final Random random = new Random();

    public boolean checkWin(Board board, char disc) {
        //a tábla gridje a board classból
        char[][] grid = board.getGrid();

        //minden cellát végigvizgálunk
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                //vízszintes (0, 1)
                if (checkDirection(grid, row, col, 0, 1, disc) ||
                        //függőleges (1, 0)
                        checkDirection(grid, row, col, 1, 0, disc) ||
                        //átlósan lefelé (1, 1)
                        checkDirection(grid, row, col, 1, 1, disc) ||
                        //átlósan felfelé (1, -1)
                        checkDirection(grid, row, col, 1, -1, disc)) {
                    return true;
                }
            }
        }
        return false;
    }

    //kezdőpozíció (row, col) és irány (dRow, dCol) valamint vizsgált korong (disc)
    private boolean checkDirection(char[][] grid, int row, int col, int dRow, int dCol, char disc) {
        //számláló (hány disc)
        int count = 0;
        //négy lépés a vizsgált irányba
        for (int i = 0; i < 4; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            //ha a pozíció a tábla határain belül van és a korong = cella értéke
            if (r >= 0 && r < 6 && c >= 0 && c < 7 && grid[r][c] == disc) {
                count++;
            } else {
                //ha nem egyezik a korong a cella értékével, akkor tovább nem vizsgál
                break;
            }
        }
        //ha 4 korong van egymás mellett, akkor győzelem
        return count == 4;
    }

    //AI lépése, random 0-6 közötti oszlop
    public void computerMove(Board board) {
        //ha megtelt egy adott oszlop akkor újat választ
        while (!board.placeDisc(String.valueOf((char) ('a' + random.nextInt(7))), 'P')) {
        }
    }
}
