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
		PreparedStatement st= null;
		try {
			connection=DatabaseConnection.getConnection();
			connection.setAutoCommit(false);
			String query="insert into Appointment(patientId,userId,appointmentDate,appointmentTime,testType) values(?,?,?,?,?)";
			st=connection.prepareStatement(query);
			st.setInt(1, ap.getPatient().getPatientId());
			st.setInt(1,ap.getUser().getUserId());
			st.setDate(3, ap.getAppointmentDate());
			st.setTime(4, ap.getAppointmentTime());
			st.setString(5, ap.getTestType());
			connection.commit();
			
			int rowsInserted=st.executeUpdate();
			if(rowsInserted>0) {
				System.out.println("New appointment schedule Successfully !!");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			if(connection!=null)st.close();
			if(connection!=null)connection.close();
			
		}
	
	}

	@Override
	public List<Appointment> viewAppointment(int patientId) throws SQLException {
		Connection connection=null;
   	 try {
	            connection = DatabaseConnection.getConnection();
	            connection.setAutoCommit(false);  // Start transaction
	            
   	List<Appointment> appointments = new ArrayList<>();
       String query = "SELECT * FROM appointment WHERE patient_id = ?";
       PreparedStatement pstmt = connection.prepareStatement(query);
       pstmt.setInt(1, patientId);
       ResultSet rs = pstmt.executeQuery();

       while (rs.next()) {
           Appointment appointment = new Appointment();
           appointment.setAppointmentId(patientId);
           appointment.setAppointmentDate(rs.getDate("appointment_date"));
           appointment.setAppointmentTime(rs.getTime("appointment_time"));
           appointment.setTestType(rs.getString("test_type"));
           // You can also set User and Patient objects here if needed
           appointments.add(appointment);
       }
       return appointments;
       
   	} catch (SQLException e) {
        if (connection != null) {
            connection.rollback();  // Rollback transaction in case of error
        }
        throw e;
    } 

	}

	@Override
	public void updateAppointment(Appointment ap) throws SQLException {
		 Connection connection = null;
	        PreparedStatement updateStatement = null;

	        try {
	            connection = DatabaseConnection.getConnection();
	            connection.setAutoCommit(false);  // Start transaction

	            String updateQuery = "UPDATE Appointment SET patient_id = ?, user_id = ?, appointment_date = ?, appointment_time = ?, test_type = ? WHERE appointment_id = ?";
	            updateStatement = connection.prepareStatement(updateQuery);
	            updateStatement.setInt(1, ap.getPatient().getPatientId());
	            updateStatement.setInt(2, ap.getUser().getUserId());
	            updateStatement.setDate(3, ap.getAppointmentDate());
	            updateStatement.setTime(4, ap.getAppointmentTime());
	            updateStatement.setString(5, ap.getTestType());
	            updateStatement.setInt(6, ap.getAppointmentId());  // Assuming appointment_id is primary key
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


