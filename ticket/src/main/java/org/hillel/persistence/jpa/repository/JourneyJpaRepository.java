package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.JourneyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface JourneyJpaRepository extends CommonJpaRepository<JourneyEntity, Long>, JpaSpecificationExecutor<JourneyEntity> {

    /*List<JourneyEntity> findAllWithSorted(Pageable page);*/

    /*@Query(value = "select count(*) from JourneyEntity")
    Long findCountLong();*/
    /*@Query("select e from JourneyEntity e")
    List<JourneyEntity> findList(Pageable page);*/


}

