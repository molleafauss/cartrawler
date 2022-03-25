package com.cartrawler.assessment.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.cartrawler.assessment.loader.CarResultLoader;
import com.cartrawler.assessment.service.CarSorter;
import com.cartrawler.assessment.service.MedianFilter;
import com.cartrawler.assessment.view.Display;

import java.io.IOException;

public class AssessmentRunner {

    public static final String DEFAULT_FILE_NAME = "cars.csv";

    @Parameter(names = "--help", help = true)
    private boolean help;

    @Parameter(names = "--filterAboveMedian", description = "Filter the FULLFULL cars above median price.")
    private boolean filterAboveMedian = false;

    @Parameter(description = "The CSV file where to load the car results from. Default is a file in current directory called 'cars.csv'")
    private String fileName = DEFAULT_FILE_NAME;

    public static void main(String[] args) throws IOException {
        var app = new AssessmentRunner();
        var jc = JCommander.newBuilder()
                .addObject(app)
                .programName(AssessmentRunner.class.getName())
                .build();
        jc.parse(args);
        if (app.help) {
            jc.usage();
            return;
        }
        var cars = new CarResultLoader().loadCsv(app.fileName);
        cars = new CarSorter().sort(cars);
        if (app.filterAboveMedian) {
            cars = new MedianFilter().filter(cars);
        }
        new Display().render(cars);
    }
}