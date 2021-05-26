package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface StopJpaRepository extends CommonJpaRepository<StopEntity, Long>, JpaSpecificationExecutor<StopEntity> {

}
