package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DateUtil;

import java.time.LocalDateTime;

/**
 * Appointment class representing a doctor-patient appointment.
 * Demonstrates:
 * - Composition (contains references to Doctor and Patient)
 * - Enum usage (AppointmentStatus)
 * - Cloneable for deep copy
 * - Immutable status (using enum instead of string)
 */
public class Appointment implements Cloneable {
    
    private String appointmentId;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;
    private String notes;
    private double consultationFee;
    
    public Appointment(String appointmentId, Doctor doctor, Patient patient,
                      LocalDateTime appointmentDateTime) throws InvalidDataException {
        if (doctor == null || patient == null) {
            throw new InvalidDataException("Doctor and Patient cannot be null");
        }
        
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDateTime = appointmentDateTime;
        this.status = AppointmentStatus.PENDING;
        this.notes = "";
        this.consultationFee = doctor.getConsultationFee();
    }
    
    
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }
    
    public void setAppointmentDateTime(LocalDateTime dateTime) throws InvalidDataException {
        if (status != AppointmentStatus.PENDING && status != AppointmentStatus.CONFIRMED) {
            throw new InvalidDataException(
                "Cannot reschedule appointment with status: " + status,
                "appointmentDateTime",
                dateTime,
                "Appointment must be PENDING or CONFIRMED"
            );
        }
        this.appointmentDateTime = dateTime;
        if (status == AppointmentStatus.CONFIRMED) {
            this.status = AppointmentStatus.RESCHEDULED;
        }
    }
    
    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    
    public void confirm() throws InvalidDataException {
        if (status != AppointmentStatus.PENDING) {
            throw new InvalidDataException(
                "Only PENDING appointments can be confirmed",
                "status",
                status,
                "Appointment status must be PENDING"
            );
        }
        this.status = AppointmentStatus.CONFIRMED;
    }
    
    public void cancel(String reason) throws InvalidDataException {
        if (!status.canModify()) {
            throw new InvalidDataException(
                "Appointment with status " + status + " cannot be cancelled",
                "status",
                status,
                "Status must allow modification"
            );
        }
        this.status = AppointmentStatus.CANCELLED;
        this.notes = "Cancelled: " + reason;
    }
    
    public void complete(String notes) {
        this.status = AppointmentStatus.COMPLETED;
        this.notes = notes;
    }
    
    public boolean isUpcoming() {
        return appointmentDateTime.isAfter(LocalDateTime.now());
    }
    
    public boolean isPast() {
        return appointmentDateTime.isBefore(LocalDateTime.now());
    }
    
    public boolean isToday() {
        return appointmentDateTime.toLocalDate().isEqual(java.time.LocalDate.now());
    }
    
    public String getAppointmentDetails() {
        return String.format(
            "APPOINTMENT DETAILS\n" +
            "ID: %s\n" +
            "Patient: %s (%s)\n" +
            "Doctor: %s (Specialization: %s)\n" +
            "Date & Time: %s\n" +
            "Status: %s\n" +
            "Fee: Rs. %.2f\n" +
            "Notes: %s",
            appointmentId,
            patient.getName(), patient.getId(),
            doctor.getName(), doctor.getSpecialization(),
            DateUtil.formatDateTime(appointmentDateTime),
            status,
            consultationFee,
            notes.isEmpty() ? "No notes" : notes
        );
    }
    
    
    @Override
    public Appointment clone() throws CloneNotSupportedException {
        return (Appointment) super.clone();
    }
    
    public Appointment deepCopy() throws CloneNotSupportedException {
        Appointment copied = this.clone();
        return copied;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Appointment{id='%s', patient='%s', doctor='%s', date=%s, status=%s, fee=%.2f}",
            appointmentId, patient.getName(), doctor.getName(),
            DateUtil.formatDateTime(appointmentDateTime), status, consultationFee
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Appointment that = (Appointment) obj;
        return appointmentId.equals(that.appointmentId);
    }
    
    @Override
    public int hashCode() {
        return appointmentId.hashCode();
    }
}
