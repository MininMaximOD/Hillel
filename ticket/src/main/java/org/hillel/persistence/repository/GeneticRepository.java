package org.hillel.persistence.repository;

import java.util.Optional;

public interface GeneticRepository<E, ID> {

    E createOrUpdate(E entity);

    Optional<E> findById (ID id);

    void removeById(ID id);

    void remove(E entity);
}
