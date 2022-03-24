package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Bulk of the logic that performs the filtering of the CarResult list as per specs:
 * * Sort the list so that all corporate cars appear at the top. Note corporate cars are those supplied
 * by AVIS, BUDGET, ENTERPRISE, FIREFLY, HERTZ, SIXT, THRIFTY.
 * * Within both the corporate and non-corporate groups, sort the cars into “mini”, “economy”,
 * “compact” and “other” based on SIPP beginning with M, E, C respectively.
 * * Within each group sort low-to-high on price.
 */
public class FilterService implements Comparator<CarResult> {

    static Set<String> ENTERPRISE_SUPPLIERS = Set.of("AVIS", "BUDGET", "ENTERPRISE", "FIREFLY", "HERTZ", "SIXT", "THRIFTY");
    static Logger LOGGER = LoggerFactory.getLogger(FilterService.class);

    public List<CarResult> filter(Collection<CarResult> cars) {
        return cars.stream().sorted(this).collect(Collectors.toList());
    }

    @Override
    public int compare(CarResult car1, CarResult car2) {
        return getSortScore(car1) - getSortScore(car2);
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
        int enterpriseScore = ENTERPRISE_SUPPLIERS.contains(car.getSupplierName()) ? 0 : 1;

        int sippScore = getSippScore(car.getSippCode());

        int priceScore = (int) (car.getRentalCost() * 100);

        // shift enterprise and sipp score into the highest byte
        int score = ((enterpriseScore << 2) + sippScore) << 24;
        // add price score
        score += priceScore;
        LOGGER.debug("Score for {}: {} [{} {} {}]", car, score, enterpriseScore, sippScore, priceScore);
        return score;
    }

    private int getSippScore(String sippCode) {
        switch (sippCode.charAt(0)) {
            case 'M':
                return 0;
            case 'E':
                return 1;
            case 'C':
                return 2;
            default:
                return 3;
        }
    }
}
