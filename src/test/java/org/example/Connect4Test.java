package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Connect4Test {

    @Test
    void testBoardInitialization() {
        Board board = new Board();
        char[][] grid = board.getGrid();

        assertEquals(6, grid.length); // Ellenőrizzük, hogy 6 sor van
        assertEquals(7, grid[0].length); // Ellenőrizzük, hogy 7 oszlop van

        for (char[] row : grid) {
            for (char cell : row) {
                assertEquals('.', cell); // Ellenőrizzük, hogy minden cella üres ('.')
            }
        }
    }

    @Test
    void testPlaceDisc() {
        Board board = new Board();
        boolean success = board.placeDisc("a", 'P'); // Rakjunk egy piros korongot az 'a' oszlopba
        assertTrue(success); // Sikeresnek kell lennie a lépésnek

        char[][] grid = board.getGrid();
        assertEquals('P', grid[5][0]); // A korongnak az 'a' oszlop alján kell lennie (5. sor, 0. oszlop)
    }

    @Test
    void testPlaceDiscInvalidColumn() {
        Board board = new Board();
        boolean success = board.placeDisc("z", 'P'); // Érvénytelen oszlop ('z')
        assertFalse(success); // Nem lehet sikeres
    }

    @Test
    void testPlaceDiscFullColumn() {
        Board board = new Board();
        for (int i = 0; i < 6; i++) {
            assertTrue(board.placeDisc("a", 'P')); // Töltsük fel az 'a' oszlopot
        }
        boolean success = board.placeDisc("a", 'S'); // Próbáljunk még egy korongot rakni
        assertFalse(success); // Nem lehet sikeres, mert az oszlop tele van
    }

    @Test
    void testSaveAndLoadState() throws IOException {
        Board board = new Board();
        board.placeDisc("a", 'P');
        board.placeDisc("b", 'S');

        // Állapot mentése
        String filename = "test_board.txt";
        board.saveState(filename);

        // Új tábla, állapot betöltése
        Board loadedBoard = new Board();
        loadedBoard.loadState(filename);

        char[][] loadedGrid = loadedBoard.getGrid();
        assertEquals('P', loadedGrid[5][0]); // Ellenőrizzük, hogy az 'a' oszlopban a piros korong az alján van
        assertEquals('S', loadedGrid[5][1]); // Ellenőrizzük, hogy a 'b' oszlopban a sárga korong az alján van
    }
}
