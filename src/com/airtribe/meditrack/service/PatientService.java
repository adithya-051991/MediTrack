package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PatientService class for managing patient-related operations.
 * Demonstrates:
 * - Service layer pattern
 * - CRUD operations
 * - Generic DataStore usage
 * - Stream API and lambdas
 * - Method overloading
 */
public class PatientService {
    
    private DataStore<Patient> patientStore;
    
    public PatientService() {
        this.patientStore = new DataStore<>("PatientStore");
    }
    
    public void addPatient(Patient patient) throws InvalidDataException {
        if (patient == null) {
            throw new InvalidDataException("Patient cannot be null");
        }
        patientStore.add(patient.getId(), patient);
    }
    
    public Patient createPatient(String name, int age) throws InvalidDataException {
        String patientId = IdGenerator.generatePatientId();
        Patient patient = new Patient(patientId, name, age);
        addPatient(patient);
        return patient;
    }
    
    public Patient getPatientById(String patientId) {
        return patientStore.get(patientId);
    }
    
    public List<Patient> searchPatient(String name) {
        return patientStore.getAll().stream()
            .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    public List<Patient> searchPatient(int age) {
        return patientStore.getAll().stream()
            .filter(p -> p.getAge() == age)
            .collect(Collectors.toList());
    }
    
    public List<Patient> searchPatient(String fieldName, String value) {
        return patientStore.getAll().stream()
            .filter(p -> matches(p, fieldName, value))
            .collect(Collectors.toList());
    }
    
    public boolean updatePatient(Patient patient) {
        if (patientStore.contains(patient.getId())) {
            patientStore.update(patient.getId(), patient);
            return true;
        }
        return false;
    }
    
    public boolean deletePatient(String patientId) {
        return patientStore.remove(patientId) != null;
    }
    
    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }
    
    private boolean matches(Patient patient, String fieldName, String value) {
        switch (fieldName.toLowerCase()) {
            case "name":
                return patient.getName().toLowerCase().contains(value.toLowerCase());
            case "bloodtype":
                return patient.getBloodType().equalsIgnoreCase(value);
            case "age":
                try {
                    return patient.getAge() == Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }
}
