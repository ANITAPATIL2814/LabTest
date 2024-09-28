package com.labtest.LabTest.dao;

import java.sql.SQLException;
import java.util.List;

import com.labtest.LabTest.entity.Appointment;

public interface AppointmentDao {
	 void createAppointment(Appointment ap) throws SQLException;
	 List<Appointment> viewAppointment(int patientId) throws SQLException;
	 void updateAppointment(Appointment ap) throws SQLException;
}
