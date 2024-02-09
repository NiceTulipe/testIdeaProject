package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class FlightSchedule {
    public ArrayList<Ticket> tickets;

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    Map<String, Long> VVOtoTLVTime = new HashMap<>() {
    };
    List<Integer> ticketPriceList = new ArrayList<>();

    public void prepareData() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatterTimeAlter = DateTimeFormatter.ofPattern("H:mm");
        Long minTimeAtCarrier = 0L;
        for (Ticket t : tickets) {
            LocalDateTime depart;
            LocalDateTime arrived;
            if ((t.origin.equals("VVO") & t.destination.equals("TLV")) ||
                    (t.origin.equals("TLV")) & t.destination.equals("VVO")) {
                try {
                    depart = LocalDateTime.of(LocalDate.parse(t.getDeparture_date(), formatterDate),
                            LocalTime.parse(t.getDeparture_time(), formatterTime));
                } catch (Exception e) {
                    depart = LocalDateTime.of(LocalDate.parse(t.getDeparture_date(), formatterDate),
                            LocalTime.parse(t.getDeparture_time(), formatterTimeAlter));
                }
                try {
                    arrived = LocalDateTime.of(LocalDate.parse(t.getArrival_date(), formatterDate),
                            LocalTime.parse(t.getArrival_time(), formatterTime));
                } catch (Exception e) {
                    arrived = LocalDateTime.of(LocalDate.parse(t.getArrival_date(), formatterDate),
                            LocalTime.parse(t.getArrival_time(), formatterTimeAlter));
                }
                Duration duration = Duration.between(depart, arrived);
                Long durRem = duration.toMinutes();
                if (VVOtoTLVTime.containsKey(t.carrier)) {
                    minTimeAtCarrier = VVOtoTLVTime.get(t.carrier) < durRem ?
                            VVOtoTLVTime.get(t.carrier) : durRem;
                    VVOtoTLVTime.put(t.carrier, minTimeAtCarrier);
                } else {
                    VVOtoTLVTime.put(t.carrier, durRem);
                }
                ticketPriceList.add(t.price);
            }
        }
    }

    public void calculatedTime() {
        for (String carrier : VVOtoTLVTime.keySet()) {
            long minimalTimeHour = VVOtoTLVTime.get(carrier) / 60;
            long minimalTimeMin = VVOtoTLVTime.get(carrier) % 60;
            System.out.println("Минимальная время полета авиакомпании " + carrier
                    + " между Владивостоком и Тель-Авивом = "
                    + minimalTimeHour + " часов и " + minimalTimeMin + " минут");
        }
    }

    public void calculatedPrice() {
        int sum = 0;
        double mediana = 0.0;
        for (Integer p : ticketPriceList) {
            sum = sum + p;
        }
        Collections.sort(ticketPriceList);
        double avg = (double) (sum / ticketPriceList.size());
        if (ticketPriceList.size() % 2 == 0) {
            mediana = (double) ((ticketPriceList.get(ticketPriceList.size() / 2)
                    + ticketPriceList.get((ticketPriceList.size() / 2) - 1)) / 2);
        } else {
            mediana = (double)((ticketPriceList.get(ticketPriceList.size() / 2)));
        }
        System.out.println("Разница между средней ценой и медианой " + (avg - mediana));
    }

    @Override
    public String toString() {
        return "Root{" +
                "tickets=" + tickets +
                '}';
    }
}




