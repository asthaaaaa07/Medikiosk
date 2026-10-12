
package com.medikiosk.dao;

import com.medikiosk.config.DatabaseConnection;
import com.medikiosk.config.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {

    public int getTotalPatients() throws DatabaseException {
        return getCount("SELECT COUNT(*) FROM patients");
    }

    public int getTotalAppointments() throws DatabaseException {
        return getCount("SELECT COUNT(*) FROM appointments");
    }

    public int getTotalCaseRecords() throws DatabaseException {
        return getCount("SELECT COUNT(*) FROM medical_history");
    }

    private int getCount(String sql) throws DatabaseException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                return result.getInt(1);
            }

            return 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Could not load dashboard statistics: " + e.getMessage(),
                    e
            );
        }
    }
}
