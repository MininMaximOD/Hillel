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
import java.util.HashMap;
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

    public Collection<VehicleEntity> findAllVehicles(){
        return vehicleService.findAll();
    }

    public Collection<JourneyEntity> findAllJourneys(){
        return journeyService.findAll();
    }

    public Collection<StopEntity> findAllStops(){
        return stopService.findAll();
    }

    public Collection<JourneyEntity> findAllJourneysWithPagination(final int numberPage,final int sizePage){
        return journeyService.findAllWithPagination(numberPage, sizePage);
    }

    public Collection<JourneyEntity> findAllJourneysWithPaginationAndSort(final int numberPage,final int sizePage, String sort){
        return journeyService.findAllWithPagination(numberPage, sizePage, sort);
    }

    public Collection<VehicleEntity> findAllVehiclesWithPagination(final int numberPage,final int sizePage){
        return vehicleService.findAllWithPagination(numberPage, sizePage);
    }

    public Collection<VehicleEntity> findAllVehiclesWithPaginationAndSort(final int numberPage,final int sizePage, String sort){
        return vehicleService.findAllWithPagination(numberPage, sizePage, sort);
    }

    public Collection<StopEntity> findAllStopsWithPagination(final int numberPage,final int sizePage){
        return stopService.findAllWithPagination(numberPage, sizePage);
    }

    public Collection<StopEntity> findAllWithStopsPaginationAndSort(final int numberPage,final int sizePage, String sort){
        return stopService.findAllWithPagination(numberPage, sizePage, sort);
    }
    public Collection<VehicleEntity> findVehiclesWithMinFreeSeats(){
        return null;
    }

    public Collection<VehicleEntity> findVehiclesWithMaxFreeSeats(){
        return null;
    }

//    public VehicleEntity findFreeVehicle(){
//        return vehicleService.findFreeEntity();
//    }

    public Collection<JourneyEntity> findAllWithDynamicFilterJourney(HashMap<String, String> predicates, int numberPage, int sizePage){
        return journeyService.findAllWithPagination(numberPage, sizePage, predicates);
    }

    public Collection<VehicleEntity> findAllWithDynamicFilterVehicle(HashMap<String, String> predicates, int numberPage, int sizePage){
        return vehicleService.findAllWithPagination(numberPage, sizePage, predicates);
    }

    public Collection<StopEntity> findAllWithDynamicFilterStop(HashMap<String, String> predicates, int numberPage, int sizePage){
        return stopService.findAllWithPagination(numberPage, sizePage, predicates);
    }


    public Long getCountJourneys() {
        return journeyService.getCount();
    }
    public long getCountVehicles() {
        return vehicleService.getCount();
    }

    public Long getCountStops() {
        return stopService.getCount();
    }

    /*public Collection<VehicleEntity> findVehiclesWithMaxOrMinFreeSeats(String maxOrMin){
        return vehicleService.findVehiclesWithMaxOrMinSeats(maxOrMin);
    }*/
}

