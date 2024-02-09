package org.example;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;

public class JsonReader {

    public FlightSchedule parse() {

        Gson gson = new Gson();
        try (Reader reader = new FileReader("./res/tickets.json")) {
            FlightSchedule tickets = gson.fromJson(reader, FlightSchedule.class);
            return tickets;
        } catch (Exception e) {
            System.out.println("Parsing error " + e.toString());
        }
        return null;
    }
}

