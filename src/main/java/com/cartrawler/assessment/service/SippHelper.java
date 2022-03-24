package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;

/**
 * Helper to determine SIPP code "class" based on first letter of SIPP code.
 */
final class SippHelper {

    private SippHelper() {}

    public static int getSippClass(CarResult car) {
        switch (car.getSippCode().charAt(0)) {
            case 'M':
                return 0;
            case 'E':
                return 1;
            case 'C':
                return 2;
            default:
                return 3;
        }
    }
}
