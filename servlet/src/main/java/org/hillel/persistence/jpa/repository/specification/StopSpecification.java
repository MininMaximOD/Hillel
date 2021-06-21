package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.StopEntity_;
import org.springframework.data.jpa.domain.Specification;

public enum StopSpecification {

    BY_NAME {
        public Specification<StopEntity> specification(final String param) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(StopEntity_.NAME), criteriaBuilder.literal(param));
        }
    },
    ONLY_ACTIVE {
        public Specification<StopEntity> specification(final String param) {
            boolean active = Boolean.parseBoolean(param);
            if(active) {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.isTrue(root.get(StopEntity_.ACTIVE));
            }else {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.isFalse(root.get(StopEntity_.ACTIVE));
            }
        }
    },
    BY_ID {
        public Specification<StopEntity> specification(final String param) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(StopEntity_.ID), criteriaBuilder.literal(Long.parseLong(param)));
        }
    };

    public abstract Specification<StopEntity> specification(String param);
}
