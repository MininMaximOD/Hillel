package org.hillel.service;

import org.hillel.persistence.entity.SeatEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Collection<SeatEntity> findAll(){
        return seatRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Collection<SeatEntity> findAllAsNative(){
        return seatRepository.findAllAsNative();
    }

   /* @Transactional(readOnly = true)
    public Collection<SeatEntity> findAllAsNamed(){
        return seatRepository.findAllAsNamed();
    }
*/
    @Transactional(readOnly = true)
    public Collection<SeatEntity> findAllAsCriteria(){
        return seatRepository.findAllAsCriteria();
    }

   /* @Transactional(readOnly = true)
    public Collection<SeatEntity> findAllAsStoredProcedure(){
        return seatRepository.findAllAsStoredProcedure();
    }
*/
}
