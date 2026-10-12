package com.medikiosk.config;

/**
 * Checked exception used by DAO and service layers so the UI can show a friendly message
 * instead of a raw SQL stack trace.
 */
public class DatabaseException extends Exception {

    private static final long serialVersionUID = 1L;

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
