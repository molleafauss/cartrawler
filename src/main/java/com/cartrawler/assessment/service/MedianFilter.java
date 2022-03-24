package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MedianFilter {

    private final Map<Integer, PriceBucket> priceBuckets;

    public MedianFilter() {
        priceBuckets = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            priceBuckets.put(i, new PriceBucket(i));
        }
    }

    /**
     * Filters the given car collection removing the FULLFULL cars which are priced above median.
     * @param cars
     *      the list of cars to filter
     * @return
     *      the filtered list
     */
    public Collection<CarResult> filter(Collection<CarResult> cars) {
        cars.forEach(this::buildBuckets);
        calculateMedianPrices();
        return cars.stream().filter(this::isAcceptablePrice).collect(Collectors.toList());
    }

    private void calculateMedianPrices() {
        priceBuckets.values().forEach(PriceBucket::calculateMedian);
    }

    /**
     * for every car, find the bucket based on its group (supplier & sipp code) and memorize the price so that we can
     * calculate the median price.
     * @param car the car examined
     */
    private void buildBuckets(CarResult car) {
        priceBuckets.get(findBucket(car)).addPrice(car.getRentalCost());
    }

    private boolean isAcceptablePrice(CarResult car) {
        if (car.getFuelPolicy() != CarResult.FuelPolicy.FULLFULL) {
            return true;
        }
        return car.getRentalCost() < priceBuckets.get(findBucket(car)).getMedianPrice();
    }

    private int findBucket(CarResult car) {
        boolean isEnterprise = SupplierHelper.isEnterprise(car);
        int sippCode = SippHelper.getSippClass(car);
        return sippCode + (isEnterprise ? 0 : 4);
    }

}
