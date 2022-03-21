package org.hillel.service;

import org.hillel.Journey;
import org.hillel.persistence.entity.JourneyEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface JourneyService {

    Long createJourney(JourneyEntity entity);

    Collection<Journey> find(String stationFrom, String stationTo, LocalDateTime dateFrom);
}
