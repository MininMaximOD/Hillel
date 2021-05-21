package org.hillel.persistence.repository;

import lombok.SneakyThrows;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.hillel.persistence.entity.AbstractModifyEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public abstract class CommonRepository<E extends AbstractModifyEntity<ID>, ID extends Serializable> implements GeneticRepository<E, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<E> entityClass;

    protected CommonRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public E createOrUpdate(E entity) {
        Assert.notNull(entity, "entity must be a set");
        if(Objects.isNull(entity.getId())){
            entityManager.persist(entity);
        }else{
            return entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public Optional<E> findById(ID id) {
        if(Objects.isNull(id)) return Optional.empty();
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public Collection<E> findByName(String name) {
        return entityManager.createQuery("from " + entityClass.getName() + " e where e.name = " + "?1", entityClass).setParameter(1, name).getResultList();
       /* return entityManager.createNativeQuery("select * from " + entityClass.getAnnotation(Table.class).name() + "e where e.name = :entity_name ", entityClass)
                .setParameter("entity_name", name)
                .getResultList();

        */
    }

    @SneakyThrows
    @Override
    public void removeById(ID id) {
        entityManager.remove(entityManager.getReference(entityClass, id));

    }

    @Override
    public void remove(E entity) {
        if(entityManager.contains(entity)){
            entityManager.remove(entity);
        }else {
            removeById(entity.getId());
        }
    }

    @Override
    public Collection<E> findByIds(ID... ids) {
        entityManager.unwrap(Session.class).byMultipleIds(entityClass).multiLoad(ids);
        return null;
    }

    @Override
    public Collection<E> findAll() {
        return entityManager.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
    }

    public Collection<E> findAllAsNative(){
        if(!entityClass.isAnnotation()) throw new IllegalArgumentException("entity Class is not have Annotation");
        String tableName = entityClass.getAnnotation(Table.class).name();
        if(tableName.isEmpty()){
            throw new IllegalArgumentException("table name is not correct");
        }
        return entityManager.createNativeQuery("select * from " + tableName, entityClass).getResultList();
    }

    public Collection<E> findAllAsCriteria(){
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        final Root<E> from = query.from(entityClass);
        return entityManager.createQuery(query.select(from)).getResultList();
    }

    public Collection<E> findAllWithSorted(int numberPage, int countValue, String sortsField, boolean ascending){
        int startPosition = (numberPage-1) * countValue;
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        final Root<E> from = query.from(entityClass);

        return entityManager.createQuery(query.select(from)
                .orderBy(new OrderImpl(from.get(sortsField), ascending))
        )
                .setFirstResult(startPosition)
                .setMaxResults(countValue)
                .getResultList();
    }

    public BigInteger getCountEntitys(){
/*        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<E> query = criteriaBuilder.createQuery(entityClass);
        Root<E> from = query.from(entityClass);
        entityManager.createQuery(query
                        .select(from).*/

        return (BigInteger) entityManager.createNativeQuery("select count(*) from " + entityClass.getAnnotation(Table.class).name()).getSingleResult();
//        return (Long) entityManager.createQuery("count(*) from " + entityClass.getSimpleName(), entityClass).getResultList();
//        return (int) entityManager.createNativeQuery("select count(*) from " + entityClass.getAnnotation(Table.class).name()).getSingleResult();
    }
}
