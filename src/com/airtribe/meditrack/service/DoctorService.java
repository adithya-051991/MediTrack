package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DoctorService class for managing doctor-related operations.
 * Demonstrates:
 * - Service layer pattern
 * - CRUD operations for doctors
 * - Filtering and searching with streams
 * - Method overloading for different search criteria
 */
public class DoctorService {
    
    private DataStore<Doctor> doctorStore;
    
    public DoctorService() {
        this.doctorStore = new DataStore<>("DoctorStore");
    }
    
    public void addDoctor(Doctor doctor) throws InvalidDataException {
        if (doctor == null) {
            throw new InvalidDataException("Doctor cannot be null");
        }
        doctorStore.add(doctor.getId(), doctor);
    }
    
    public Doctor createDoctor(String name, String specialization, double consultationFee) 
            throws InvalidDataException {
        String doctorId = IdGenerator.generateDoctorId();
        Doctor doctor = new Doctor(doctorId, name, specialization, consultationFee);
        addDoctor(doctor);
        return doctor;
    }
    
    public Doctor getDoctorById(String doctorId) {
        return doctorStore.get(doctorId);
    }
    
    public List<Doctor> searchDoctor(String name) {
        return doctorStore.getAll().stream()
            .filter(d -> d.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public List<Doctor> searchDoctor(String specialization, boolean isSpecialization) {
        if (!isSpecialization) {
            return searchDoctor(specialization);
        }
        return doctorStore.getAll().stream()
            .filter(d -> d.getSpecialization().toLowerCase().contains(specialization.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public List<Doctor> searchDoctor(String fieldName, String value) {
        return doctorStore.getAll().stream()
            .filter(d -> matches(d, fieldName, value))
            .collect(Collectors.toList());
    }
    
    public boolean updateDoctor(Doctor doctor) {
        if (doctorStore.contains(doctor.getId())) {
            doctorStore.update(doctor.getId(), doctor);
            return true;
        }
        return false;
    }
    
    public boolean deleteDoctor(String doctorId) {
        return doctorStore.remove(doctorId) != null;
    }
    
    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }
    
    private boolean matches(Doctor doctor, String fieldName, String value) {
        switch (fieldName.toLowerCase()) {
            case "name":
                return doctor.getName().toLowerCase().contains(value.toLowerCase());
            case "specialization":
                return doctor.getSpecialization().toLowerCase().contains(value.toLowerCase());
            case "fee":
                try {
                    return doctor.getConsultationFee() == Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }
}
