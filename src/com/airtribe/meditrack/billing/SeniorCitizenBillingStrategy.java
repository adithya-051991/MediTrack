package com.airtribe.meditrack.billing;

public class SeniorCitizenBillingStrategy implements BillingStrategy {
    @Override
    public double calculateTax(double baseFee) {
        return baseFee * 0.05;
    }

    @Override
    public double calculateDiscount(double baseFee) {
        return baseFee * 0.15;
    }

    @Override
    public double calculateTotal(double baseFee) {
        return baseFee + calculateTax(baseFee) - calculateDiscount(baseFee);
    }
}
