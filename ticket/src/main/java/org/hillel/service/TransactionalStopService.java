package org.hillel.service;

import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@Service(value = "transactionalStopService")
public class TransactionalStopService {

    @Autowired
    private StopRepository stopRepository;

    @Transactional
    public StopEntity createOrUpdate(final StopEntity entity){
        if (entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        return stopRepository.createOrUpdate(entity);
    }

    @Transactional(readOnly = true)
    public Optional<StopEntity> getById(Long id){
        return stopRepository.findById(id);
    }

    @Transactional
    public void remove(final StopEntity stop){
        stopRepository.remove(stop);
    }

    @Transactional
    public void removeById(Long stopId) {
        stopRepository.removeById(stopId);
    }

    @Transactional(readOnly = true)
    public Collection<StopEntity> findAll(){
        return stopRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<StopEntity> findAllAsNative(){
        return stopRepository.findAllAsNative();
    }

  /*  @Transactional(readOnly = true)
    public Collection<StopEntity> findAllAsNamed(){
        return stopRepository.findAllAsNamed();
    }
*/
    @Transactional(readOnly = true)
    public Collection<StopEntity> findAllAsCriteria(){
        return stopRepository.findAllAsCriteria();
    }

   /* @Transactional(readOnly = true)
    public Collection<StopEntity> findAllAsStoredProcedure(){
        return stopRepository.findAllAsStoredProcedure();
    }
*/
    @Transactional
    public Collection<StopEntity> findAllWithSorted(int startPosition, int countValues, String sortedField, boolean ascending) {
        return stopRepository.findAllWithSorted(startPosition, countValues, sortedField, ascending);
    }

    public BigInteger getCount() {
        return stopRepository.getCountEntitys();
    }
}
