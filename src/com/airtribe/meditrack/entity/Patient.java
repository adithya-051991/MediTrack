package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Patient class extending Person.
 * Demonstrates:
 * - Inheritance (extends Person)
 * - Interface implementation (Searchable)
 * - Cloneable for deep/shallow copy semantics
 * - Medical history tracking
 * - Method overriding
 */
public class Patient extends Person implements Searchable, Cloneable {
    
    private String medicalHistory;
    private String bloodType;
    private boolean hasInsurance;
    private List<String> allergies;
    
    public Patient(String id, String name, int age, String bloodType, boolean hasInsurance) throws InvalidDataException {
        super(id, name, age);
        
        this.bloodType = bloodType;
        this.hasInsurance = hasInsurance;
        this.medicalHistory = "";
        this.allergies = new ArrayList<>();
    }
    
    public Patient(String id, String name, int age) throws InvalidDataException {
        super(id, name, age);
        
        this.bloodType = "O+";
        this.hasInsurance = false;
        this.medicalHistory = "";
        this.allergies = new ArrayList<>();
    }
    
    
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public String getBloodType() {
        return bloodType;
    }
    
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
    
    public boolean hasInsurance() {
        return hasInsurance;
    }
    
    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }
    
    public List<String> getAllergies() {
        return new ArrayList<>(allergies);
    }
    
    public void addAllergy(String allergy) {
        if (!allergies.contains(allergy)) {
            allergies.add(allergy);
        }
    }
    
    public void removeAllergy(String allergy) {
        allergies.remove(allergy);
    }
    
    @Override
    public String getEntityType() {
        return "PATIENT";
    }
    
    @Override
    public String getDescription() {
        return String.format("%s (Age: %d, Blood Type: %s, Insurance: %s)",
                           getName(), getAge(), bloodType, hasInsurance ? "Yes" : "No");
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
        List<Patient> results = new ArrayList<>();
        
        if (getId().contains(criteria) || 
            getName().toLowerCase().contains(criteria.toLowerCase()) ||
            bloodType.contains(criteria)) {
            results.add(this);
        }
        
        return results;
    }
    
    @Override
    public String[] getSearchableFields() {
        return new String[]{"ID", "Name", "Blood Type"};
    }
    
    
    @Override
    public Patient clone() throws CloneNotSupportedException {
        return (Patient) super.clone();
    }
    
    public Patient deepCopy() throws CloneNotSupportedException {
        Patient copied = this.clone();
        
        copied.allergies = new ArrayList<>(this.allergies);
        
        return copied;
    }
    
    
    @Override
    public String toString() {
        return String.format(
            "Patient{id='%s', name='%s', age=%d, bloodType='%s', insurance=%b, allergies=%d}",
            getId(), getName(), getAge(), bloodType, hasInsurance, allergies.size()
        );
    }
}
