package com.cartrawler.assessment.service;

import java.util.ArrayList;

class PriceBucket {
    private final int key;
    private final ArrayList<Double> prices;
    private Double median;

    public PriceBucket(int key) {
        this.key = key;
        this.prices = new ArrayList<>();
        this.median = 0.0;
    }

    public void calculateMedian() {
        if (prices.size() == 0) {
            // no values - don't change from zero
            return;
        }
        if ((prices.size() + 1) % 2 == 0) {
            // there's an even number of prices, find the entry exactly in the middle (remember 0-index)
            median = prices.get((prices.size() + 1) / 2 - 1);
        } else {
            // prices are odd in number - median is the average of the two - division yields 0.5, which rounds down as integer
            var mid_index = (prices.size() + 1) / 2 - 1;
            median = (prices.get(mid_index) + prices.get(mid_index + 1)) / 2;
        }
    }

    public void addPrice(double rentalCost) {
        prices.add(rentalCost);
    }

    public Double getMedianPrice() {
        return median;
    }
}
