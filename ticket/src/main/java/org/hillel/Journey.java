package org.hillel;

import java.time.LocalDateTime;
import java.util.Objects;

public class Journey {
    private final String stationFrom;
    private final String stationTo;
    private final LocalDateTime departure;
    private final LocalDateTime arrival;
    private final String route;

    public Journey(final String stationFrom, final String stationTo, final LocalDateTime departure, final LocalDateTime arrival) throws IllegalAccessException {
        if(stationFrom == null) throw new IllegalAccessException("station from must be a set");
        if(stationTo == null) throw new IllegalAccessException("station to must be a set");
        if(departure == null) throw new IllegalAccessException("departure must be a set");
        if(arrival == null) throw new IllegalAccessException("arrival from must be a set");

        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.departure = departure;
        this.arrival = arrival;
        this.route = stationFrom + "->" + stationTo;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public String getRoute() {
        return route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journey journey = (Journey) o;
        return Objects.equals(stationFrom, journey.stationFrom) && Objects.equals(stationTo, journey.stationTo) && Objects.equals(departure, journey.departure) && Objects.equals(arrival, journey.arrival) && Objects.equals(route, journey.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationFrom, stationTo, departure, arrival, route);
    }

    @Override
    public String toString() {
        return "Journey{" +
                "stationFrom='" + stationFrom + '\'' +
                ", stationTo='" + stationTo + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", route='" + route + '\'' +
                '}';
    }
}
