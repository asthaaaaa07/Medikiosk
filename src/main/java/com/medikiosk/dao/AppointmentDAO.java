
package com.medikiosk.dao;

import com.medikiosk.config.DatabaseConnection;
import com.medikiosk.config.DatabaseException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Save a new appointment
    public int saveAppointment(int patientId, LocalDate appointmentDate,
                               String reason) throws DatabaseException {

        String sql = """
                INSERT INTO appointments
                (patient_id, appointment_date, reason)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql,
                             PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, patientId);
            statement.setDate(2, Date.valueOf(appointmentDate));
            statement.setString(3, reason);

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

            throw new SQLException("Appointment ID was not generated.");

        } catch (SQLException e) {
            throw new DatabaseException("Could not save appointment.", e);
        }
    }

    // Retrieve appointments along with patient names
    public List<Object[]> getAllAppointments() throws DatabaseException {

        List<Object[]> appointments = new ArrayList<>();

        String sql = """
                SELECT a.appointment_id, a.patient_id, p.full_name,
                       a.appointment_date, a.reason, a.status
                FROM appointments a
                JOIN patients p ON a.patient_id = p.patient_id
                ORDER BY a.appointment_id DESC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Object[] row = {
                        result.getInt("appointment_id"),
                        result.getInt("patient_id"),
                        result.getString("full_name"),
                        result.getDate("appointment_date"),
                        result.getString("reason"),
                        result.getString("status")
                };

                appointments.add(row);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not load appointments.", e);
        }

        return appointments;
    }

    // Update an appointment's status
    public void updateAppointmentStatus(int appointmentId, String status)
            throws DatabaseException {

        if (!List.of("Scheduled", "Completed", "Cancelled").contains(status)) {
            throw new IllegalArgumentException("Invalid appointment status.");
        }

        String sql = """
                UPDATE appointments
                SET status = ?
                WHERE appointment_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, appointmentId);

            int updated = statement.executeUpdate();

            if (updated == 0) {
                throw new SQLException("Appointment not found.");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Could not update appointment status.", e);
        }
    }
}
