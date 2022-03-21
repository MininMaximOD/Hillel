package org.hillel.service;

import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionalVehicleService {

    private VehicleRepository vehicleRepository;

    @Transactional
    public VehicleEntity createOrUpdate (final VehicleEntity vehicleEntity){
        if(vehicleEntity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        return vehicleRepository.createOrUpdate(vehicleEntity);
    }

    @Transactional
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
}
