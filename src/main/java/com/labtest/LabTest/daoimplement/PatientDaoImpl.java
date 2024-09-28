package com.labtest.LabTest.daoimplement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.labtest.LabTest.dao.PatientDao;
import com.labtest.LabTest.entity.Patient;
import com.labtest.LabTest.helper.DatabaseConnection;

public class PatientDaoImpl implements PatientDao{

	@Override
	public Patient getPatientById(int patientId) throws SQLException {
		Connection connection=DatabaseConnection.getConnection();
		String query="select*from Patient where patientId=?";
		PreparedStatement statement=connection.prepareStatement(query);
		statement.setInt(1, patientId);
		ResultSet rs=statement.executeQuery();
		Patient patient=null;
		if(rs.next()) {
			patient = new Patient();
			patient.setPatientId(rs.getInt("patientId"));
			patient.setFirstname(rs.getString("firstname"));
			patient.setLastname(rs.getString("lastname"));
			patient.setDateofbirth(rs.getDate("dateofbirth"));
			patient.setContactNumber(rs.getString("contactNumber"));
			patient.setAddress(rs.getString("address"));
		}
		statement.close();
		connection.close();
		return patient;
	}

	@Override
	public void createPatient(Patient patient) throws SQLException {
		Connection connection = null;
		PreparedStatement statement= null;
		try {
			connection=DatabaseConnection.getConnection();
			String query="insert into patient(firstname,lastname,dateofbirth,contactNumber,address)values(?,?,?,?,?)";
			statement= connection.prepareStatement(query);
			statement.setString(1, patient.getFirstname());
			statement.setString(2, patient.getLastname());
			statement.setDate(3, patient.getDateofbirth());
			statement.setString(4, patient.getContactNumber());
			statement.setString(5, patient.getAddress());
			int rowsInserted=statement.executeUpdate();
			if(rowsInserted>0) {
				System.out.println("New Patient inserted Successfully !!");
			}		
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			if(connection!=null)statement.close();
			if(connection!=null)connection.close();
			
		}
	}

	@Override
	public void updatePatient(Patient patient) throws SQLException {
		Connection connection = null;
		PreparedStatement statement= null;
		try {
			connection=DatabaseConnection.getConnection();
			String query="update Patient set datofbirth=?,contactNumber=?,address =?,where patientId=?";
			statement=connection.prepareStatement(query);
			statement.setDate(3, patient.getDateofbirth());
			statement.setString(4, patient.getContactNumber());
			statement.setString(5, patient.getAddress());
			
			int rowsUpdated=statement.executeUpdate();
			if(rowsUpdated>0) {
				System.out.println(" Patient Updated Successfully !!");
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			if(connection!=null)statement.close();
			if(connection!=null)connection.close();
			
		}
	}

}
