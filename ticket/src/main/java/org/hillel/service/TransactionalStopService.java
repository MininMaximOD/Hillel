package org.hillel.service;

import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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
}
