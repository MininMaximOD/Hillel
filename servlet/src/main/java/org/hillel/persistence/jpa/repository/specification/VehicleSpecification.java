package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.entity.VehicleEntity_;
import org.springframework.data.jpa.domain.Specification;

public enum VehicleSpecification {

    BY_NAME {
        public Specification<VehicleEntity> specification(final String param) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(VehicleEntity_.NAME), criteriaBuilder.literal(param));
        }
    },
    ONLY_ACTIVE {
        public Specification<VehicleEntity> specification(final String param) {
            boolean active = Boolean.parseBoolean(param);
            if(active) {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.isTrue(root.get(VehicleEntity_.ACTIVE));
            }else {
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.isFalse(root.get(VehicleEntity_.ACTIVE));
            }
        }
    },
    BY_ID {
        public Specification<VehicleEntity> specification(final String param) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(VehicleEntity_.ID), criteriaBuilder.literal(Long.parseLong(param)));
        }
    },
    BY_ORDER_NUMBER {
        public Specification<VehicleEntity> specification(final String param){
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(VehicleEntity_.ORDER_NUMBER), criteriaBuilder.literal(param));
        }
    },
    BY_VEHICLE_TYPE {
        public Specification<VehicleEntity> specification(final String param) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(VehicleEntity_.VEHICLE_TYPE), criteriaBuilder.literal(param));
        }
    },
    BY_COUNT_SEATS {
        public Specification<VehicleEntity> specification(final String param) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(VehicleEntity_.COUNT_SEATS), criteriaBuilder.literal(Integer.parseInt(param)));
        }
    },
    BY_STATUS {
        public Specification<VehicleEntity> specification(final String param) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(VehicleEntity_.VEHICLE_STATUS), criteriaBuilder.literal(param));
        }
    };
    public abstract Specification<VehicleEntity> specification(String param);
}
