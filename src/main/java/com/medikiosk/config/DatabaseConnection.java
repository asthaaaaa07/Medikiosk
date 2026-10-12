package com.medikiosk.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The ONE place where a JDBC connection to MySQL is opened.
 *
 * How JDBC works (viva answer):
 *   Java code -> JDBC API (java.sql) -> MySQL Connector/J driver -> MySQL server.
 * DriverManager.getConnection(url, user, password) returns a Connection object.
 * DAOs use that Connection to run PreparedStatements.
 *
 * Each call opens a short-lived connection and callers close it with try-with-resources.
 * This is simple and fine for a single-desktop prototype.
 */
public final class DatabaseConnection {

    private DatabaseConnection() { }

    /** Opens a new connection. Throws DatabaseException with a student-friendly message on failure. */
    public static Connection getConnection() throws DatabaseException {
        String password = AppConfig.getDbPassword();
        if (password == null) {
            throw new DatabaseException(
                    "Database password is not configured.\n"
                  + "Copy config.properties.example to config.properties and set db.password.\n"
                  + "Expected location: " + AppConfig.describeConfigLocation());
        }
        try {
            return DriverManager.getConnection(AppConfig.getDbUrl(), AppConfig.getDbUser(), password);
        } catch (SQLException e) {
            throw new DatabaseException(explain(e), e);
        }
    }

    /** Quick health check used at start-up. */
    public static void testConnection() throws DatabaseException {
        try (Connection c = getConnection()) {
            if (!c.isValid(3)) {
                throw new DatabaseException("The database connection was opened but is not valid.");
            }
        } catch (SQLException e) {
            throw new DatabaseException(explain(e), e);
        }
    }

    /** Turns technical SQL errors into messages a user can act on (no passwords are included). */
    private static String explain(SQLException e) {
        int code = e.getErrorCode();
        if (code == 1045) {
            return "MySQL rejected the username or password. Check db.user / db.password in config.properties.";
        }
        if (code == 1049) {
            return "Database 'medikiosk' does not exist. Run database/schema.sql first.";
        }
        String state = e.getSQLState();
        if (state != null && state.startsWith("08")) {
            return "Cannot reach MySQL. Make sure the MySQL server is running and db.url is correct.";
        }
        return "Database error: " + e.getMessage();
    }
}
