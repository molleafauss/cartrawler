package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class FilterServiceTest {

    @Test
    public void testEnterprise() {
        var result = new FilterService().filter(Arrays.asList(
                mockCar("description", "ENTERPRISE", "MMMM", 123.45, CarResult.FuelPolicy.FULLFULL),
                mockCar("description", "PIPPO", "MMMM", 123.45, CarResult.FuelPolicy.FULLFULL)
        ));
        assertThat(result).hasSize(2);
        validateElement(result.get(0), "ENTERPRISE", "MMMM", 123.45);
        validateElement(result.get(1), "PIPPO", "MMMM", 123.45);
    }

    @Test
    public void testSipp() {
        var result = new FilterService().filter(Arrays.asList(
                mockCar("description", "ENTERPRISE", "XXXX", 123.45, CarResult.FuelPolicy.FULLEMPTY),
                mockCar("description", "ENTERPRISE", "CCCC", 123.45, CarResult.FuelPolicy.FULLFULL),
                mockCar("description", "ENTERPRISE", "EEEE", 123.45, CarResult.FuelPolicy.FULLEMPTY),
                mockCar("description", "ENTERPRISE", "MMMM", 123.45, CarResult.FuelPolicy.FULLFULL)
        ));
        assertThat(result).hasSize(4);
        validateElement(result.get(0), "ENTERPRISE", "MMMM", 123.45);
        validateElement(result.get(1), "ENTERPRISE", "EEEE", 123.45);
        validateElement(result.get(2), "ENTERPRISE", "CCCC", 123.45);
        validateElement(result.get(3), "ENTERPRISE", "XXXX", 123.45);
    }

    @Test
    public void testPrice() {
        var car1 = mockCar("description", "ENTERPRISE", "MMMM", 678.45, CarResult.FuelPolicy.FULLFULL);
        var car2 = mockCar("description", "ENTERPRISE", "MMMM", 123.45, CarResult.FuelPolicy.FULLEMPTY);
        var car3 = mockCar("description", "ENTERPRISE", "CCCC", 679.45, CarResult.FuelPolicy.FULLFULL);
        var car4 = mockCar("description", "ENTERPRISE", "CCCC", 123.45, CarResult.FuelPolicy.FULLEMPTY);

        var result = new FilterService().filter(Arrays.asList(car4, car3, car2, car1));
        assertThat(result).hasSize(4);
        validateElement(result.get(0), "ENTERPRISE", "MMMM", 123.45);
        validateElement(result.get(1), "ENTERPRISE", "MMMM", 678.45);
        validateElement(result.get(2), "ENTERPRISE", "CCCC", 123.45);
        validateElement(result.get(3), "ENTERPRISE", "CCCC", 679.45);
    }

    @Test
    public void testFullSample() {
        var cars = Arrays.asList(
                mockCar("description", "ENTERPRISE", "MMMM", 678.45, CarResult.FuelPolicy.FULLFULL),
                mockCar("description", "CUSTOM", "MMMM", 678.45, CarResult.FuelPolicy.FULLFULL),
                mockCar("description", "CUSTOM", "EEEE", 678.45, CarResult.FuelPolicy.FULLFULL),
                mockCar("description", "CUSTOM", "EEEE", 115.45, CarResult.FuelPolicy.FULLFULL),
                mockCar("description", "ENTERPRISE", "MMMM", 123.45, CarResult.FuelPolicy.FULLEMPTY),
                mockCar("description", "ENTERPRISE", "PPPP", 123.45, CarResult.FuelPolicy.FULLEMPTY)
        );

        var result = new FilterService().filter(cars);
        assertThat(result).hasSize(6);
        validateElement(result.get(0), "ENTERPRISE", "MMMM", 123.45);
        validateElement(result.get(1), "ENTERPRISE", "MMMM", 678.45);
        validateElement(result.get(2), "ENTERPRISE", "PPPP", 123.45);
        validateElement(result.get(3), "CUSTOM", "MMMM", 678.45);
        validateElement(result.get(4), "CUSTOM", "EEEE", 115.45);
        validateElement(result.get(5), "CUSTOM", "EEEE", 678.45);
    }

    private CarResult mockCar(String description, String supplier, String sipp, double price, CarResult.FuelPolicy fuelPolicy) {
        return new CarResult(description, supplier, sipp, price, fuelPolicy);
    }

    private void validateElement(CarResult carResult, String supplier, String sippCode, double price) {
        assertThat(carResult.getSupplierName()).isEqualTo(supplier);
        assertThat(carResult.getSippCode()).isEqualTo(sippCode);
        assertThat(carResult.getRentalCost()).isEqualTo(price);
    }
}