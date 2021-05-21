package org.hillel.persistence.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public interface GeneticRepository<E, ID> {

    E createOrUpdate(E entity);

    Optional<E> findById (ID id);

    Collection<E> findByName(String name);

    void removeById(ID id);

    void remove(E entity);

    Collection<E> findByIds(ID ... ids);

    Collection<E> findAll();
}
