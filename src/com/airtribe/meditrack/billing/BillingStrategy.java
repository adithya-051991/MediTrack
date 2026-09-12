package com.airtribe.meditrack.billing;

public interface BillingStrategy {
    double calculateTax(double baseFee);
    double calculateDiscount(double baseFee);
    double calculateTotal(double baseFee);
}
