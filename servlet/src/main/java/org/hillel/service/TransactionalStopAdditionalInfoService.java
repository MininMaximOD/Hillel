package org.hillel.service;

import org.hillel.persistence.entity.StopAdditionalInfoEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.jpa.repository.StopAdditionalInfoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "transactionalAdditionalInfoStopService")
public class TransactionalStopAdditionalInfoService {

    @Autowired
    private StopAdditionalInfoJpaRepository stopAdditionalInfoJpaRepository;

    public StopAdditionalInfoEntity findAdditionalInfoStopById(Long stopId) {
        return stopAdditionalInfoJpaRepository.findById(stopId).get();
    }

    @Transactional
    public StopAdditionalInfoEntity createOrUpdate(final StopAdditionalInfoEntity entity){
        if (entity == null){
            throw new IllegalArgumentException("Entity is null");
        }
        return stopAdditionalInfoJpaRepository.save(entity);
    }
}
