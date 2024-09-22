package com.labtest.LabTest.dao;

import java.sql.SQLException;

import com.labtest.LabTest.entity.Patient;

public interface PatientDao {
	Patient getPatientById (int patientId) throws SQLException;
	void createPatient (Patient patient) throws SQLException;
	void updatePatient(Patient patient)throws SQLException;
}
