package org.hillel.service;

import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionalVehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public VehicleEntity createOrUpdate (final VehicleEntity vehicleEntity){
        if(vehicleEntity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        return vehicleRepository.createOrUpdate(vehicleEntity);
    }

    @Transactional(readOnly = true)
    public Optional<VehicleEntity> getById(Long id){
        return vehicleRepository.findById(id);
    }

    @Transactional
    public void remove(VehicleEntity vehicle){
        vehicleRepository.remove(vehicle);
    }

    @Transactional
    public void removeById(Long vehicleId) {
        vehicleRepository.removeById(vehicleId);
    }

    @Transactional
    public Collection<VehicleEntity> findByIds(Long ... ids){
        return vehicleRepository.findByIds(ids);
    }

    @Transactional(readOnly = true)
    public Optional<VehicleEntity> findById(Long id, boolean withDep){
        final Optional<VehicleEntity> byId = vehicleRepository.findById(id);
        if(!byId.isPresent()) return byId;
        if(!withDep) return byId;
        byId.get().getJourneys().size();
        return byId;
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAll(){
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllAsNative(){
        return vehicleRepository.findAllAsNative();
    }

   /* @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllAsNamed(){
        return vehicleRepository.findAllAsNamed();
    }*/

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllAsCriteria(){
        return vehicleRepository.findAllAsCriteria();
    }

   /* @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllAsStoredProcedure(){
        return vehicleRepository.findAllAsStoredProcedure();
    }*/

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllWithSorted(int startPosition, int countValues, String sortedField, boolean ascending){
        return vehicleRepository.findAllWithSorted(startPosition, countValues, sortedField, ascending);
    }

    @Transactional(readOnly = true)
    public VehicleEntity findFreeEntity() {
        return vehicleRepository.findFreeEntity();
    }

    @Transactional(readOnly = true)
    public BigInteger getCount() {
        return vehicleRepository.getCountEntitys();
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findVehiclesWithMaxOrMinSeats(String a){
        return vehicleRepository.findVehiclesWithMaxOrMinSeats(a);
    }
}
