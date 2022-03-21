package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface VehicleJpaRepository extends CommonJpaRepository<VehicleEntity, Long>, JpaSpecificationExecutor<VehicleEntity> {


}
