package org.hillel.persistence.repository;

import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;

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
