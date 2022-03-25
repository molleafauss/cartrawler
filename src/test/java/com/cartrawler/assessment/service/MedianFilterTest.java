package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class MedianFilterTest {

    @Test
    public void emptyListReturnsEmpty() {
        Collection<CarResult> input = Collections.emptyList();

        var cars = new MedianFilter().filter(input);

        assertThat(cars).isEmpty();
    }

    @Test
    public void fullEmptyPoilcyIsntFiltered() {
        var input = Arrays.asList(
                new CarResult("description", "SUPPLIER", "MMMM", 10.00, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("description", "SUPPLIER", "MMMM", 20.00, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("description", "SUPPLIER", "MMMM", 30.00, CarResult.FuelPolicy.FULLEMPTY)
        );

        var cars = new MedianFilter().filter(input);

        assertThat(cars).hasSameSizeAs(input);
    }

    @Test
    public void testFiltering() {
        var car1 = new CarResult("description", "SUPPLIER", "MMMM", 10.00, CarResult.FuelPolicy.FULLEMPTY);
        var car2 = new CarResult("description", "SUPPLIER", "MMMM", 20.00, CarResult.FuelPolicy.FULLFULL);
        var car3 = new CarResult("description", "SUPPLIER", "MMMM", 30.00, CarResult.FuelPolicy.FULLFULL);
        var input = Arrays.asList(car1, car2, car3);

        var cars = new MedianFilter().filter(input);

        // last one removed
        assertThat(cars).hasSize(2);
        assertThat(cars).noneMatch(car -> car.getRentalCost() == 30.00 && car.getFuelPolicy() == CarResult.FuelPolicy.FULLFULL);
    }
}