package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SupplierHelperTest {

    @ParameterizedTest
    @CsvSource({
            "AVIS, true",
            "BUDGET, true",
            "ENTERPRISE, true",
            "FIREFLY, true",
            "HERTZ, true",
            "SIXT, true",
            "XYZ, false",
    })
    public void testIsEnterprise(String supplier, boolean expected) {
        assertThat(SupplierHelper.isEnterprise(mockCarResult(supplier))).isEqualTo(expected);
    }

    private CarResult mockCarResult(String supplier) {
        return new CarResult("Make / model", supplier, "ABCD", 12.34, CarResult.FuelPolicy.FULLEMPTY);
    }
}