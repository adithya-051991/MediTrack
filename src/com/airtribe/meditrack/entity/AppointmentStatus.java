package com.airtribe.meditrack.entity;

/**
 * Enum for appointment status values.
 * Demonstrates Java enums as a type-safe alternative to string constants.
 * Benefits: type safety, can't assign invalid status, iteration support
 */
public enum AppointmentStatus {
    PENDING("Pending - Waiting for confirmation"),
    CONFIRMED("Confirmed - Appointment is scheduled"),
    COMPLETED("Completed - Appointment has been completed"),
    CANCELLED("Cancelled - Appointment was cancelled"),
    RESCHEDULED("Rescheduled - Appointment was rescheduled");
    
    private final String description;
    
    AppointmentStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isActive() {
        return this != CANCELLED && this != COMPLETED;
    }
    
    public boolean canModify() {
        return this == PENDING || this == CONFIRMED;
    }
    
    public static AppointmentStatus fromString(String name) {
        try {
            return AppointmentStatus.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
