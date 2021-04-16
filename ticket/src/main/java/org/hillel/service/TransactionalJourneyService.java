package org.hillel.service;

import org.hillel.Journey;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.repository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service(value = "transactionJourneyService")
public class TransactionalJourneyService{

    @Autowired
    private JourneyRepository journeyRepository;

    public Collection<Journey> find(String stationFrom, String stationTo, LocalDateTime dateFrom) {
        return null;
    }

    @Transactional
    public JourneyEntity createOrUpdate(final JourneyEntity entity){
       if (entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        final JourneyEntity orUpdate = journeyRepository.createOrUpdate(entity);
        JourneyEntity journey = journeyRepository.findById(orUpdate.getId()).get();
        journeyRepository.removeById(journey.getId());
        return journeyRepository.createOrUpdate(entity);
    }

    @Transactional(readOnly = true)
    public Optional<JourneyEntity> getById(Long id, Boolean withDependencies){
        final Optional<JourneyEntity> byId = journeyRepository.findById(id);
        if (withDependencies && byId.isPresent()){
            final JourneyEntity journeyEntity = byId.get();
            journeyEntity.getVehicle().getName();
            journeyEntity.getStops().size();
        }
        return journeyRepository.findById(id);
    }

    @Transactional
    public void remove(JourneyEntity journey) {
        journeyRepository.remove(journey);
    }

    @Transactional
    public void removeById(Long journeyId) {
        journeyRepository.removeById(journeyId);
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAll(){
        return journeyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllAsNative(){
        return journeyRepository.findAllAsNative();
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllAsNamed(){
        return journeyRepository.findAllAsNamed();
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllAsCriteria(){
        return journeyRepository.findAllAsCriteria();
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllAsStoredProcedure(){
        return journeyRepository.findAllAsStoredProcedure();
    }
}
