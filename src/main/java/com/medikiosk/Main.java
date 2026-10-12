package com.medikiosk;

import com.medikiosk.config.DatabaseConnection;
import com.medikiosk.config.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        System.out.println("MediKiosk - Database Connectivity Check");

        try {
            DatabaseConnection.testConnection();

            System.out.println("[OK] Connected to MySQL!");

            String sql = "SELECT COUNT(*) FROM patients";

            try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
            ) {
                result.next();

                System.out.println(
                    "[OK] Patients table is accessible. Current patient count: "
                    + result.getInt(1)
                );
            }

        } catch (DatabaseException e) {
            System.out.println("[FAIL] " + e.getMessage());

        } catch (SQLException e) {
            System.out.println("[FAIL] Could not query patients: " + e.getMessage());
        }
    }
}