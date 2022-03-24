package com.cartrawler.assessment.loader;

import com.cartrawler.assessment.car.CarResult;
import org.apache.commons.csv.CSVFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;

/**
 * Loads data from the given file
 */
public class CarResultLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarResultLoader.class);
    private static final String[] CSV_HEADERS = {"DESCRIPTION", "SUPPLIER", "SIPP", "PRICE", "FUEL_POLICY"};

    public Collection<CarResult> loadCsv(String fileName) throws IOException {
        var reader = new FileReader(fileName, StandardCharsets.UTF_8);
        var csv = CSVFormat.Builder.create()
                .setHeader(CSV_HEADERS)
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader);
        var cars = new HashSet<CarResult>();
        csv.forEach(record -> {
            var car = new CarResult(
                    record.get(CSV_HEADERS[0]),
                    record.get(CSV_HEADERS[1]),
                    record.get(CSV_HEADERS[2]),
                    Double.parseDouble(record.get(CSV_HEADERS[3])),
                    CarResult.FuelPolicy.valueOf(record.get(CSV_HEADERS[4]))
            );
            if (!cars.add(car)) {
                LOGGER.warn("Found duplicate result at row {} - {}", csv.getCurrentLineNumber(), car);
            }
        });
        LOGGER.info("Loaded {} cars from {} [{} lines]", cars.size(), fileName, csv.getRecordNumber());
        return cars;
    }
}
