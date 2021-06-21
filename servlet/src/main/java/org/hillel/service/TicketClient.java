package org.hillel.service;

import org.hillel.controller.dto.JourneyDto;
import org.hillel.persistence.entity.*;
import org.hillel.persistence.entity.enums.VehicleStatus;
import org.hillel.persistence.jpa.repository.specification.VehicleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Component
public class TicketClient {

    @Autowired
    private TransactionalJourneyService journeyService;

    @Autowired
    private TransactionalStopService stopService;

    @Autowired
    private TransactionalVehicleService vehicleService;

    @Autowired
    private TransactionalStopAdditionalInfoService stopAdditionalInfoService;

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

    public StopEntity createOrUpdateStop(StopEntity stop){
        if (stop == null){
            throw new IllegalArgumentException("stop is null");
        }
        stop.addStopAdditionalInfo(stop.getAdditionalInfo());
       /* StopEntity stopEntity = stopService.createOrUpdate(stop);
        if(stopEntity.getAdditionalInfo() == null){
            StopAdditionalInfoEntity stopAdditionalInfoEntity = new StopAdditionalInfoEntity();
        }else{

        }
        if
        stopAdditionalInfoService.createOrUpdate(stop.getAdditionalInfo());*/
       return stopService.createOrUpdate(stop);
//         stopAdditionalInfoService.createOrUpdate(stopEntity.getAdditionalInfo());
    }

    public void removeVehicleById(Long id){
        vehicleService.removeById(id);
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

    public Collection<StopEntity> findAllStops(){ return stopService.findAll(); }

    public Page<JourneyEntity> findAllJourneysWithPagination(Pageable page){
        return journeyService.findAllWithPagination(page);
    }

    public Page<JourneyEntity> findAllJourneysWithPagination(Pageable page, HashMap<String, String> filter){
        return journeyService.findAllWithPagination(page, filter);
    }

    public Page<VehicleEntity> findAllVehiclesWithPagination(Pageable page){
        return vehicleService.findAllWithPagination(page);
    }

    public Page<VehicleEntity> findAllVehiclesWithPagination(Pageable page, HashMap<String, String> filter){
        return vehicleService.findAllWithPagination(page);
    }

    public Page<StopEntity> findAllStopsWithPagination(Pageable page){
        return stopService.findAllWithPagination(page);
    }

    public Page<StopEntity> findAllStopsWithPagination(Pageable page, HashMap<String, String> filter){
        return stopService.findAllWithPagination(page);
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

    public Page<JourneyEntity> findAllWithDynamicFilterJourney(Pageable page){
        return journeyService.findAllWithPagination(page);
    }

    public Page<VehicleEntity> findAllWithDynamicFilterVehicle(HashMap<String, String> predicates, Pageable page){
        return vehicleService.findAllWithPagination(page, predicates);
    }

    public Page<StopEntity> findAllWithDynamicFilterStop(HashMap<String, String> predicates, Pageable page){
        return stopService.findAllWithPagination(page, predicates);
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

    public void removeJourneyById(Long journeyId) {
        journeyService.removeById(journeyId);
    }

    public void removeStopById(Long stopId) {
        StopEntity stopEntity = stopService.findById(stopId);
        stopEntity.removeStopAdditionalInfo();
        stopService.removeById(stopId);
    }

    public StopEntity findStopById(Long stopId) {
        return stopService.findById(stopId);
    }

    public StopAdditionalInfoEntity findAdditionalInfoStopById(Long stopId) {
        return stopAdditionalInfoService.findAdditionalInfoStopById(stopId);
    }

    public VehicleEntity findVehicleById(Long vehicleId) {
        return vehicleService.getById(vehicleId).get();
    }

    public JourneyEntity findJourneyById(Long journeyId) {
        return journeyService.getById(journeyId, false).get();
    }

    public void addVehicleToJourney(JourneyEntity journey, VehicleEntity vehicle) {
        journey.addVehicle(vehicle);
        vehicle.setVehicleStatus(VehicleStatus.ON_ROUTE);
        journeyService.createOrUpdate(journey);
        vehicleService.createOrUpdate(vehicle);
    }

    public Collection<VehicleEntity> findAllFreeVehicles() {
        VehicleEntity example = new VehicleEntity();
        example.setVehicleStatus(VehicleStatus.FREE);
        return vehicleService.findAllFree(Example.of(example));
    }

    public void addVehicleToJourneyById(Long journeyId, Long vehicleId) {
        JourneyEntity journeyEntity = journeyService.getById(journeyId, false).get();
        VehicleEntity vehicleEntity = vehicleService.getById(vehicleId).get();
        journeyEntity.addVehicle(vehicleEntity);
        vehicleEntity.setVehicleStatus(VehicleStatus.ON_ROUTE);
        createOrUpdateJourney(journeyEntity);
        createOrUpdateVehicle(vehicleEntity);
    }

    /*public Collection<VehicleEntity> findVehiclesWithMaxOrMinFreeSeats(String maxOrMin){
        return vehicleService.findVehiclesWithMaxOrMinSeats(maxOrMin);
    }*/
}

