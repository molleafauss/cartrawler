package com.cartrawler.assessment.view;

import com.cartrawler.assessment.car.CarResult;

import java.util.Collection;

public class Display {
    public void render(Collection<CarResult> cars) {
        System.out.println(" SUPPLIER  | MAKE / MODEL                                       | SIPP |  PRICE   | FUEL");
        System.out.println("-----------+----------------------------------------------------+------+----------+-----------");
        for (CarResult car : cars) {
            System.out.printf("%-10.10s | %-50.50s | %4.4s | %8.2f | %-9.9s %n", car.getSupplierName(), car.getDescription(),
                    car.getSippCode(), car.getRentalCost(), car.getFuelPolicy());
        }
    }
}
