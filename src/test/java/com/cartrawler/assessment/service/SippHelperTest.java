package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SippHelperTest {

    @ParameterizedTest
    @CsvSource({
            "MMMM, 0",
            "EEEE, 1",
            "CCCC, 2",
            "XXXX, 3",
            "YYYY, 3",
            "MNOP, 0",
    })
    public void testSippClass(String sippCode, int expectedClass) {
        assertThat(SippHelper.getSippClass(mockCarResult(sippCode))).isEqualTo(expectedClass);
    }

    private CarResult mockCarResult(String sippCode) {
        return new CarResult("Make model", "SUPPLIER", sippCode, 123.45, CarResult.FuelPolicy.FULLEMPTY);
    }
}