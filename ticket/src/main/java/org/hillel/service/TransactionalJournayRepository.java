package org.hillel.service;

import org.hillel.Journey;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.repository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collection;

public class TransactionalJournayRepository implements JourneyService{

    @Autowired
    private JourneyRepository journeyRepository;

    @Override
    public Collection<Journey> find(String stationFrom, String stationTo, LocalDateTime dateFrom) {
        return null;
    }

    public Long createJourney(final JourneyEntity entity){
        if (entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        return journeyRepository.create(entity);
    }
}
