package org.hillel.service;

import org.hillel.Journey;

import java.time.LocalDateTime;
import java.util.Collection;

public class TicketClient {
    private JourneyService journeyService;

    public TicketClient(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    public Collection<Journey> find(String stationFrom, String stationTo, LocalDateTime dataFrom, LocalDateTime dataTo) throws IllegalAccessException {
        if(stationFrom == null) throw new IllegalAccessException("station from must be a set");
        if(stationTo == null) throw new IllegalAccessException("station to must be a set");
        if(dataFrom == null) throw new IllegalAccessException("departure must be a set");
        if(dataTo == null) throw new IllegalAccessException("arrival from must be a set");
        return journeyService.find(stationFrom, stationTo, dataFrom);
    }
}
