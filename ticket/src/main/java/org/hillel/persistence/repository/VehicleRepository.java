package org.hillel.persistence.repository;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.VehicleEntity_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

     public Collection<VehicleEntity> findVehiclesWithMaxOrMinSeats(String maxOrMin){
        return entityManager.createNativeQuery("select * from vehicle as v where v.count_seats=(select " + maxOrMin + "(veh.count_seats) from vehicle veh)", VehicleEntity.class).getResultList();
        //select * from vehicle as v where v.count_seats=(select max(veh.count_seats) from vehicle veh)

     }

   /* public Collection<VehicleEntity> findAllAsNamed() {
        return entityManager.createNamedQuery("findAllAsNamed", VehicleEntity.class).getResultList();
    }*/

  /*  public Collection<VehicleEntity> findAllAsStoredProcedure(){
        return entityManager.createNamedStoredProcedureQuery("findAllAsStoredProcedure").getResultList();
    }
*/
    public VehicleEntity findFreeEntity(){
        return entityManager.createQuery("select v from VehicleEntity v where v.status = free", VehicleEntity.class)
                .getSingleResult();
    }
}
