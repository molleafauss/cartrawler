package com.cartrawler.assessment.service;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PriceBucketTest {

    @Test
    public void testNoPrices() {
        assertThat(mockPriceBucket().getMedianPrice()).isEqualTo(0.0);
    }

    @Test
    public void onePricesIsMedian() {
        assertThat(mockPriceBucket(12.34).getMedianPrice()).isEqualTo(12.34);
    }

    @Test
    public void twoPricesMedianIsAverage() {
        assertThat(mockPriceBucket(12.00, 14.00).getMedianPrice()).isEqualTo(13.00);
    }

    @Test
    public void testThreePrices() {
        assertThat(mockPriceBucket(1.00, 12.00, 180.00).getMedianPrice()).isEqualTo(12.00);
    }

    private PriceBucket mockPriceBucket(double... prices) {
        var bucket = new PriceBucket(0);
        for (var price : prices) {
            bucket.addPrice(price);
        }
        bucket.calculateMedian();
        return bucket;
    }
}