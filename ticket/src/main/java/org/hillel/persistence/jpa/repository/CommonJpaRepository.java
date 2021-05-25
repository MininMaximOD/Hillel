package org.hillel.persistence.jpa.repository;

import org.hillel.persistence.entity.AbstractModifyEntity;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.jpa.repository.specification.CommonSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@NoRepositoryBean
public interface CommonJpaRepository<E extends Persistable<ID>, ID extends Serializable> extends JpaRepository<E, Long>{

    /*@Query("select e from #{#entityName} e where e.active = true")
    Collection<E> findOnlyActive();*/

    /*Collection<AbstractModifyEntity> findOnlyActive(Specification<AbstractModifyEntity> spec);*/

   /* Collection<E> findByName(String name);*/

    @Query("select e from #{#entityName} e")
    List<E> findAll();

    @Query(value = "select count(*) from #{#entityName}")
    Long findCountLong();

    @Query("select e from #{#entityName} e")
    List<E> findList(Pageable page);
}
