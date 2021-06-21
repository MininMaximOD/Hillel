package org.hillel.service;

import org.hillel.persistence.entity.StopAdditionalInfoEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.jpa.repository.StopAdditionalInfoJpaRepository;
import org.hillel.persistence.jpa.repository.StopJpaRepository;
import org.hillel.persistence.jpa.repository.specification.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private StopAdditionalInfoJpaRepository stopAdditionalInfoJpaRepository;

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
        return stopRepository.findAll();
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
    public Page<StopEntity> findAllWithPagination(Pageable page, HashMap<String, String> specifications){
        if(specifications.isEmpty()){return stopRepository.findAll(page);}
        return stopRepository.findAll(filter.builderSpecification(specifications) , page);
    }

    @Transactional(readOnly = true)
    public Page<StopEntity> findAllWithPagination(Pageable page){
        return stopRepository.findAll(page);
    }

    public Long getCount() {
        return stopRepository.findCountLong();
    }

    @Transactional(readOnly = true)
    public StopEntity findById(Long stopId) {
        return stopRepository.findById(stopId).get();
    }
}
