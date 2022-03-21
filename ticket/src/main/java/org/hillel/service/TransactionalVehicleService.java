package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.jpa.repository.VehicleJpaRepository;
import org.hillel.persistence.jpa.repository.specification.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    }*/

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllWithPagination(final int numberPage, final int sizePage){
        return vehicleRepository.findList(PageRequest.of(numberPage, sizePage));
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllWithPagination(final int numberPage, final int sizePage, String sort){
        return vehicleRepository.findList(PageRequest.of(numberPage, sizePage, Sort.by(sort)));
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllWithPagination(final int numberPage, final int sizePage, HashMap<String, String> specifications){
        if(specifications.isEmpty()){return findAllWithPagination(numberPage, sizePage);}
        return vehicleRepository.findAll(filter.builderSpecification(specifications) , PageRequest.of(numberPage, sizePage)).getContent();
    }

    @Transactional(readOnly = true)
    public Long getCount() {
        return vehicleRepository.findCountLong();
    }

    /*@Transactional(readOnly = true)
    public Collection<VehicleEntity> findVehiclesWithMaxOrMinSeats(String a){
        return vehicleRepository.findVehiclesWithMaxOrMinSeats(a);
    }*/
}
