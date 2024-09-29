package com.labtest.LabTest;
import java.util.List;
import java.util.Scanner;
import com.labtest.LabTest.daoimplement.AppointmentDaoImpl;
import com.labtest.LabTest.daoimplement.PatientDaoImpl;
import com.labtest.LabTest.daoimplement.UserDaoImp;
import com.labtest.LabTest.entity.Appointment;
import com.labtest.LabTest.entity.Patient;
import com.labtest.LabTest.entity.User;

public class App {
    @SuppressWarnings("resource")
	public static void main( String[] args ){
        Scanner sc = new Scanner(System.in);
        UserDaoImp udao= new UserDaoImp();
        PatientDaoImpl pdao=new PatientDaoImpl();
        AppointmentDaoImpl adao = new AppointmentDaoImpl();
        
        //user login
        System.out.println("Welcome to labtest ");
        System.out.println("Enter username");
        String username=sc.nextLine();
        System.out.println("Enter password");
        String password = sc.nextLine();
        
        User loggedInUser = null;
        try {
        	loggedInUser = udao.login(username, password);
        }catch(Exception e) {
        	System.out.println("Error during login "+e);
        }
        if(loggedInUser == null) {
        	System.out.println("Invalid username or password");
        	return;
        }else {
        	System.out.println("Login successfully.. Welcome");
        }
        //menu 
        while(true) {
        	System.out.println("\n Menu ");
        	System.out.println("1. Create patient");
        	System.out.println("2. Get patient by id");
        	System.out.println("3. Update patient");
        	System.out.println("4. Schedule Appointment");
        	System.out.println("5. View Appointment by Id");
        	System.out.println("6. Update Appointment ");
        	System.out.println("7. Exit");
        	System.out.println("Enter choice ");
        	int choice = sc.nextInt();
        	sc.nextLine();
        	
        	switch(choice) {
        	case 1 : 
        		try {
        			Patient newPatient = new Patient();
        			System.out.println("enter first name");
        			newPatient.setFirstname(sc.nextLine());
        			System.out.println("enter last name");
        			newPatient.setLastname(sc.nextLine());
        			System.out.println("enter date of birth (yyyy-mm-dd)");
        	newPatient.setDateofbirth(java.sql.Date.valueOf(sc.nextLine()));
        	System.out.println("enter contact number");
			newPatient.setContactNumber(sc.nextLine());
			System.out.println("enter address");
			newPatient.setAddress(sc.nextLine());
			
			pdao.createPatient(newPatient);
        		}catch(Exception e ) {
        			System.out.println("error creating patient"+e);
        		}break;
        		
        	case 2:
        		System.out.println("Enter patient id");
        		int patientId = sc.nextInt();
        		try {
        			Patient patient = pdao.getPatientById(patientId);
        		if(patient!=null) {
        			System.out.println("Patient found : "+
     patient.getFirstname() +" "+patient.getLastname()+" "+patient.getContactNumber());
        		}else {
        			System.out.println("No Patient found with ID :" + patientId);
        		}
        		}catch(Exception e) {
        			System.out.println("Error retrieveing patient");
        		}
        		break;
        	case 3:
        		System.out.println("enter patient id");
        		int updatedPatientId = sc.nextInt();
        		sc.nextLine();
        	try{
        		Patient updatedPatient = pdao.getPatientById(updatedPatientId);
        		if(updatedPatient!=null) {
        			System.out.println("Enter new date of birth");
        	updatedPatient.setDateofbirth(java.sql.Date.valueOf(sc.nextLine()));
        			System.out.println("enter contact number");
        			updatedPatient.setContactNumber(sc.nextLine());
        			System.out.println("enter address");
        			updatedPatient.setAddress(sc.nextLine());
        			pdao.updatePatient(updatedPatient);
        		}else {
        			System.out.println("No patient found with id");
        		}
        	}catch(Exception e) {
        		System.out.println("Error updating patient");
        	}	break;
        	
        	case 4:
                // Schedule appointment
                try {
                    Appointment appointment = new Appointment();
                    System.out.print("Enter patient ID: ");
                    int appPatientId = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    Patient appPatient = pdao.getPatientById(appPatientId);
                    if (appPatient == null) {
                        System.out.println("No patient found with ID: " + appPatientId);
                        break;
                    }
                    
                    appointment.setPatient(appPatient);
                    appointment.setUser(loggedInUser); // Set the logged-in user as the doctor/technician
                    
                    System.out.print("Enter appointment date (YYYY-MM-DD): ");
                    appointment.setAppointmentDate(java.sql.Date.valueOf(sc.nextLine()));
                    System.out.print("Enter appointment time (HH:MM:SS): ");
                    appointment.setAppointmentTime(java.sql.Time.valueOf(sc.nextLine()));
                    System.out.print("Enter test type: ");
                    appointment.setTestType(sc.nextLine());
                    
                    adao.createAppointment(appointment);
                } catch (Exception e) {
                    System.out.println("Error scheduling appointment: " + e.getMessage());
                }
                break;
                
            case 5:
                // View appointments by patient ID
                System.out.print("Enter patient ID: ");
                int viewPatientId = sc.nextInt();
                try {
                    List<Appointment> appointments = adao.viewAppointment(viewPatientId);
                    if (appointments.isEmpty()) {
                        System.out.println("No appointments found for patient ID: " + viewPatientId);
                    } else {
                        System.out.println("Appointments:");
                        for (Appointment ap : appointments) {
                            System.out.println("Appointment Date: " + ap.getAppointmentDate() + ", Time: " + ap.getAppointmentTime() + ", Test Type: " + ap.getTestType());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error retrieving appointments: " + e.getMessage());
                }
                break;
                
            case 6:
                // Update appointment
                System.out.print("Enter appointment ID to update: ");
                int updateAppId = sc.nextInt();
                sc.nextLine(); // Consume newline
                try {
                    Appointment updateApp = adao.viewAppointment(updateAppId).get(0); // Assuming single result for simplicity
                    if (updateApp != null) {
                        System.out.print("Enter new appointment date (YYYY-MM-DD): ");
                        updateApp.setAppointmentDate(java.sql.Date.valueOf(sc.nextLine()));
                        System.out.print("Enter new appointment time (HH:MM:SS): ");
                        updateApp.setAppointmentTime(java.sql.Time.valueOf(sc.nextLine()));
                        System.out.print("Enter new test type: ");
                        updateApp.setTestType(sc.nextLine());
                        
                        adao.updateAppointment(updateApp);
                    } else {
                        System.out.println("No appointment found with ID: " + updateAppId);
                    }
                } catch (Exception e) {
                    System.out.println("Error updating appointment: " + e.getMessage());
                }
                break;
                
            case 7:
                // Exit
                System.out.println("Exiting system. Goodbye!");
                sc.close();
                return;
                
            default:
                System.out.println("Invalid option. Please try again.");
        		
        	}
        }
    }
}
