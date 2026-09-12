package com.airtribe.meditrack.billing;

import com.airtribe.meditrack.constants.Constants;

public class StandardBillingStrategy implements BillingStrategy {
    @Override
    public double calculateTax(double baseFee) {
        return baseFee * Constants.TAX_RATE;
    }

    @Override
    public double calculateDiscount(double baseFee) {
        return 0.0;
    }

    @Override
    public double calculateTotal(double baseFee) {
        return baseFee + calculateTax(baseFee) - calculateDiscount(baseFee);
    }
}
