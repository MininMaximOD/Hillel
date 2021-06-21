package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.StopAdditionalInfoEntity;
import org.hillel.persistence.entity.StopEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StopAdditionalInfoJpaRepository extends CommonJpaRepository<StopAdditionalInfoEntity, Long>, JpaSpecificationExecutor<StopAdditionalInfoEntity> {
}
