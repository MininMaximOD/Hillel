package org.hillel.service;

import org.hillel.persistence.entity.SeatEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service(value = "transactionSeatService")
public class TransactionalSeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Transactional
    public SeatEntity createOrUpdate (SeatEntity seatEntity){
        if(seatEntity == null){
            throw new IllegalArgumentException("sean is null");
        }
        return seatRepository.createOrUpdate(seatEntity);
    }

    @Transactional
    public Optional<SeatEntity> getById(Long id){
        return seatRepository.findById(id);
    }

    @Transactional
    public void remove(final SeatEntity seat){
        seatRepository.remove(seat);
    }

    @Transactional
    public void removeById(Long seatId) {
        seatRepository.removeById(seatId);
    }

}
