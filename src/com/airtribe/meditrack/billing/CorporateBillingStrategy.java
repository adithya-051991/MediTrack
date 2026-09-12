package com.airtribe.meditrack.billing;

public class CorporateBillingStrategy implements BillingStrategy {
    @Override
    public double calculateTax(double baseFee) {
        return 0.0;
    }

    @Override
    public double calculateDiscount(double baseFee) {
        return baseFee * 0.20;
    }

    @Override
    public double calculateTotal(double baseFee) {
        return baseFee + calculateTax(baseFee) - calculateDiscount(baseFee);
    }
}
