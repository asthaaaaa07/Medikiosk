package com.medikiosk.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Loads settings (database URL, user, password, upload folder) from OUTSIDE the source code.
 *
 * Order of priority:
 *   1. Environment variables (MEDIKIOSK_DB_URL, MEDIKIOSK_DB_USER, MEDIKIOSK_DB_PASSWORD, ...)
 *   2. config.properties file in the project's working folder
 *
 * No password is ever written in a .java file.
 */
public final class AppConfig {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPS = new Properties();
    private static boolean fileLoaded = false;

    private AppConfig() { }

    /** Reads config.properties once. Missing file is allowed (environment variables may be used). */
    private static synchronized void loadIfNeeded() {
        if (fileLoaded) {
            return;
        }
        Path path = Paths.get(CONFIG_FILE).toAbsolutePath();
        try (InputStream in = new FileInputStream(path.toFile())) {
            PROPS.load(in);
        } catch (IOException e) {
            // Not fatal: DatabaseConnection will give a friendly error if settings are missing.
        }
        fileLoaded = true;
    }

    private static String get(String envName, String propName, String defaultValue) {
        String env = System.getenv(envName);
        if (env != null && !env.isBlank()) {
            return env.trim();
        }
        loadIfNeeded();
        String value = PROPS.getProperty(propName);
        return (value == null || value.isBlank()) ? defaultValue : value.trim();
    }

    public static String getDbUrl() {
        return get("MEDIKIOSK_DB_URL", "db.url",
                "jdbc:mysql://localhost:3306/medikiosk?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    }

    public static String getDbUser() {
        return get("MEDIKIOSK_DB_USER", "db.user", "root");
    }

    /** Returns null when no password was configured. */
    public static String getDbPassword() {
        return get("MEDIKIOSK_DB_PASSWORD", "db.password", null);
    }

    public static String getUploadDir() {
        return get("MEDIKIOSK_UPLOAD_DIR", "upload.dir", "uploads");
    }

    public static long getMaxUploadBytes() {
        String mb = get("MEDIKIOSK_UPLOAD_MAX_MB", "upload.max.mb", "10");
        try {
            return Long.parseLong(mb) * 1024L * 1024L;
        } catch (NumberFormatException e) {
            return 10L * 1024L * 1024L;
        }
    }

    /** True if the config file exists or a password env variable is present. */
    public static boolean isConfigured() {
        return getDbPassword() != null;
    }

    public static String describeConfigLocation() {
        return Paths.get(CONFIG_FILE).toAbsolutePath().toString();
    }
}
