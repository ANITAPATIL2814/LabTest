package com.labtest.LabTest.daoimplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.labtest.LabTest.dao.AppointmentDao;
import com.labtest.LabTest.entity.Appointment;
import com.labtest.LabTest.helper.DatabaseConnection;

public class AppointmentDaoImpl implements AppointmentDao {

	@Override
	public void createAppointment(Appointment ap) throws SQLException {
	    Connection connection = null;
	    PreparedStatement st = null;

	    try {
	        connection = DatabaseConnection.getConnection();
	        connection.setAutoCommit(false);  // Start transaction

	        String query = "INSERT INTO Appointment(patientId, userId, appointmentDate, appointmentTime, testType) VALUES (?, ?, ?, ?, ?)";
	        st = connection.prepareStatement(query);
	        st.setInt(1, ap.getPatient().getPatientId());
	        st.setInt(2, ap.getUser().getUserId());
	        st.setDate(3, ap.getAppointmentDate());
	        st.setTime(4, ap.getAppointmentTime());
	        st.setString(5, ap.getTestType());

	        int rowsInserted = st.executeUpdate();
	        
	        // Commit the transaction after successful insertion
	        if (rowsInserted > 0) {
	            connection.commit();  // Commit here, after executeUpdate
	            System.out.println("New Appointment scheduled successfully.");
	        }
	    } catch (SQLException e) {
	        if (connection != null) {
	            connection.rollback();  // Rollback transaction in case of error
	        }
	        System.err.println("Error scheduling appointment: " + e.getMessage());
	        throw e;
	    } finally {
	        if (st != null) st.close();
	        if (connection != null) connection.close();
	    }
	}


	@Override
	public List<Appointment> viewAppointment(int patientId) throws SQLException {
	    Connection connection = null;
	    List<Appointment> appointments = new ArrayList<Appointment>();
	    
	    try {
	        connection = DatabaseConnection.getConnection();
	        connection.setAutoCommit(false);  // Start transaction

	        // Use the correct column name for patient ID (assuming it's 'patientId')
	        String query = "SELECT * FROM appointment WHERE patientId = ?";
	        PreparedStatement pstmt = connection.prepareStatement(query);
	        pstmt.setInt(1, patientId);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Appointment appointment = new Appointment();
	            appointment.setAppointmentId(rs.getInt("appointmentId"));  // Assuming 'appointmentId' is the correct column name
	            appointment.setAppointmentDate(rs.getDate("appointmentDate"));
	            appointment.setAppointmentTime(rs.getTime("appointmentTime"));
	            appointment.setTestType(rs.getString("testType"));
	            // Set additional fields as needed
	            
	            appointments.add(appointment);
	        }

	        connection.commit();  // Commit transaction
	    } catch (SQLException e) {
	        if (connection != null) {
	            connection.rollback();  // Rollback transaction in case of error
	        }
	        throw e;
	    } finally {
	        if (connection != null) {
	            connection.close();
	        }
	    }
	    
	    return appointments;
	}


	public void updateAppointment(Appointment appointment) throws SQLException {
        Connection connection = null;
        PreparedStatement updateStatement = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);  // Start transaction

            String updateQuery = "UPDATE Appointment SET patient_id = ?, user_id = ?, appointment_date = ?, appointment_time = ?, test_type = ? WHERE appointment_id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, appointment.getPatient().getPatientId());
            updateStatement.setInt(2, appointment.getUser().getUserId());
            updateStatement.setDate(3, appointment.getAppointmentDate());
            updateStatement.setTime(4, appointment.getAppointmentTime());
            updateStatement.setString(5, appointment.getTestType());
            updateStatement.setInt(6, appointment.getAppointmentId());  // Assuming appointment_id is primary key
            updateStatement.executeUpdate();

            connection.commit();  // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();  // Rollback transaction in case of error
            }
            throw e;
        } finally {
            if (updateStatement != null) updateStatement.close();
            if (connection != null) connection.close();
        }
    }
}