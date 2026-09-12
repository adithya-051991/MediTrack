package com.airtribe.meditrack;

import com.airtribe.meditrack.billing.*;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.exception.*;
import com.airtribe.meditrack.util.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 * Main application class for MediTrack - Menu-driven healthcare management system.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PatientService patientService = new PatientService();
    private static final DoctorService doctorService = new DoctorService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final List<Bill> bills = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("   Welcome to MediTrack v1.0    ");
        System.out.println("================================");
        System.out.println("Healthcare Management System\n");
        
        initializeSampleData();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getUserIntInput();
            
            try {
                switch (choice) {
                    case 1:
                        patientMenu();
                        break;
                    case 2:
                        doctorMenu();
                        break;
                    case 3:
                        appointmentMenu();
                        break;
                    case 4:
                        billingMenu();
                        break;
                    case 5:
                        searchMenu();
                        break;
                    case 6:
                        analyticsMenu();
                        break;
                    case 7:
                        running = false;
                        System.out.println("\nThank you for using MediTrack. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Patient Management");
        System.out.println("2. Doctor Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Billing & Payment");
        System.out.println("5. Search");
        System.out.println("6. Analytics");
        System.out.println("7. Exit");
        System.out.print("Select option: ");
    }

    private static void patientMenu() {
        while (true) {
            System.out.println("\n----- PATIENT MANAGEMENT -----");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. View Patient by ID");
            System.out.println("4. Search Patients");
            System.out.println("5. Update Patient");
            System.out.println("6. Delete Patient");
            System.out.println("7. Back to Main Menu");
            System.out.print("Select option: ");

            int choice = getUserIntInput();
            try {
                switch (choice) {
                    case 1:
                        addPatient();
                        break;
                    case 2:
                        viewAllPatients();
                        break;
                    case 3:
                        viewPatientById();
                        break;
                    case 4:
                        searchPatients();
                        break;
                    case 5:
                        updatePatient();
                        break;
                    case 6:
                        deletePatient();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addPatient() {
        try {
            System.out.print("Enter patient name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter age: ");
            int age = getUserIntInput();

            Patient patient = patientService.createPatient(name, age);
            System.out.println("Patient added successfully! ID: " + patient.getId());
        } catch (Exception e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }
    }

    private static void viewAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.println("\n----- ALL PATIENTS -----");
        patients.forEach(p -> System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Age: " + p.getAge()));
    }

    private static void viewPatientById() {
        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine().trim();
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        System.out.println("\nPatient Details: " + patient.getFullInfo());
    }

    private static void searchPatients() {
        System.out.print("Enter search text (name or age): ");
        String searchText = scanner.nextLine().trim();

        if (searchText.isEmpty()) {
            viewAllPatients();
            return;
        }

        try {
            int age = Integer.parseInt(searchText);
            List<Patient> patients = patientService.searchPatient(age);
            if (patients.isEmpty()) {
                System.out.println("No patients found for age: " + age);
                return;
            }
            patients.forEach(p -> System.out.println(p.getFullInfo()));
            return;
        } catch (NumberFormatException ignored) {
            List<Patient> patients = patientService.searchPatient(searchText);
            if (patients.isEmpty()) {
                System.out.println("No patients found for: " + searchText);
                return;
            }
            patients.forEach(p -> System.out.println(p.getFullInfo()));
        }
    }

    private static void updatePatient() {
        try {
            System.out.print("Enter patient ID to update: ");
            String patientId = scanner.nextLine().trim();
            Patient patient = patientService.getPatientById(patientId);
            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }

            System.out.print("Enter new patient name (current: " + patient.getName() + "): ");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                patient.setName(newName);
            }

            System.out.print("Enter new age (current: " + patient.getAge() + "): ");
            String ageValue = scanner.nextLine().trim();
            if (!ageValue.isEmpty()) {
                patient.setAge(Integer.parseInt(ageValue));
            }

            patientService.updatePatient(patient);
            System.out.println("Patient updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }

    private static void deletePatient() {
        try {
            System.out.print("Enter patient ID: ");
            String patientId = scanner.nextLine().trim();
            patientService.deletePatient(patientId);
            System.out.println("Patient deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void doctorMenu() {
        while (true) {
            System.out.println("\n----- DOCTOR MANAGEMENT -----");
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. View Doctor by ID");
            System.out.println("4. Search Doctors");
            System.out.println("5. Update Doctor");
            System.out.println("6. Delete Doctor");
            System.out.println("7. Back to Main Menu");
            System.out.print("Select option: ");

            int choice = getUserIntInput();
            try {
                switch (choice) {
                    case 1:
                        addDoctor();
                        break;
                    case 2:
                        viewAllDoctors();
                        break;
                    case 3:
                        viewDoctorById();
                        break;
                    case 4:
                        searchDoctors();
                        break;
                    case 5:
                        updateDoctor();
                        break;
                    case 6:
                        deleteDoctor();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addDoctor() {
        try {
            System.out.print("Enter doctor name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter specialization: ");
            String specialization = scanner.nextLine().trim();

            System.out.print("Enter consultation fee: ");
            double fee = getUserDoubleInput();

            Doctor doctor = doctorService.createDoctor(name, specialization, fee);
            System.out.println("Doctor added successfully! ID: " + doctor.getId());
        } catch (Exception e) {
            System.out.println("Error adding doctor: " + e.getMessage());
        }
    }

    private static void viewAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        System.out.println("\n----- ALL DOCTORS -----");
        doctors.forEach(d -> System.out.println("ID: " + d.getId() + ", Name: " + d.getName() +
            ", Specialization: " + d.getSpecialization() + ", Fee: Rs" + d.getConsultationFee()));
    }

    private static void viewDoctorById() {
        System.out.print("Enter doctor ID: ");
        String doctorId = scanner.nextLine().trim();
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }
        System.out.println("\nDoctor Details: " + doctor.getFullInfo() + ", Specialization: " + doctor.getSpecialization());
    }

    private static void searchDoctors() {
        System.out.print("Enter search text (name or specialization): ");
        String searchText = scanner.nextLine().trim();
        if (searchText.isEmpty()) {
            viewAllDoctors();
            return;
        }

        List<Doctor> doctors = doctorService.searchDoctor(searchText, true);
        if (doctors.isEmpty()) {
            doctors = doctorService.searchDoctor(searchText);
        }
        if (doctors.isEmpty()) {
            System.out.println("No doctors found for: " + searchText);
            return;
        }
        doctors.forEach(d -> System.out.println(d.getFullInfo() + ", Specialization: " + d.getSpecialization()));
    }

    private static void updateDoctor() {
        try {
            System.out.print("Enter doctor ID to update: ");
            String doctorId = scanner.nextLine().trim();
            Doctor doctor = doctorService.getDoctorById(doctorId);
            if (doctor == null) {
                System.out.println("Doctor not found.");
                return;
            }

            System.out.print("Enter new doctor name (current: " + doctor.getName() + "): ");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                doctor.setName(newName);
            }

            System.out.print("Enter new specialization (current: " + doctor.getSpecialization() + "): ");
            String newSpecialization = scanner.nextLine().trim();
            if (!newSpecialization.isEmpty()) {
                doctor.setSpecialization(newSpecialization);
            }

            System.out.print("Enter new consultation fee (current: " + doctor.getConsultationFee() + "): ");
            String feeValue = scanner.nextLine().trim();
            if (!feeValue.isEmpty()) {
                doctor.setConsultationFee(Double.parseDouble(feeValue));
            }

            doctorService.updateDoctor(doctor);
            System.out.println("Doctor updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating doctor: " + e.getMessage());
        }
    }

    private static void deleteDoctor() {
        try {
            System.out.print("Enter doctor ID: ");
            String doctorId = scanner.nextLine().trim();
            doctorService.deleteDoctor(doctorId);
            System.out.println("Doctor deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void appointmentMenu() {
        while (true) {
            System.out.println("\n----- APPOINTMENT MANAGEMENT -----");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. View Appointment by ID");
            System.out.println("4. Search Appointments");
            System.out.println("5. Reschedule Appointment");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Delete Appointment");
            System.out.println("8. Back to Main Menu");
            System.out.print("Select option: ");

            int choice = getUserIntInput();
            try {
                switch (choice) {
                    case 1:
                        scheduleAppointment();
                        break;
                    case 2:
                        viewAllAppointments();
                        break;
                    case 3:
                        viewAppointmentById();
                        break;
                    case 4:
                        searchAppointments();
                        break;
                    case 5:
                        rescheduleAppointment();
                        break;
                    case 6:
                        cancelAppointment();
                        break;
                    case 7:
                        deleteAppointment();
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void scheduleAppointment() {
        try {
            System.out.print("Enter patient ID: ");
            String patientId = scanner.nextLine().trim();

            System.out.print("Enter doctor ID: ");
            String doctorId = scanner.nextLine().trim();

            Patient patient = patientService.getPatientById(patientId);
            Doctor doctor = doctorService.getDoctorById(doctorId);

            if (patient == null || doctor == null) {
                System.out.println("Patient or Doctor not found.");
                return;
            }

            System.out.print("Enter appointment date (YYYY-MM-DD HH:MM): ");
            String dateTimeStr = scanner.nextLine().trim();

            LocalDateTime appointmentTime = LocalDateTime.parse(dateTimeStr.replace(" ", "T"));
            Appointment appointment = appointmentService.createAppointment(doctor, patient, appointmentTime);
            System.out.println("Appointment scheduled! ID: " + appointment.getAppointmentId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        System.out.println("\n----- ALL APPOINTMENTS -----");
        appointments.forEach(a -> System.out.println("ID: " + a.getAppointmentId() +
            ", Patient: " + a.getPatient().getName() + ", Doctor: " + a.getDoctor().getName() +
            ", Status: " + a.getStatus()));
    }

    private static void viewAppointmentById() {
        System.out.print("Enter appointment ID: ");
        String appointmentId = scanner.nextLine().trim();
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }
        System.out.println("\n" + appointment.getAppointmentDetails());
    }

    private static void searchAppointments() {
        System.out.println("Search appointments by:");
        System.out.println("1. Patient ID");
        System.out.println("2. Doctor ID");
        System.out.println("3. Status");
        System.out.println("4. Upcoming only");
        System.out.print("Select option: ");
        int choice = getUserIntInput();

        try {
            switch (choice) {
                case 1:
                    System.out.print("Enter patient ID: ");
                    String patientId = scanner.nextLine().trim();
                    List<Appointment> byPatient = appointmentService.getAppointmentsByPatient(patientId);
                    printAppointmentList(byPatient, "Patient appointments");
                    break;
                case 2:
                    System.out.print("Enter doctor ID: ");
                    String doctorId = scanner.nextLine().trim();
                    List<Appointment> byDoctor = appointmentService.getAppointmentsByDoctor(doctorId);
                    printAppointmentList(byDoctor, "Doctor appointments");
                    break;
                case 3:
                    System.out.print("Enter status (PENDING/CONFIRMED/CANCELLED/COMPLETED): ");
                    String statusInput = scanner.nextLine().trim().toUpperCase();
                    AppointmentStatus status = AppointmentStatus.valueOf(statusInput);
                    List<Appointment> byStatus = appointmentService.getAppointmentsByStatus(status);
                    printAppointmentList(byStatus, "Appointments with status " + status);
                    break;
                case 4:
                    printAppointmentList(appointmentService.getUpcomingAppointments(), "Upcoming appointments");
                    break;
                default:
                    System.out.println("Invalid search option.");
            }
        } catch (Exception e) {
            System.out.println("Search error: " + e.getMessage());
        }
    }

    private static void rescheduleAppointment() {
        try {
            System.out.print("Enter appointment ID: ");
            String appointmentId = scanner.nextLine().trim();
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
            if (appointment == null) {
                System.out.println("Appointment not found.");
                return;
            }
            System.out.print("Enter new appointment date (YYYY-MM-DD HH:MM): ");
            String dateTimeStr = scanner.nextLine().trim();
            LocalDateTime newDateTime = LocalDateTime.parse(dateTimeStr.replace(" ", "T"));
            appointmentService.rescheduleAppointment(appointmentId, newDateTime);
            System.out.println("Appointment rescheduled successfully!");
        } catch (Exception e) {
            System.out.println("Error rescheduling appointment: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        try {
            System.out.print("Enter appointment ID: ");
            String appointmentId = scanner.nextLine().trim();
            System.out.print("Enter cancellation reason: ");
            String reason = scanner.nextLine().trim();
            appointmentService.cancelAppointment(appointmentId, reason);
            System.out.println("Appointment cancelled successfully!");
        } catch (Exception e) {
            System.out.println("Error cancelling appointment: " + e.getMessage());
        }
    }

    private static void deleteAppointment() {
        try {
            System.out.print("Enter appointment ID: ");
            String appointmentId = scanner.nextLine().trim();
            boolean deleted = appointmentService.deleteAppointment(appointmentId);
            if (deleted) {
                System.out.println("Appointment deleted successfully!");
            } else {
                System.out.println("Appointment not found.");
            }
        } catch (Exception e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
        }
    }

    private static void printAppointmentList(List<Appointment> appointments, String title) {
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No appointments found for " + title + ".");
            return;
        }
        System.out.println("\n----- " + title.toUpperCase() + " -----");
        appointments.forEach(a -> System.out.println(
            a.getAppointmentId() + " | " + a.getPatient().getName() + " | " + a.getDoctor().getName() +
            " | " + a.getAppointmentDateTime() + " | " + a.getStatus()));
    }

    private static void billingMenu() {
        while (true) {
            System.out.println("\n----- BILLING & PAYMENT -----");
            System.out.println("1. Generate Bill for Appointment");
            System.out.println("2. View All Bills");
            System.out.println("3. Mark Bill as Paid");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");

            int choice = getUserIntInput();
            try {
                switch (choice) {
                    case 1:
                        generateBillForAppointment();
                        break;
                    case 2:
                        viewAllBills();
                        break;
                    case 3:
                        markBillPaid();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void generateBillForAppointment() {
        System.out.print("Enter appointment ID: ");
        String appointmentId = scanner.nextLine().trim();
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        System.out.println("Select billing strategy:");
        System.out.println("1. Standard");
        System.out.println("2. Senior Citizen");
        System.out.println("3. Corporate");
        System.out.print("Choice: ");
        int strategyChoice = getUserIntInput();

        BillingStrategy strategy;
        switch (strategyChoice) {
            case 1:
                strategy = new StandardBillingStrategy();
                break;
            case 2:
                strategy = new SeniorCitizenBillingStrategy();
                break;
            case 3:
                strategy = new CorporateBillingStrategy();
                break;
            default:
                strategy = new StandardBillingStrategy();
                break;
        }

        Bill bill = new Bill(IdGenerator.generateBillId(), appointment, strategy);
        bills.add(bill);
        System.out.println("\n" + bill.generateBill());
    }

    private static void viewAllBills() {
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
            return;
        }
        System.out.println("\n----- ALL BILLS -----");
        for (Bill bill : bills) {
            System.out.println(bill.getPaymentSummary());
        }
    }

    private static void markBillPaid() {
        System.out.print("Enter bill ID: ");
        String billId = scanner.nextLine().trim();
        for (Bill bill : bills) {
            if (bill.getBillId().equalsIgnoreCase(billId)) {
                System.out.print("Enter payment method: ");
                String method = scanner.nextLine().trim();
                bill.processPayment(method.isEmpty() ? "Cash" : method);
                System.out.println("Bill marked as paid. Total: Rs. " + bill.getTotalAmount());
                return;
            }
        }
        System.out.println("Bill not found.");
    }

    private static void searchMenu() {
        while (true) {
            System.out.println("\n----- GLOBAL SEARCH -----");
            System.out.println("1. Search Patients");
            System.out.println("2. Search Doctors");
            System.out.println("3. Search Appointments");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");

            int choice = getUserIntInput();
            try {
                switch (choice) {
                    case 1:
                        searchPatients();
                        break;
                    case 2:
                        searchDoctors();
                        break;
                    case 3:
                        searchAppointments();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void analyticsMenu() {
        System.out.println("\n----- ANALYTICS -----");
        System.out.println("Total Patients: " + patientService.getAllPatients().size());
        System.out.println("Total Doctors: " + doctorService.getAllDoctors().size());
        System.out.println("Total Appointments: " + appointmentService.getAllAppointments().size());
    }

    private static void initializeSampleData() {
        try {
            System.out.println("Initializing sample data...\n");
            
            Patient p1 = patientService.createPatient("Raj Kumar", 35);
            Patient p2 = patientService.createPatient("Priya Singh", 28);
            
            Doctor d1 = doctorService.createDoctor("Dr. Sharma", "Cardiology", 1000);
            Doctor d2 = doctorService.createDoctor("Dr. Gupta", "Neurology", 800);
            
            LocalDateTime now = LocalDateTime.now().plusHours(2);
            Appointment a1 = appointmentService.createAppointment(d1, p1, now);
            Appointment a2 = appointmentService.createAppointment(d2, p2, now.plusDays(1));
            bills.add(new Bill(IdGenerator.generateBillId(), a1, new StandardBillingStrategy()));
            bills.add(new Bill(IdGenerator.generateBillId(), a2, new CorporateBillingStrategy()));
            
            System.out.println("Sample data initialized: 3 Patients, 3 Doctors, 2 Appointments, 2 Bills\n");
        } catch (Exception e) {
            System.out.println("Error initializing sample data: " + e.getMessage());
        }
    }

    private static int getUserIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.print("Invalid input. Please enter a number: ");
            return getUserIntInput();
        }
    }

    private static double getUserDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.print("Invalid input. Please enter a valid number: ");
            return getUserDoubleInput();
        }
    }
}
