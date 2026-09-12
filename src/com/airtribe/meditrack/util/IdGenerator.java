package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * IdGenerator utility for generating unique IDs across the application.
 * Demonstrates static variables, atomic operations, and Singleton pattern.
 */
public class IdGenerator {
    
    private static final AtomicInteger patientIdCounter = new AtomicInteger(1000);
    private static final AtomicInteger doctorIdCounter = new AtomicInteger(2000);
    private static final AtomicInteger appointmentIdCounter = new AtomicInteger(3000);
    private static final AtomicInteger billIdCounter = new AtomicInteger(4000);
    
    private static IdGenerator instance;
    
    private IdGenerator() {
    }
    
    public static synchronized IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }
    
    public static String generatePatientId() {
        return "PAT-" + patientIdCounter.incrementAndGet();
    }
    
    public static String generateDoctorId() {
        return "DOC-" + doctorIdCounter.incrementAndGet();
    }
    
    public static String generateAppointmentId() {
        return "APT-" + appointmentIdCounter.incrementAndGet();
    }
    
    public static String generateBillId() {
        return "BILL-" + billIdCounter.incrementAndGet();
    }
    
    public static void resetCounters() {
        patientIdCounter.set(1000);
        doctorIdCounter.set(2000);
        appointmentIdCounter.set(3000);
        billIdCounter.set(4000);
    }
    
    public static int getPatientIdCounter() {
        return patientIdCounter.get();
    }
    
    public static int getDoctorIdCounter() {
        return doctorIdCounter.get();
    }
    
    public static int getAppointmentIdCounter() {
        return appointmentIdCounter.get();
    }
    
    public static int getBillIdCounter() {
        return billIdCounter.get();
    }
}
