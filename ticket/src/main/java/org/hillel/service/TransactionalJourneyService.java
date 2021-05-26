package org.hillel.service;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.jpa.repository.JourneyJpaRepository;
import org.hillel.persistence.jpa.repository.specification.Filter;
import org.hillel.persistence.jpa.repository.specification.JourneySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Service(value = "transactionJourneyService")
public class TransactionalJourneyService{

    private Filter<JourneyEntity> filter = new Filter();

    @Autowired
    private JourneyJpaRepository journeyRepository;

    @Transactional
    public JourneyEntity createOrUpdate(final JourneyEntity entity){
       if (entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        final JourneyEntity orUpdate = journeyRepository.save(entity);
        JourneyEntity journey = journeyRepository.findById(orUpdate.getId()).get();
        return journeyRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public Optional<JourneyEntity> getById(Long id, Boolean withDependencies){
        final Optional<JourneyEntity> byId = journeyRepository.findById(id);
        if (withDependencies && byId.isPresent()){
            final JourneyEntity journeyEntity = byId.get();
            journeyEntity.getVehicle().getName();
            journeyEntity.getStops().size();
        }
        return journeyRepository.findById(id);
    }

    @Transactional
    public void remove(JourneyEntity journey) {
        journeyRepository.delete(journey);
    }

    @Transactional
    public void removeById(Long journeyId) {
        journeyRepository.deleteById(journeyId);
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAll(){
        return journeyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllWithPagination(final int numberPage, final int sizePage){
        return journeyRepository.findList(PageRequest.of(numberPage, sizePage));
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllWithPagination(final int numberPage, final int sizePage, HashMap<String, String> specifications){
        return journeyRepository.findAll(filter.builderSpecification(specifications) , PageRequest.of(numberPage, sizePage)).getContent();
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllWithPagination(final int numberPage, final int sizePage, String sort){
        return journeyRepository.findList(PageRequest.of(numberPage, sizePage, Sort.by(sort)));
    }




   /* @Transactional(readOnly = true)
    public Collection<AbstractModifyEntity> findOnlyActive(){return journeyRepository.findOnlyActive(CommonSpecification.onlyActive());}
*/
   /* @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllAsNative(){
        return journeyRepository.findAllAsNative();
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllAsNamed(){
        return journeyRepository.findAllAsNamed();
    }*/

  /*  @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllAsCriteria(){
        return journeyRepository.findAllAsCriteria();
    }

    @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllAsStoredProcedure(){
        return journeyRepository.findAllAsStoredProcedure();
    }
*/   /* @Transactional(readOnly = true)
    public Collection<JourneyEntity> findAllWithSorted(int pageNumber, int countValues, String sortedField, boolean ascending) {
        return journeyRepository.findAllWithSorted(PageRequest.of(pageNumber, countValues, Sort.by("JourneyEntity_."+ sortedField)));
    }
*/
    @Transactional(readOnly = true)
    public Long getCount() {
        return journeyRepository.findCountLong();
    }

}
