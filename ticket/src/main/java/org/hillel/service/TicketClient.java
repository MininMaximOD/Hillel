package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class TicketClient {

    @Autowired
  //  @Qualifier("transactionalJourneyService")
    private TransactionalJourneyService journeyService;

    @Autowired
    private TransactionalStopService stopService;

    @Autowired
    private TransactionalVehicleService vehicleService;

//    @Autowired
//    Environment environment;

    public TicketClient(){
    }


    public Optional<JourneyEntity> findById(Long id, Boolean withDependencies){
        return id ==null ? Optional.empty() : journeyService.getById(id, withDependencies);
    }

    public JourneyEntity createOrUpdateJourney(JourneyEntity journey){
        if (journey == null){
            throw new IllegalArgumentException("journey is null");
        }
        return journeyService.createOrUpdate(journey);
    }

    public void createOrUpdateVehicle(VehicleEntity vehicle){
        if (vehicle == null){
            throw new IllegalArgumentException("vehicle is null");
        }
        vehicleService.createOrUpdate(vehicle);
    }

    public void createOrUpdateStop(StopEntity stop){
        if (stop == null){
            throw new IllegalArgumentException("stop is null");
        }
        stopService.createOrUpdate(stop);
    }

    public void remove(JourneyEntity journey){
        journeyService.remove(journey);
    }

    public void removeById(Long journeyId){
        journeyService.removeById(journeyId);
    }

    public void removeVehicle(VehicleEntity vehicle){
        vehicleService.remove(vehicle);
    }

    public Collection<VehicleEntity> findAllVehicles(int startPosition, int countValues, String sortedField, boolean ascending){
        return vehicleService.findAllWithSorted(startPosition, countValues, sortedField, ascending);
    }

    public Collection<JourneyEntity> findAllJourneys(int startPosition, int countValues, String sortedField, boolean ascending){
        return journeyService.findAllWithSorted(startPosition, countValues, sortedField, ascending);
    }

    public Collection<StopEntity> findAllStops(int startPosition, int countValues, String sortedField, boolean ascending){
        return stopService.findAllWithSorted(startPosition, countValues, sortedField, ascending);
    }

    public Collection<VehicleEntity> findVehiclesWithMinFreeSeats(){
        return null;
    }

    public Collection<VehicleEntity> findVehiclesWithMaxFreeSeats(){
        return null;
    }

    public VehicleEntity findFreeVehicle(){
        return vehicleService.findFreeEntity();
    }



    public BigInteger getCountJourneys() {
        return journeyService.getCount();
    }

    public BigInteger getCountVehicles() {
        return vehicleService.getCount();
    }

    public BigInteger getCountStops() {
        return stopService.getCount();
    }

    public Collection<VehicleEntity> findVehiclesWithMaxOrMinFreeSeats(String maxOrMin){
        return vehicleService.findVehiclesWithMaxOrMinSeats(maxOrMin);
    }
}

