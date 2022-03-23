package com.cartrawler.assessment.car;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CarResultTest {

    public static Stream<Arguments> verifyEquals() {
        return Stream.of(
                arguments(null, false),
                arguments(new CarResult(null, "supplier", "sipp", 321.00, CarResult.FuelPolicy.FULLEMPTY), false),
                arguments(new CarResult("description", null, "sipp", 321.00, CarResult.FuelPolicy.FULLEMPTY), false),
                arguments(new CarResult("description", "supplier", null, 321.00, CarResult.FuelPolicy.FULLEMPTY), false),
                arguments(new CarResult("description", "supplier", "sipp", 321.00, null), false),
                arguments(new CarResult("description", "supplier", "sipp", 321.00, CarResult.FuelPolicy.FULLEMPTY), true)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void verifyEquals(CarResult b, boolean expected) {
        var a = new CarResult("description", "supplier", "sipp", 123.45, CarResult.FuelPolicy.FULLEMPTY);
        assertThat(a.equals(b)).isEqualTo(expected);
    }

    @Test
    public void verifyHashCode() {
        var a = new CarResult("description", "supplier", "sipp", 123.45, CarResult.FuelPolicy.FULLEMPTY);
        var b = new CarResult("description", "supplier", "sipp", 654.32, CarResult.FuelPolicy.FULLEMPTY);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
    }
}