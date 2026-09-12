package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.exception.*;
import com.airtribe.meditrack.util.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TestRunner - Manual test class for MediTrack.
 */
public class TestRunner {
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("   MediTrack Test Suite         ");
        System.out.println("================================\n");

        testPatientService();
        testDoctorService();
        testAppointmentService();
        testValidation();

        printTestSummary();
    }

    private static void testPatientService() {
        System.out.println("\nTEST SUITE: Patient Service");
        System.out.println("-----------------------------------------------------");
        
        PatientService service = new PatientService();
        try {
            Patient p1 = service.createPatient("John Doe", 35);
            assert p1 != null : "Patient creation failed";
            assert p1.getName().equals("John Doe") : "Patient name mismatch";
            
            Patient retrieved = service.getPatientById(p1.getId());
            assert retrieved != null : "Patient retrieval failed";
            
            pass("OK Patient creation and retrieval");
        } catch (Exception e) {
            fail("FAIL Patient operations: " + e.getMessage());
        }

        try {
            Patient p1 = service.createPatient("Alice", 30);
            Patient p2 = service.createPatient("Bob", 30);
            
            List<Patient> byAge = service.searchPatient(30);
            assert byAge.size() >= 2 : "Patient search by age failed";
            
            pass("OK Patient search operations");
        } catch (Exception e) {
            fail("FAIL Patient search: " + e.getMessage());
        }

        try {
            List<Patient> all = service.getAllPatients();
            assert all != null : "Get all patients returned null";
            
            pass("OK Get all patients");
        } catch (Exception e) {
            fail("FAIL Get all patients: " + e.getMessage());
        }
    }

    private static void testDoctorService() {
        System.out.println("\nTEST SUITE: Doctor Service");
        System.out.println("-----------------------------------------------------");
        
        DoctorService service = new DoctorService();
        try {
            Doctor d1 = service.createDoctor("Dr. Smith", "Cardiology", 1000);
            assert d1 != null : "Doctor creation failed";
            assert d1.getSpecialization().equals("Cardiology") : "Specialization mismatch";
            
            Doctor retrieved = service.getDoctorById(d1.getId());
            assert retrieved != null : "Doctor retrieval failed";
            
            pass("OK Doctor creation and retrieval");
        } catch (Exception e) {
            fail("FAIL Doctor operations: " + e.getMessage());
        }

        try {
            Doctor d1 = service.createDoctor("Dr. A", "Cardiology", 1000);
            Doctor d2 = service.createDoctor("Dr. B", "Cardiology", 1200);
            Doctor d3 = service.createDoctor("Dr. C", "Neurology", 800);
            
            List<Doctor> cardiologists = service.searchDoctor("Cardiology", true);
            assert cardiologists.size() >= 2 : "Specialization search failed";
            
            pass("OK Doctor search by specialization");
        } catch (Exception e) {
            fail("FAIL Doctor search: " + e.getMessage());
        }

        try {
            List<Doctor> all = service.getAllDoctors();
            assert all != null : "Get all doctors returned null";
            
            pass("OK Get all doctors");
        } catch (Exception e) {
            fail("FAIL Get all doctors: " + e.getMessage());
        }
    }

    private static void testAppointmentService() {
        System.out.println("\nTEST SUITE: Appointment Service");
        System.out.println("-----------------------------------------------------");
        
        try {
            PatientService patientService = new PatientService();
            DoctorService doctorService = new DoctorService();
            AppointmentService appointmentService = new AppointmentService();
            
            Patient p1 = patientService.createPatient("Patient1", 30);
            Doctor d1 = doctorService.createDoctor("Dr. Test", "General", 500);
            
            LocalDateTime time = LocalDateTime.now().plusDays(1);
            Appointment apt = appointmentService.createAppointment(d1, p1, time);
            
            assert apt != null : "Appointment creation failed";
            assert apt.getStatus() == AppointmentStatus.PENDING : "Appointment status should be PENDING";
            
            pass("OK Appointment scheduling");
        } catch (Exception e) {
            fail("FAIL Appointment scheduling: " + e.getMessage());
        }

        try {
            AppointmentService service = new AppointmentService();
            List<Appointment> all = service.getAllAppointments();
            assert all != null : "Get all appointments returned null";
            
            pass("OK Get all appointments");
        } catch (Exception e) {
            fail("FAIL Get all appointments: " + e.getMessage());
        }
    }

    private static void testValidation() {
        System.out.println("\nTEST SUITE: Validation");
        System.out.println("-----------------------------------------------------");
        
        try {
            Validator.validateEmail("test@example.com");
            pass("OK Valid email");
        } catch (Exception e) {
            fail("FAIL Valid email rejected");
        }

        try {
            Validator.validateEmail("invalid-email");
            fail("FAIL Invalid email should throw exception");
        } catch (InvalidDataException e) {
            pass("OK Invalid email validation");
        }
    }

    private static void pass(String message) {
        System.out.println(message);
        testsPassed++;
    }

    private static void fail(String message) {
        System.out.println(message);
        testsFailed++;
    }

    private static void printTestSummary() {
        System.out.println("\n================================");
        System.out.println("   TEST EXECUTION SUMMARY       ");
        System.out.println("================================");
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        
        if (testsFailed == 0) {
            System.out.println("\nALL TESTS PASSED!");
        }
    }
}
