package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity_;
import org.hillel.persistence.jpa.repository.StopJpaRepository;
import org.hillel.persistence.jpa.repository.specification.Filter;
import org.hillel.persistence.jpa.repository.specification.StopSpecification;
import org.hillel.persistence.jpa.repository.specification.VehicleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Service(value = "transactionalStopService")
public class TransactionalStopService {

    private Filter<StopEntity> filter = new Filter<>();

    @Autowired
    private StopJpaRepository stopRepository;

    @Transactional
    public StopEntity createOrUpdate(final StopEntity entity){
        if (entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        return stopRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public Optional<StopEntity> getById(Long id){
        return stopRepository.findById(id);
    }

    @Transactional
    public void remove(final StopEntity stop){
        stopRepository.delete(stop);
    }

    @Transactional
    public void removeById(Long stopId) {
        stopRepository.deleteById(stopId);
    }

    @Transactional(readOnly = true)
    public Collection<StopEntity> findAll(){
        return (Collection<StopEntity>) stopRepository.findAll();
    }

    /*@Transactional(readOnly = true)
    public Collection<StopEntity> findAllAsNative(){
        return stopRepository.findAllAsNative();
    }
*/
  /*  @Transactional(readOnly = true)
    public Collection<StopEntity> findAllAsNamed(){
        return stopRepository.findAllAsNamed();
    }
*/
   /* @Transactional(readOnly = true)
    public Collection<StopEntity> findAllAsCriteria(){
        return stopRepository.findAllAsCriteria();
    }
*/
   /* @Transactional(readOnly = true)
    public Collection<StopEntity> findAllAsStoredProcedure(){
        return stopRepository.findAllAsStoredProcedure();
    }
*/
    @Transactional(readOnly = true)
    public Collection<StopEntity> findAllWithPagination(final int numberPage, final int sizePage, HashMap<String, String> specifications){
        if(specifications.isEmpty()){return findAllWithPagination(numberPage, sizePage);}
        return stopRepository.findAll(filter.builderSpecification(specifications) , PageRequest.of(numberPage, sizePage)).getContent();
    }

    @Transactional(readOnly = true)
    public Collection<StopEntity> findAllWithPagination(final int numberPage, final int sizePage){
        return stopRepository.findList(PageRequest.of(numberPage, sizePage));
    }

    @Transactional(readOnly = true)
    public Collection<StopEntity> findAllWithPagination(final int numberPage, final int sizePage, String sort){
        return stopRepository.findList(PageRequest.of(numberPage, sizePage, Sort.by(sort)));
    }
    public Long getCount() {
        return stopRepository.findCountLong();
    }
}
