package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.util.DateUtil;

import java.time.LocalDateTime;

public final class BillSummary {
    
    private final String billId;
    private final String patientName;
    private final String doctorName;
    private final double amount;
    private final boolean isPaid;
    private final LocalDateTime billDate;
    
    public BillSummary(String billId, String patientName, String doctorName,
                       double amount, boolean isPaid, LocalDateTime billDate) {
        if (billId == null || billId.trim().isEmpty()) {
            throw new IllegalArgumentException("Bill ID cannot be null or empty");
        }
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be null or empty");
        }
        if (doctorName == null || doctorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be null or empty");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (billDate == null) {
            throw new IllegalArgumentException("Bill date cannot be null");
        }
        
        this.billId = billId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.amount = amount;
        this.isPaid = isPaid;
        this.billDate = billDate;
    }
    
    
    public String getBillId() {
        return billId;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    
    public String getSummary() {
        return String.format(
            "Bill %s | Patient: %s | Doctor: %s | Amount: Rs. %.2f | Status: %s | Date: %s",
            billId,
            patientName,
            doctorName,
            amount,
            isPaid ? "PAID" : "PENDING",
            DateUtil.formatDateTime(billDate)
        );
    }
    
    public static BillSummary fromBill(Bill bill) {
        return new BillSummary(
            bill.getBillId(),
            bill.getAppointment().getPatient().getName(),
            bill.getAppointment().getDoctor().getName(),
            bill.calculateAmount(),
            bill.isPaid(),
            bill.getBillDate()
        );
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        BillSummary that = (BillSummary) obj;
        return billId.equals(that.billId) &&
               patientName.equals(that.patientName) &&
               doctorName.equals(that.doctorName) &&
               Double.compare(amount, that.amount) == 0 &&
               isPaid == that.isPaid &&
               billDate.equals(that.billDate);
    }
    
    @Override
    public int hashCode() {
        int result = billId.hashCode();
        result = 31 * result + patientName.hashCode();
        result = 31 * result + doctorName.hashCode();
        result = 31 * result + Double.hashCode(amount);
        result = 31 * result + Boolean.hashCode(isPaid);
        result = 31 * result + billDate.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return String.format(
            "BillSummary{billId='%s', patient='%s', doctor='%s', amount=%.2f, paid=%b, date=%s}",
            billId, patientName, doctorName, amount, isPaid, DateUtil.formatDateTime(billDate)
        );
    }
}
