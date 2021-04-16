package org.hillel.persistence.repository;

import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Objects;

@Repository
public class VehicleRepository  extends CommonRepository<VehicleEntity, Long>{

    protected VehicleRepository() {
        super(VehicleEntity.class);
    }

    @Override
    public void remove(VehicleEntity entity) {
        entity = findById(entity.getId()).get();
        entity.removeAllJourney();
        super.remove(entity);
    }

    public Collection<VehicleEntity> findAllAsNamed() {
        return entityManager.createNamedQuery("findAllAsNamed", VehicleEntity.class).getResultList();
    }

    public Collection<VehicleEntity> findAllAsStoredProcedure(){
        return entityManager.createNamedStoredProcedureQuery("findAllAsStoredProcedure").getResultList();
    }
}
