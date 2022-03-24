package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;

import java.util.Set;

/**
 * helper to determine if cars have an enterprise supplier or not
 */
final class SupplierHelper {

    private static final Set<String> ENTERPRISE_SUPPLIERS = Set.of("AVIS", "BUDGET", "ENTERPRISE", "FIREFLY", "HERTZ", "SIXT", "THRIFTY");

    private SupplierHelper() {}

    public static boolean isEnterprise(CarResult car) {
        return ENTERPRISE_SUPPLIERS.contains(car.getSupplierName());
    }
}
