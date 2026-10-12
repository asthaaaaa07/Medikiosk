
package com.medikiosk.dao;

import com.medikiosk.config.DatabaseConnection;
import com.medikiosk.config.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryDAO {

    public void saveMedicalHistory(
            int patientId,
            String symptoms,
            String allergies,
            String existingConditions,
            String currentMedications,
            String bloodGroup
    ) throws DatabaseException {

        String sql = """
                INSERT INTO medical_history
                (patient_id, symptoms, allergies, existing_conditions,
                 current_medications, blood_group)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);
            statement.setString(2, symptoms);
            statement.setString(3, allergies);
            statement.setString(4, existingConditions);
            statement.setString(5, currentMedications);
            statement.setString(6, bloodGroup);

            int rows = statement.executeUpdate();

            if (rows == 0) {
                throw new DatabaseException(
                        "Medical history could not be saved.");
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Could not save medical history: " + e.getMessage(), e);
        }
    }
public List<Object[]> getAllMedicalHistories() throws DatabaseException {
    String sql = """
            SELECT mh.history_id, p.patient_id, p.full_name,
                   mh.symptoms, mh.allergies, mh.existing_conditions,
                   mh.current_medications, mh.blood_group,
                   mh.recorded_at
            FROM medical_history mh
            JOIN patients p ON mh.patient_id = p.patient_id
            ORDER BY mh.history_id DESC
            """;

    List<Object[]> histories = new ArrayList<>();

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet result = statement.executeQuery()) {

        while (result.next()) {
            histories.add(new Object[]{
                result.getInt("history_id"),
                result.getInt("patient_id"),
                result.getString("full_name"),
                result.getString("symptoms"),
                result.getString("allergies"),
                result.getString("existing_conditions"),
                result.getString("current_medications"),
                result.getString("blood_group"),
                result.getTimestamp("recorded_at")
            });
        }

        return histories;

    } catch (SQLException e) {
        throw new DatabaseException(
                "Could not load case histories: " + e.getMessage(), e);
    }
}

}
