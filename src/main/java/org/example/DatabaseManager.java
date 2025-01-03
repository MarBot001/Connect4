package org.example;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/connect4";
    private static final String USER = "root";
    private static final String PASSWORD = "";


    private Connection connection;

    public DatabaseManager() throws SQLException {

        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void updateWins(String player) throws SQLException {
        String selectQuery = "SELECT winsnumber FROM wins WHERE player = ?";
        String updateQuery = "UPDATE wins SET winsnumber = ? WHERE player = ?";
        String insertQuery = "INSERT INTO wins (player, winsnumber) VALUES (?, ?)";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, player);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int winsNumber = rs.getInt("winsnumber") + 1;
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, winsNumber);
                    updateStmt.setString(2, player);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, player);
                    insertStmt.setInt(2, 1);
                    insertStmt.executeUpdate();
                }
            }
        }
    }
    public void printWinsTable() throws SQLException {
        String query = "SELECT * FROM wins";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Eddigi győzelmek:");
            while (rs.next()) {
                String player = rs.getString("player");
                int wins = rs.getInt("winsnumber");
                System.out.println(player + ", Győzelmek száma: " + wins);
            }
        }
    }


    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}