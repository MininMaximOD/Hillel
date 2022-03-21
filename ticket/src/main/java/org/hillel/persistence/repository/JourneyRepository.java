package org.hillel.persistence.repository;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JourneyRepository extends CommonRepository<JourneyEntity, Long>{

    protected JourneyRepository() {
        super(JourneyEntity.class);
    }

    @Override
    public JourneyEntity createOrUpdate(JourneyEntity entity){
        VehicleEntity vehicleEntity = entity.getVehicle();
        if(Objects.nonNull((vehicleEntity))) {
            if(!entityManager.contains(vehicleEntity)){
                entity.setVehicle(entityManager.merge(vehicleEntity));
        }
        }
        return super.createOrUpdate(entity);
    }

    @Override
    public void remove(JourneyEntity entity) {
        if(entity == null) return;
        entity = findById(entity.getId()).get();
        entity.removeVehicle();
        entity.removeAllSeats();
        entity.removeAllStops();
        super.remove(entity);
    }

    /*public Collection<JourneyEntity> findAllAsNamed() {
        return entityManager.createNamedQuery("findAllAsNamed", JourneyEntity.class).getResultList();
    }*/

  /*  public Collection<JourneyEntity> findAllAsStoredProcedure(){
        return entityManager.createNamedStoredProcedureQuery("findAllAsStoredProcedure").getResultList();
    }*/
}
