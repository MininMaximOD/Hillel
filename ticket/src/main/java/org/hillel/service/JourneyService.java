package org.hillel.service;

import org.hillel.Journey;

import java.time.LocalDateTime;
import java.util.Collection;

public interface JourneyService {
    Collection<Journey> find(String stationFrom, String stationTo, LocalDateTime dateFrom);
}
