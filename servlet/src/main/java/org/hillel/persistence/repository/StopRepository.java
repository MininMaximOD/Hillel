package org.hillel.persistence.repository;

import org.hillel.persistence.entity.StopEntity;
import org.springframework.stereotype.Repository;

@Repository
public class StopRepository extends CommonRepository<StopEntity, Long> {

    protected StopRepository() {
        super(StopEntity.class);
    }

    @Override
    public void remove(StopEntity entity) {
        entity = findById(entity.getId()).get();
        entity.removeAllJourney();
        super.remove(entity);
    }

   /* public Collection<StopEntity> findAllAsNamed() {
        return entityManager.createNamedQuery("findAllAsNamed", StopEntity.class).getResultList();
    }*/

   /* public Collection<StopEntity> findAllAsStoredProcedure(){
        return entityManager.createNamedStoredProcedureQuery("findAllAsStoredProcedure").getResultList();
    }*/
}
