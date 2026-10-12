
package com.medikiosk.dao;

import com.medikiosk.config.DatabaseConnection;
import com.medikiosk.config.DatabaseException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class PatientDAO {

    public int savePatient(
            String fullName,
            LocalDate dateOfBirth,
            String gender,
            String phone,
            String email,
            String address
    ) throws DatabaseException {

        String sql = """
                INSERT INTO patients
                (full_name, date_of_birth, gender, phone, email, address)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, fullName);

            if (dateOfBirth != null) {
                statement.setDate(2, Date.valueOf(dateOfBirth));
            } else {
                statement.setNull(2, java.sql.Types.DATE);
            }

            statement.setString(3, gender);
            statement.setString(4, phone);
            statement.setString(5, email);
            statement.setString(6, address);

            int rows = statement.executeUpdate();

            if (rows == 0) {
                throw new DatabaseException(
                        "Patient could not be saved."
                );
            }

            try (var keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

            throw new DatabaseException(
                    "Patient was saved, but the generated ID was unavailable."
            );

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Could not save patient: " + e.getMessage(), e
            );
        }
    }
public java.util.List<Object[]> getAllPatients() throws DatabaseException {
    String sql = """
            SELECT patient_id, full_name, date_of_birth, gender, phone, email
            FROM patients
            ORDER BY patient_id DESC
            """;

    java.util.List<Object[]> patients = new java.util.ArrayList<>();

    try (java.sql.Connection connection =
                 com.medikiosk.config.DatabaseConnection.getConnection();
         java.sql.PreparedStatement statement =
                 connection.prepareStatement(sql);
         java.sql.ResultSet result = statement.executeQuery()) {

        while (result.next()) {
            patients.add(new Object[]{
                result.getInt("patient_id"),
                result.getString("full_name"),
                result.getDate("date_of_birth"),
                result.getString("gender"),
                result.getString("phone"),
                result.getString("email")
            });
        }

        return patients;

    } catch (java.sql.SQLException e) {
        throw new DatabaseException(
            "Could not load patient records: " + e.getMessage(), e);
    }
}

}
