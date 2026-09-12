package com.airtribe.meditrack.exception;

/**
 * Custom exception for invalid data encountered during operations.
 * Demonstrates exception chaining and provides field-level error information.
 */
public class InvalidDataException extends Exception {
    
    private String fieldName;
    private Object invalidValue;
    private String expectedFormat;
    
    public InvalidDataException(String message) {
        super(message);
    }
    
    public InvalidDataException(String message, String fieldName, Object invalidValue) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
    
    public InvalidDataException(String message, String fieldName, Object invalidValue, String expectedFormat) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
        this.expectedFormat = expectedFormat;
    }
    
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidDataException(String message, String fieldName, Object invalidValue, 
                               String expectedFormat, Throwable cause) {
        super(message, cause);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
        this.expectedFormat = expectedFormat;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public Object getInvalidValue() {
        return invalidValue;
    }
    
    public String getExpectedFormat() {
        return expectedFormat;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("InvalidDataException{");
        sb.append("message='").append(getMessage()).append('\'');
        if (fieldName != null) {
            sb.append(", field='").append(fieldName).append('\'');
        }
        if (invalidValue != null) {
            sb.append(", invalidValue='").append(invalidValue).append('\'');
        }
        if (expectedFormat != null) {
            sb.append(", expectedFormat='").append(expectedFormat).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}
