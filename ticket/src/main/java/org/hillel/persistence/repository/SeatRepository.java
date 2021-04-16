package org.hillel.persistence.repository;

import org.hillel.persistence.entity.SeatEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class SeatRepository extends CommonRepository<SeatEntity, Long>{

    protected SeatRepository() {
        super(SeatEntity.class);
    }

    public Collection<SeatEntity> findAllAsNamed() {
        return entityManager.createNamedQuery("findAllAsNamed", SeatEntity.class).getResultList();
    }

    public Collection<SeatEntity> findAllAsStoredProcedure(){
        return entityManager.createNamedStoredProcedureQuery("findAllAsStoredProcedure").getResultList();
    }
}
