package org.example;

import org.apache.commons.compress.utils.IOUtils;

import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException {
        JsonReader parser = new JsonReader();
        FlightSchedule schedule = parser.parse();
        System.out.println("______________________________");
        schedule.prepareData();
        schedule.calculatedTime();
        System.out.println("______________________________");
        schedule.calculatedPrice();
    }
}