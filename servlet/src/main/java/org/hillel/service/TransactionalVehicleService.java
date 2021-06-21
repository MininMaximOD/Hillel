package org.hillel.service;

import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.jpa.repository.VehicleJpaRepository;
import org.hillel.persistence.jpa.repository.specification.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TransactionalVehicleService {

    private Filter<VehicleEntity> filter = new Filter<>();

    @Autowired
    private VehicleJpaRepository vehicleRepository;

    @Transactional
    public VehicleEntity createOrUpdate (final VehicleEntity vehicleEntity){
        if(vehicleEntity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        return vehicleRepository.save(vehicleEntity);
    }

    @Transactional(readOnly = true)
    public Optional<VehicleEntity> getById(Long id){
        return vehicleRepository.findById(id);
    }

    @Transactional
    public void remove(VehicleEntity vehicle){
        vehicleRepository.delete(vehicle);
    }

    @Transactional
    public void removeById(Long vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    @Transactional
    public List<VehicleEntity> findByIds(Long ... ids){
        return vehicleRepository.findAllById(Arrays.asList(ids));
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
        return (Collection<VehicleEntity>) vehicleRepository.findAll();
    }

    /*@Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllAsNative(){
        return vehicleRepository.findAllAsNative();
    }
*/
   /* @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllAsNamed(){
        return vehicleRepository.findAllAsNamed();
    }*/

    /*@Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllAsCriteria(){
        return vehicleRepository.findAllAsCriteria();
    }
*/
   /* @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllAsStoredProcedure(){
        return vehicleRepository.findAllAsStoredProcedure();
    }*/

    /*@Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllWithSorted(int startPosition, int countValues, String sortedField, boolean ascending){
        return vehicleRepository.findAllWithSorted(startPosition, countValues, sortedField, ascending);
    }

    @Transactional(readOnly = true)
    public VehicleEntity findFreeEntity() {
        return vehicleRepository.findFreeEntity();
    }
*/
    @Transactional(readOnly = true)
    public Page<VehicleEntity> findAllWithPagination(Pageable page){
        return vehicleRepository.findAll(page);
    }

    @Transactional(readOnly = true)
    public Page<VehicleEntity> findAllWithPagination(Pageable page, HashMap<String, String> specifications){
        if(specifications.isEmpty()){return vehicleRepository.findAll(page);}
        return vehicleRepository.findAll(filter.builderSpecification(specifications) , page);
    }

    @Transactional(readOnly = true)
    public Long getCount() {
        return vehicleRepository.findCountLong();
    }

    public Collection<VehicleEntity> findAllFree(Example<VehicleEntity> example) {
        return vehicleRepository.findAll(example);
    }

    /*@Transactional(readOnly = true)
    public Collection<VehicleEntity> findVehiclesWithMaxOrMinSeats(String a){
        return vehicleRepository.findVehiclesWithMaxOrMinSeats(a);
    }*/
}
