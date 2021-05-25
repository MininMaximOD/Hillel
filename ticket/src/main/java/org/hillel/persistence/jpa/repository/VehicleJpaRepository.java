package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface VehicleJpaRepository extends CommonJpaRepository<VehicleEntity, Long>{


  /*  @Query(value = "select count(*) from VehicleEntity")
    Long findCountLong();*/

}
