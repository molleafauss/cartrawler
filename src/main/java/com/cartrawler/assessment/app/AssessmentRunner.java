package com.cartrawler.assessment.app;

import com.cartrawler.assessment.loader.CarResultLoader;
import com.cartrawler.assessment.service.CarSorter;
import com.cartrawler.assessment.view.Display;

import java.io.IOException;

public class AssessmentRunner {

    public static final String DEFAULT_FILE_NAME = "cars.csv";

    public static void main(String[] args) throws IOException {
        var cars = new CarResultLoader().loadCsv(DEFAULT_FILE_NAME);
        cars = new CarSorter().sort(cars);
        new Display().render(cars);
    }
}