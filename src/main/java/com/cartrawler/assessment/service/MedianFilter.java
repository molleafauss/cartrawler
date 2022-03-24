package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;

import java.util.Collection;

public class MedianFilter {
    /**
     * Filters the given car collection removing the FULLFULL cars which are priced above median.
     * The list is expected to be sorted by groups as per `CarSorter`
     * @param cars
     *      the list of cars to filter
     * @return
     *      the filtered list
     */
    public Collection<CarResult> filter(Collection<CarResult> cars) {
        return cars;
    }
}
