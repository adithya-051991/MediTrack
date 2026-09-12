package com.airtribe.meditrack.interfaces;

/**
 * Interface for entities that can be billed/paid.
 * Demonstrates polymorphism - different entities have different billing strategies.
 */
public interface Payable {
    
    double calculateAmount();
    
    String generateBill();
    
    double applyDiscount(double discountPercentage);
    
    double applyTax(double taxPercentage);
    
    boolean isPaymentDue();
    
    void markPaymentComplete();
}
