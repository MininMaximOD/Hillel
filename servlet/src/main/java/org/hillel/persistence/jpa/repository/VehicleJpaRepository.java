package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehicleJpaRepository extends CommonJpaRepository<VehicleEntity, Long>, JpaSpecificationExecutor<VehicleEntity> {


}
