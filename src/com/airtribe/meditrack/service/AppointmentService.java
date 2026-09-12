package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AppointmentService class for managing appointment-related operations.
 * Demonstrates:
 * - Service layer pattern
 * - CRUD operations with custom exceptions
 * - Exception chaining and handling
 * - Complex filtering with streams
 * - Status management with enums
 */
public class AppointmentService {
    
    private DataStore<Appointment> appointmentStore;
    
    public AppointmentService() {
        this.appointmentStore = new DataStore<>("AppointmentStore");
    }
    
    public void addAppointment(Appointment appointment) throws InvalidDataException {
        if (appointment == null) {
            throw new InvalidDataException("Appointment cannot be null");
        }
        appointmentStore.add(appointment.getAppointmentId(), appointment);
    }
    
    public Appointment createAppointment(com.airtribe.meditrack.entity.Doctor doctor,
                                        com.airtribe.meditrack.entity.Patient patient,
                                        LocalDateTime dateTime) throws InvalidDataException {
        String appointmentId = IdGenerator.generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, doctor, patient, dateTime);
        addAppointment(appointment);
        return appointment;
    }
    
    public Appointment getAppointmentById(String appointmentId) {
        return appointmentStore.get(appointmentId);
    }
    
    public Appointment getAppointmentByIdOrThrow(String appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = appointmentStore.get(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException(
                "Appointment not found with ID: " + appointmentId,
                appointmentId
            );
        }
        return appointment;
    }
    
    public void confirmAppointment(String appointmentId) throws AppointmentNotFoundException, InvalidDataException {
        Appointment appointment = getAppointmentByIdOrThrow(appointmentId);
        appointment.confirm();
    }
    
    public void cancelAppointment(String appointmentId, String reason) 
            throws AppointmentNotFoundException, InvalidDataException {
        Appointment appointment = getAppointmentByIdOrThrow(appointmentId);
        appointment.cancel(reason);
    }
    
    public void completeAppointment(String appointmentId, String notes) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentByIdOrThrow(appointmentId);
        appointment.complete(notes);
    }
    
    public void rescheduleAppointment(String appointmentId, LocalDateTime newDateTime) 
            throws AppointmentNotFoundException, InvalidDataException {
        Appointment appointment = getAppointmentByIdOrThrow(appointmentId);
        appointment.setAppointmentDateTime(newDateTime);
    }
    
    public boolean deleteAppointment(String appointmentId) {
        return appointmentStore.remove(appointmentId) != null;
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }
    
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentStore.getAll().stream()
            .filter(a -> a.getPatient().getId().equals(patientId))
            .collect(Collectors.toList());
    }
    
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentStore.getAll().stream()
            .filter(a -> a.getDoctor().getId().equals(doctorId))
            .collect(Collectors.toList());
    }
    
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentStore.getAll().stream()
            .filter(a -> a.getStatus() == status)
            .collect(Collectors.toList());
    }
    
    public List<Appointment> getUpcomingAppointments() {
        return appointmentStore.getAll().stream()
            .filter(Appointment::isUpcoming)
            .sorted(Comparator.comparing(Appointment::getAppointmentDateTime))
            .collect(Collectors.toList());
    }
    
}
