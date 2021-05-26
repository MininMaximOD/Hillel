package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.AbstractModifyEntity;
import org.hillel.persistence.entity.AbstractModifyEntity_;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.JourneyEntity_;
import org.springframework.data.jpa.domain.Specification;

public enum JourneySpecification {
    BY_STATION_FROM {
        public Specification<JourneyEntity> specification(final String param) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(JourneyEntity_.STATION_FROM), criteriaBuilder.literal(param));
        }
    },
    BY_STATION_TO {
        public Specification<JourneyEntity> specification(final String param) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(JourneyEntity_.STATION_TO), criteriaBuilder.literal(param));
        }
    },
    BY_NAME {
        public Specification<JourneyEntity> specification(final String param) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(JourneyEntity_.NAME), criteriaBuilder.literal(param));
        }
    },
    ONLY_ACTIVE {
        public Specification<JourneyEntity> specification(final String param) {
            boolean active = Boolean.parseBoolean(param);
            if(active) {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.isTrue(root.get(JourneyEntity_.ACTIVE));
            }else {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.isFalse(root.get(JourneyEntity_.ACTIVE));
            }
        }
    },
    BY_ID {
        public Specification<JourneyEntity> specification(final String param) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(JourneyEntity_.ID), criteriaBuilder.literal(Long.parseLong(param)));
        }
    };

    public abstract Specification<JourneyEntity> specification(String param);
}
