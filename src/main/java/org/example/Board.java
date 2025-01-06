package org.example;

import java.io.*;
import java.util.Arrays;

public class Board {
    private final char[][] grid;
    private final int rows = 6;
    private final int columns = 7;
    private final char emptySlot = '_';

    public Board() {
        grid = new char[rows][columns];
        for (char[] row : grid) {
            Arrays.fill(row, emptySlot);
        }
    }

    public boolean placeDisc(String column, char disc) {
        int colIndex = column.charAt(0) - 'a';
        if (colIndex < 0 || colIndex >= columns) {
            return false;
        }
        for (int i = rows - 1; i >= 0; i--) {
            if (grid[i][colIndex] == emptySlot) {
                grid[i][colIndex] = disc;
                return true;
            }
        }
        return false;
    }

    public void printBoard() {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("a b c d e f g");
    }

    public void saveState(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (char[] row : grid) {
                writer.write(new String(row));
                writer.newLine();
            }
        }
    }

    public void loadState(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                grid[row] = line.toCharArray();
                row++;
            }
        }
    }

    public char[][] getGrid() {
        return grid;
    }
}
