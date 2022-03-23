package com.cartrawler.assessment.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CarResult {
    private final String description;
    private final String supplierName;
    private final String sippCode;
    private final double rentalCost;
    private final FuelPolicy fuelPolicy;
    
	public enum FuelPolicy {
        FULLFULL,
        FULLEMPTY}
    
    public CarResult(String description, String supplierName, String sipp, double cost, FuelPolicy fuelPolicy) {
        this.description = description;
        this.supplierName = supplierName;
        this.sippCode = sipp;
        this.rentalCost = cost;
        this.fuelPolicy = fuelPolicy;
    }
    
    public String getDescription() {
        return this.description;        
    }
    
    public String getSupplierName() {
        return this.supplierName;        
    }
    
    public String getSippCode() {
        return this.sippCode;        
    }
    
    public double getRentalCost() {
        return this.rentalCost;        
    }
    
    public FuelPolicy getFuelPolicy() {
        return this.fuelPolicy;
    }
    
    public String toString() {
        return this.supplierName + " : " +
            this.description + " : " +
            this.sippCode + " : " +
            this.rentalCost + " : " +
            this.fuelPolicy;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.description)
                .append(this.supplierName)
                .append(this.sippCode)
                .append(this.fuelPolicy)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CarResult)) {
            return false;
        }
        CarResult otherCar = (CarResult) obj;
        return new EqualsBuilder()
                .setTestRecursive(false)
                .append(this.description, otherCar.description)
                .append(this.supplierName, otherCar.supplierName)
                .append(this.sippCode, otherCar.sippCode)
                .append(this.fuelPolicy, otherCar.fuelPolicy)
                .isEquals();
    }
}
