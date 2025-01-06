package org.example;

import java.io.*;
import java.util.Arrays;

public class Board {
    //2d-s tömb a táblához
    private final char[][] grid;
    private final int rows = 6;
    private final int columns = 7;
    private final char emptySlot = '_';

    public Board() {
        //grid méret meghatározása előre megírt értékekkel
        grid = new char[rows][columns];

        //minden cella értéke üres
        for (char[] row : grid) {
            Arrays.fill(row, emptySlot);
        }
    }

    //korong lerakása adott oszlopba
    public boolean placeDisc(String column, char disc) {
        //Ai és a player is betűvel ad meg oszlopot
        int colIndex = column.charAt(0) - 'a';
        //ha nem a megadott oszlopokon belül van az oszlop akkor false
        if (colIndex < 0 || colIndex >= columns) {
            return false;
        }
        // a legalsó sortól (rows-1) indulunk felfelé (i--) és ha találunk üres cellát akkor oda kerül a korong
        for (int i = rows - 1; i >= 0; i--) {
            //ha az adott cella üres akkor abba kerül a korong
            if (grid[i][colIndex] == emptySlot) {
                grid[i][colIndex] = disc;
                return true;
            }
        }
        //ha az oszlop megtelt akkor false
        return false;
    }

    //tábla kiíratása
    public void printBoard() {
        // a tábla oszlopainak kiíratása
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println("a b c d e f g");
    }

    //tábla állapotának mentése txt-be
    public void saveState(String filename) throws IOException {
        // BufferedWriter automatikusan bezáródik a végén
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            //soronként kiírjuk a tábla celláit a fájlba
            for (char[] row : grid) {
                writer.write(new String(row));
                writer.newLine();
            }
        }
    }

    //tábla állapotának betöltése txt-ből amennyiben létezik
    public void loadState(String filename) throws IOException {
        File file = new File(filename);
        //ha nem létezik a fájl akkor nem csinálunk semmit, üres táblával indul a játék
        if (!file.exists()) return;

        // BufferedReader automatikusan beolvassa sorokat és betölti a gridbe
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                grid[row] = line.toCharArray();
                row++;
            }
        }
    }

    //getter a gridhez hogy elérjék más classok
    public char[][] getGrid() {
        return grid;
    }
}
