package com.airtribe.meditrack.exception;

/**
 * Custom exception for when an appointment is not found in the system.
 * Demonstrates exception chaining and custom exception handling.
 */
public class AppointmentNotFoundException extends Exception {
    
    private String appointmentId;
    
    public AppointmentNotFoundException(String message) {
        super(message);
        this.appointmentId = null;
    }
    
    public AppointmentNotFoundException(String message, String appointmentId) {
        super(message);
        this.appointmentId = appointmentId;
    }
    
    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.appointmentId = null;
    }
    
    public AppointmentNotFoundException(String message, String appointmentId, Throwable cause) {
        super(message, cause);
        this.appointmentId = appointmentId;
    }
    
    public String getAppointmentId() {
        return appointmentId;
    }
    
    @Override
    public String toString() {
        return "AppointmentNotFoundException{" +
                "appointmentId='" + appointmentId + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
