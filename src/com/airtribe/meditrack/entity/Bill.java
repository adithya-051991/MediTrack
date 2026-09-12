package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.billing.BillingStrategy;
import com.airtribe.meditrack.billing.StandardBillingStrategy;
import com.airtribe.meditrack.interfaces.Payable;
import com.airtribe.meditrack.util.DateUtil;

import java.time.LocalDateTime;

/**
 * Bill class representing a medical bill.
 * Demonstrates:
 * - Interface implementation (Payable)
 * - Composition (references to Appointment)
 * - Money calculation with tax and discounts
 * - Payment status tracking
 */
public class Bill implements Payable {
    
    private String billId;
    private Appointment appointment;
    private double baseFee;
    private double taxAmount;
    private double discountAmount;
    private double totalAmount;
    private LocalDateTime billDate;
    private boolean isPaid;
    private String paymentMethod;
    private String notes;
    private BillingStrategy billingStrategy;
    
    public Bill(String billId, Appointment appointment) {
        this(billId, appointment, new StandardBillingStrategy());
    }

    public Bill(String billId, Appointment appointment, BillingStrategy billingStrategy) {
        this.billId = billId;
        this.appointment = appointment;
        this.baseFee = appointment.getConsultationFee();
        this.billDate = LocalDateTime.now();
        this.isPaid = false;
        this.paymentMethod = "";
        this.notes = "";
        this.billingStrategy = billingStrategy == null ? new StandardBillingStrategy() : billingStrategy;
        this.taxAmount = this.billingStrategy.calculateTax(baseFee);
        this.discountAmount = this.billingStrategy.calculateDiscount(baseFee);
        this.totalAmount = this.billingStrategy.calculateTotal(baseFee);
        this.isPaid = false;
    }
    
    
    public String getBillId() {
        return billId;
    }
    
    public Appointment getAppointment() {
        return appointment;
    }
    
    public double getBaseFee() {
        return baseFee;
    }
    
    public double getTaxAmount() {
        return taxAmount;
    }
    
    public double getDiscountAmount() {
        return discountAmount;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BillingStrategy getBillingStrategy() {
        return billingStrategy;
    }

    public void setBillingStrategy(BillingStrategy billingStrategy) {
        this.billingStrategy = billingStrategy == null ? new StandardBillingStrategy() : billingStrategy;
        this.taxAmount = this.billingStrategy.calculateTax(baseFee);
        this.discountAmount = this.billingStrategy.calculateDiscount(baseFee);
        this.totalAmount = this.billingStrategy.calculateTotal(baseFee);
    }
    
    @Override
    public double calculateAmount() {
        return billingStrategy == null ? totalAmount : billingStrategy.calculateTotal(baseFee);
    }
    
    @Override
    public String generateBill() {
        return String.format(
            "╔════════════════════════════════════════════════════════╗\n" +
            "║                   MEDICAL BILL                          ║\n" +
            "╠════════════════════════════════════════════════════════╣\n" +
            "║ Bill ID        : %-47s ║\n" +
            "║ Date           : %-47s ║\n" +
            "╠════════════════════════════════════════════════════════╣\n" +
            "║ Patient        : %-47s ║\n" +
            "║ Doctor         : %-47s ║\n" +
            "║ Specialization : %-47s ║\n" +
            "╠════════════════════════════════════════════════════════╣\n" +
            "║ Base Fee       : Rs. %-45.2f ║\n" +
            "║ Tax            : Rs. %-45.2f ║\n" +
            "║ Discount       : Rs. %-45.2f ║\n" +
            "╠════════════════════════════════════════════════════════╣\n" +
            "║ TOTAL AMOUNT   : Rs. %-45.2f ║\n" +
            "║ Status         : %-47s ║\n" +
            "╠════════════════════════════════════════════════════════╣\n" +
            "║ Payment Method : %-47s ║\n" +
            "║ Notes          : %-47s ║\n" +
            "╚════════════════════════════════════════════════════════╝",
            billId,
            DateUtil.formatDateTime(billDate),
            appointment.getPatient().getName(),
            appointment.getDoctor().getName(),
            appointment.getDoctor().getSpecialization(),
            baseFee,
            taxAmount,
            discountAmount,
            calculateAmount(),
            isPaid ? "PAID" : "PENDING",
            paymentMethod.isEmpty() ? "Not recorded" : paymentMethod,
            notes.isEmpty() ? "None" : notes
        );
    }
    
    @Override
    public double applyDiscount(double discountPercentage) {
        this.discountAmount = (baseFee * discountPercentage) / 100;
        this.totalAmount = baseFee + taxAmount - discountAmount;
        return calculateAmount();
    }
    
    @Override
    public double applyTax(double taxPercentage) {
        this.taxAmount = (baseFee * taxPercentage) / 100;
        this.totalAmount = baseFee + taxAmount - discountAmount;
        return calculateAmount();
    }
    
    @Override
    public boolean isPaymentDue() {
        return !isPaid;
    }
    
    @Override
    public void markPaymentComplete() {
        this.isPaid = true;
        this.paymentMethod = "Online Payment";
    }
    
    
    public void processPayment(String method) {
        this.isPaid = true;
        this.paymentMethod = method;
    }
    
    public String getPaymentSummary() {
        return String.format(
            "Bill: %s | Amount: Rs. %.2f | Status: %s | Date: %s",
            billId,
            calculateAmount(),
            isPaid ? "PAID" : "PENDING",
            DateUtil.formatDateTime(billDate)
        );
    }
    
    @Override
    public String toString() {
        return String.format(
            "Bill{id='%s', amount=%.2f, status=%s, date=%s}",
            billId, calculateAmount(), isPaid ? "PAID" : "PENDING", DateUtil.formatDateTime(billDate)
        );
    }
}
