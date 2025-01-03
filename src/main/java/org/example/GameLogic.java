package org.example;

import java.util.Random;

public class GameLogic {

    private final Random random = new Random();

    public boolean checkWin(Board board, char disc) {
        char[][] grid = board.getGrid();

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (checkDirection(grid, row, col, 0, 1, disc) ||
                        checkDirection(grid, row, col, 1, 0, disc) ||
                        checkDirection(grid, row, col, 1, 1, disc) ||
                        checkDirection(grid, row, col, 1, -1, disc)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(char[][] grid, int row, int col, int dRow, int dCol, char disc) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < 6 && c >= 0 && c < 7 && grid[r][c] == disc) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
    }

    public void computerMove(Board board) {
        while (!board.placeDisc(String.valueOf((char) ('a' + random.nextInt(7))), 'P')) {
        }
    }
}
