package com.cartrawler.assessment.app;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.service.FilterService;
import com.cartrawler.assessment.view.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.csv.CSVFormat;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class AssessmentRunner {

    static final Logger LOGGER = LoggerFactory.getLogger(AssessmentRunner.class);

    public static final String DEFAULT_FILE_NAME = "cars.csv";
    public static final String[] CSV_HEADERS = {"DESCRIPTION", "SUPPLIER", "SIPP", "PRICE", "FUEL_POLICY"};

    public static void main(String[] args) throws IOException {
        var cars = loadCsv();
        var filterService = new FilterService();
        Display display = new Display();
        display.render(filterService.filter(cars));
    }

    private static Set<CarResult> loadCsv() throws IOException {
        var reader = new FileReader(DEFAULT_FILE_NAME, StandardCharsets.UTF_8);
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
        LOGGER.info("Loaded {} cars from {} [{} lines]", cars.size(), DEFAULT_FILE_NAME, csv.getRecordNumber());
        return cars;
    }
}