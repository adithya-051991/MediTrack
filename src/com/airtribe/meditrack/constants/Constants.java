package com.airtribe.meditrack.constants;

/**
 * Central configuration and constants for the MediTrack application.
 * Demonstrates static variables and static blocks for application-wide initialization.
 */
public class Constants {
    
    public static final double TAX_RATE = 0.10;
    public static final double DISCOUNT_RATE = 0.05;
    
    public static final String DATA_DIRECTORY = "data/";
    public static final String PATIENTS_FILE = DATA_DIRECTORY + "patients.csv";
    public static final String DOCTORS_FILE = DATA_DIRECTORY + "doctors.csv";
    public static final String APPOINTMENTS_FILE = DATA_DIRECTORY + "appointments.csv";
    public static final String BILLS_FILE = DATA_DIRECTORY + "bills.csv";
    
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 150;
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MIN_PHONE_LENGTH = 10;
    public static final int MAX_PHONE_LENGTH = 15;
    
    public static final String APP_NAME = "MediTrack";
    public static final String APP_VERSION = "1.0";
    public static final String ORGANIZATION = "Airtribe";
    
    public static final int APPOINTMENT_DURATION_MINUTES = 30;
    public static final double CONSULTATION_FEE_MIN = 100.0;
    public static final double CONSULTATION_FEE_MAX = 5000.0;
    
    static {
        System.out.println("=== " + APP_NAME + " v" + APP_VERSION + " (by " + ORGANIZATION + ") ===");
        System.out.println("Configuration loaded successfully");
        System.out.println("Tax Rate: " + (TAX_RATE * 100) + "%");
    }
    
    private Constants() {
        throw new AssertionError("Constants class cannot be instantiated");
    }
}
