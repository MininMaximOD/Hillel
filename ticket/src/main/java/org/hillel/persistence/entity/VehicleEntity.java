package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.graalvm.util.CollectionsUtil;
import org.hillel.persistence.entity.enums.VehicleType;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicle", uniqueConstraints = @UniqueConstraint(name = "uniq_order_number", columnNames = "order_number"))
@Setter
@Getter
@NoArgsConstructor
public class VehicleEntity extends AbstractModifyEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    @Column(name = "type", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "count_seats")
    private Integer countSeats;

    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.PERSIST)
    private VehicleAdditionalInfoEntity additionalInfo;

    @OneToMany(mappedBy = "vehicle")
    private List<JourneyEntity> journeys = new ArrayList<>();

    public void setVehicleAdditionalInfo(VehicleAdditionalInfoEntity vehicleAdditionalInfo){
        if(vehicleAdditionalInfo == null){
            vehicleAdditionalInfo = null;
        }
        vehicleAdditionalInfo.setVehicle(this);
        this.additionalInfo = vehicleAdditionalInfo;
    }

    public void addJourney(final JourneyEntity journey){
        if (journeys == null){
            journeys = new ArrayList<>();
        }
        journeys.add(journey);
        journey.addVehicle(this);
    }

    public void removeAllJourney(){
        if(CollectionUtils.isEmpty(journeys)) return;;
        journeys.forEach(item->item.setVehicle(null));
    }
}
