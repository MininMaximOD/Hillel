package org.hillel.persistence.jpa.repository.specification;

import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filter<E> {

    public Specification<E> builderSpecification(final HashMap<String, String> predicates){
        if(predicates.isEmpty()) throw new IllegalArgumentException("predicates must be a set");
        List<Specification> fullSpecifications = new ArrayList<>();
        for(Map.Entry<String, String> para:predicates.entrySet()){
            String[] keys = para.getKey().split("\\.");
            switch (keys[0]){
                case "journey":
                    fullSpecifications.add(builderJourneySpecification(keys[1], para.getValue())); break;
                case "vehicle":
                    fullSpecifications.add(builderVehicleSpecification(keys[1], para.getValue())); break;
                case "stop":
                    fullSpecifications.add(builderStopSpecification(keys[1], para.getValue())); break;
            }
        }
        if (fullSpecifications == null ){
            return null;
        }else{
            return builderFullSpecification(fullSpecifications);
        }
    }

    private Specification<JourneyEntity> builderJourneySpecification(final String field, final String value){
        switch (field){
            case "name":
                return JourneySpecification.BY_NAME.specification(value);
            case "stationFrom":
                return JourneySpecification.BY_STATION_FROM.specification(value);
            case "stationTo":
                return JourneySpecification.BY_STATION_TO.specification(value);
            case "active":
                return JourneySpecification.ONLY_ACTIVE.specification(value);
            case "id":
                return JourneySpecification.BY_ID.specification(value);
        }
        return null;
    }

    private Specification<VehicleEntity> builderVehicleSpecification(final String field, final String value){
        List<Specification> specificationsVehicle = new ArrayList<>();
        switch (field){
            case "name":
                return VehicleSpecification.BY_NAME.specification(value);
            case "countSeats":
                return VehicleSpecification.BY_COUNT_SEATS.specification(value);
            case "vehicleType":
                return VehicleSpecification.BY_VEHICLE_TYPE.specification(value);
            case "orderNumber":
                return VehicleSpecification.BY_ORDER_NUMBER.specification(value);
            case "status":
                return VehicleSpecification.BY_STATUS.specification(value);
            case "active":
                return VehicleSpecification.ONLY_ACTIVE.specification(value);
            case "id":
                return VehicleSpecification.BY_ID.specification(value);
        }
        return builderFullSpecification(specificationsVehicle);
    }

    private Specification<StopEntity> builderStopSpecification(final String field, final String value){
        List<Specification> specificationsStop = new ArrayList<>();
        switch (field){
            case "name":
                return StopSpecification.BY_NAME.specification(value);
            case "active":
                return StopSpecification.ONLY_ACTIVE.specification(value);
            case "id":
                return StopSpecification.BY_ID.specification(value);
        }
        return builderFullSpecification(specificationsStop);
    }

    private Specification builderFullSpecification(List<Specification> specifications){
        Specification specification = specifications.get(0);
        for (int i = 1; i<specifications.size(); i++){
            specification = specification.and(specifications.get(i));
        }
        return specification;
    }

}
