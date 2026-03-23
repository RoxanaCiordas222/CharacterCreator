package org.roxi.charactercreator.model.repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:dnd_data.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        String createCharactersTable = """
            CREATE TABLE IF NOT EXISTS characters (
                id TEXT PRIMARY KEY,
                ownerUsername TEXT NOT NULL,
                name TEXT NOT NULL,
                species TEXT,
                characterClass TEXT,
                level INTEGER,
                strength INTEGER,
                dexterity INTEGER,
                constitution INTEGER,
                intelligence INTEGER,
                wisdom INTEGER,
                charisma INTEGER
            );
            """;

        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT UNIQUE NOT NULL,
                name TEXT NOT NULL,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            );
            """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createCharactersTable);
            stmt.execute(createUsersTable);

        } catch (SQLException e) {
            System.out.println("Database initialization error " + e.getMessage());
        }
    }
}
