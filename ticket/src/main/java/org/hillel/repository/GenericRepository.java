package org.hillel.repository;

import java.util.List;

public interface GenericRepository<T> {
    Integer create(T t);

    List<T> get();

    Integer update(T t);

    boolean delete(T t);
}
