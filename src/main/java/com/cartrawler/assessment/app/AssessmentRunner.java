package com.cartrawler.assessment.app;

import com.cartrawler.assessment.loader.CarResultLoader;
import com.cartrawler.assessment.service.FilterService;
import com.cartrawler.assessment.view.Display;

import java.io.IOException;

public class AssessmentRunner {

    public static final String DEFAULT_FILE_NAME = "cars.csv";

    public static void main(String[] args) throws IOException {
        var cars = new CarResultLoader().loadCsv(DEFAULT_FILE_NAME);
        var filterService = new FilterService();
        Display display = new Display();
        display.render(filterService.filter(cars));
    }
}