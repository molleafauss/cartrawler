package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bulk of the logic that performs the filtering of the CarResult list as per specs:
 * * Sort the list so that all corporate cars appear at the top. Note corporate cars are those supplied
 * by AVIS, BUDGET, ENTERPRISE, FIREFLY, HERTZ, SIXT, THRIFTY.
 * * Within both the corporate and non-corporate groups, sort the cars into “mini”, “economy”,
 * “compact” and “other” based on SIPP beginning with M, E, C respectively.
 * * Within each group sort low-to-high on price.
 */
public class CarSorter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSorter.class);

    public List<CarResult> sort(Collection<CarResult> cars) {
        return cars.stream()
                .sorted(Comparator.comparingInt(this::getSortScore))
                .collect(Collectors.toList());
    }

    /**
     * Calculate a sort score using the components required, supplier, sipp code and price.
     * Tries to fit the score on the int, "fitting" the price into an int (by multiplying it by 100), then adding the
     * scores coming from the enterprise and sipp values. Enterprise has 2 possible values and SIPP 4, so we can try to
     * sum the 4*2=8 possibilities and shift them into the highest bit of an int, this leaves 24 bits for the price, which
     * would allow prices up to 2^24/100 ~= 167000 - "should" be enough (if prices are in euro/dollars).
     * @param car
     *      the car to score
     * @return
     *      the sort score calculated as above
     */
    private int getSortScore(CarResult car) {
        int enterpriseScore = SupplierHelper.isEnterprise(car) ? 0 : 1;

        int sippScore = SippHelper.getSippClass(car);

        int priceScore = (int) (car.getRentalCost() * 100);

        // shift enterprise and sipp score into the highest byte
        int score = ((enterpriseScore << 2) + sippScore) << 24;
        // add price score
        score += priceScore;
        LOGGER.debug("Score for {}: {} [{} {} {}]", car, score, enterpriseScore, sippScore, priceScore);
        return score;
    }
}
