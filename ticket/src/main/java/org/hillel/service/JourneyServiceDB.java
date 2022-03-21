package org.hillel.service;

import org.hillel.Journey;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.repository.JourneyRepositoryImpl;

import java.time.LocalDateTime;
import java.util.*;

public class JourneyServiceDB implements JourneyService{
    private JourneyRepositoryImpl journeyRepository;

    @Override
    public Long createJourney(JourneyEntity entity) {
        return null;
    }

    @Override
    public Collection<Journey> find(String stationFrom, String stationTo, LocalDateTime dateFrom) {
        List<Journey> journeys = journeyRepository.get(stationFrom, stationTo, dateFrom); //вызов метода поиска в БД
        if (journeys == null || journeys.isEmpty()) return Collections.emptyList();
        List<Journey> out = new ArrayList<>();
        for (Journey item: journeys) {
            if (item.getStationFrom().equals(stationFrom) && item.getStationTo().equals(stationTo) && item.getDeparture().equals(dateFrom)){
                out.add(item);
            }
        }
        return Collections.unmodifiableList(out);
    }
}
