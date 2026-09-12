package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.exception.InvalidDataException;

/**
 * Validator utility class for centralized validation logic.
 * Demonstrates encapsulation of validation rules and exception handling.
 */
public class Validator {
    
    public static void validateName(String name) throws InvalidDataException {
        if (name == null) {
            throw new InvalidDataException(
                "Name cannot be null",
                "name",
                null,
                "Non-null String of length 2-100"
            );
        }
        if (name.trim().isEmpty()) {
            throw new InvalidDataException(
                "Name cannot be empty or contain only whitespace",
                "name",
                name,
                "Non-empty String of length 2-100"
            );
        }
        if (name.length() < Constants.MIN_NAME_LENGTH || name.length() > Constants.MAX_NAME_LENGTH) {
            throw new InvalidDataException(
                "Name length must be between " + Constants.MIN_NAME_LENGTH + " and " + Constants.MAX_NAME_LENGTH,
                "name",
                name,
                "String of length 2-100"
            );
        }
    }
    
    public static void validateAge(int age) throws InvalidDataException {
        if (age < Constants.MIN_AGE || age > Constants.MAX_AGE) {
            throw new InvalidDataException(
                "Age must be between " + Constants.MIN_AGE + " and " + Constants.MAX_AGE,
                "age",
                age,
                "Integer between 0-150"
            );
        }
    }
    
    public static void validatePhone(String phone) throws InvalidDataException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidDataException(
                "Phone number cannot be null or empty",
                "phone",
                phone,
                "Non-empty numeric string of length 10-15"
            );
        }
        String numericPhone = phone.replaceAll("[^0-9]", "");
        if (numericPhone.length() < Constants.MIN_PHONE_LENGTH || numericPhone.length() > Constants.MAX_PHONE_LENGTH) {
            throw new InvalidDataException(
                "Phone number must contain " + Constants.MIN_PHONE_LENGTH + "-" + Constants.MAX_PHONE_LENGTH + " digits",
                "phone",
                phone,
                "Numeric string with 10-15 digits"
            );
        }
    }
    
    public static void validateEmail(String email) throws InvalidDataException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException(
                "Email cannot be null or empty",
                "email",
                email,
                "Valid email address (contains @)"
            );
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidDataException(
                "Email must contain @ and domain extension",
                "email",
                email,
                "Valid email address (format: user@domain.com)"
            );
        }
    }
    
    public static void validateAmount(double amount) throws InvalidDataException {
        if (amount < 0) {
            throw new InvalidDataException(
                "Amount cannot be negative",
                "amount",
                amount,
                "Positive number >= 0"
            );
        }
    }
    
    public static void validateSpecialization(String specialization) throws InvalidDataException {
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new InvalidDataException(
                "Specialization cannot be null or empty",
                "specialization",
                specialization,
                "Non-empty string (e.g., Cardiology, Neurology)"
            );
        }
    }
    
    public static void validateId(String id) throws InvalidDataException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidDataException(
                "ID cannot be null or empty",
                "id",
                id,
                "Non-empty alphanumeric string"
            );
        }
    }
    
}
