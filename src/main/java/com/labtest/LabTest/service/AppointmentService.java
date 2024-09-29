package com.labtest.LabTest.service;

import java.util.List;

import com.labtest.LabTest.daoimplement.AppointmentDaoImpl;
import com.labtest.LabTest.entity.Appointment;

public class AppointmentService {
	
	private AppointmentDaoImpl appointmentDao;

	public AppointmentService() {
		this.appointmentDao = new AppointmentDaoImpl();
	}	
	
	public void scheduleAppointment(Appointment appointment) {
		try {
			appointmentDao.createAppointment(appointment);
			System.out.println("Appointment Created Successfully");
		}catch(Exception e) {
			System.out.println("Error scheduling appointment " + e);
		}
	}	
	public List<Appointment> viewAppointmentByPatientId(int patientId){
		List<Appointment> appointments = null;
		try {
			appointments = appointmentDao.viewAppointment(patientId);
			if(appointments.isEmpty()) {
				System.out.println("No appointment found with patient id");
			}
		}catch(Exception e) {
			System.out.println("Error Fetching appointment " + e);
		}
		return appointments;
	}
	
	public void updateAppointment(Appointment appointment) {
		try {
			appointmentDao.updateAppointment(appointment);
			System.out.println("Appointment Updated Successfully");
		}catch(Exception e) {
				System.out.println("Error Updating appointment " + e);
			}
		}
	}


