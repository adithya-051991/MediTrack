package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Doctor class extending Person.
 * Demonstrates:
 * - Inheritance (extends Person)
 * - Encapsulation (private specialization and fee fields)
 * - Interface implementation (Searchable)
 * - Method overriding
 * - Polymorphism
 */
public class Doctor extends Person implements Searchable {
    
    private String specialization;
    private double consultationFee;
    private int yearsOfExperience;
    private boolean isAvailable;
    
    public Doctor(String id, String name, int age, String specialization, double consultationFee, int yearsOfExperience) 
            throws InvalidDataException {
        super(id, name, age);
        
        Validator.validateSpecialization(specialization);
        Validator.validateAmount(consultationFee);
        
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.yearsOfExperience = yearsOfExperience;
        this.isAvailable = true;
    }
    
    public Doctor(String id, String name, String specialization, double consultationFee) 
            throws InvalidDataException {
        super(id, name, 25);
        
        Validator.validateSpecialization(specialization);
        Validator.validateAmount(consultationFee);
        
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.yearsOfExperience = 0;
        this.isAvailable = true;
    }
    
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) throws InvalidDataException {
        Validator.validateSpecialization(specialization);
        this.specialization = specialization;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) throws InvalidDataException {
        Validator.validateAmount(consultationFee);
        this.consultationFee = consultationFee;
    }
    
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    
    @Override
    public String getEntityType() {
        return "DOCTOR";
    }
    
    @Override
    public String getDescription() {
        return String.format("%s - Specialist in %s (Exp: %d years)", 
                           getName(), specialization, yearsOfExperience);
    }
    
    
    @Override
    public boolean searchById(String id) {
        return getId().equalsIgnoreCase(id);
    }
    
    @Override
    public boolean searchByName(String name) {
        return getName().equalsIgnoreCase(name);
    }
    
    @Override
    public List<?> search(String criteria) {
        List<Doctor> results = new ArrayList<>();
        
        if (getId().contains(criteria)) {
            results.add(this);
        }
        else if (getName().toLowerCase().contains(criteria.toLowerCase())) {
            results.add(this);
        }
        else if (getSpecialization().toLowerCase().contains(criteria.toLowerCase())) {
            results.add(this);
        }
        
        return results;
    }
    
    @Override
    public String[] getSearchableFields() {
        return new String[]{"ID", "Name", "Specialization"};
    }
    
    
    public String prescribe(String medicine) {
        return String.format("Dr. %s prescribes: %s", getName(), medicine);
    }
    
    public String prescribe(String medicine, String dosage) {
        return String.format("Dr. %s prescribes: %s - Dosage: %s", getName(), medicine, dosage);
    }
    
    public String generateConsultationBill() {
        return String.format(
            "CONSULTATION BILL\nDoctor: %s\nSpecialization: %s\nFee: Rs. %.2f",
            getName(), specialization, consultationFee
        );
    }
    
    public String getExperienceSummary() {
        return String.format("Dr. %s has %d years of experience in %s",
                           getName(), yearsOfExperience, specialization);
    }
    
    @Override
    public String toString() {
        return String.format(
            "Doctor{id='%s', name='%s', specialization='%s', fee=%.2f, years=%d, available=%b}",
            getId(), getName(), specialization, consultationFee, yearsOfExperience, isAvailable
        );
    }
}
