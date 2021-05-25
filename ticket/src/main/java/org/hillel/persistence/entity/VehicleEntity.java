package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hillel.persistence.entity.enums.VehicleStatus;
import org.hillel.persistence.entity.enums.VehicleType;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "vehicle", uniqueConstraints = @UniqueConstraint(name = "uniq_order_number", columnNames = "order_number"))
@Setter
@Getter
@NoArgsConstructor
/*@NamedQueries(value = {
        @NamedQuery(name = "findAllAsNamed", query = "from VehicleEntity")
})
@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name = "findAllAsStoredProcedure",
                procedureName = "find_all_vehicles",
                parameters = @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Class.class),
                resultClasses = VehicleEntity.class
        )
)*/
public class VehicleEntity extends AbstractModifyEntity<Long> {

    /*@Column(name = "name", nullable = false)
    private String name;
*/
    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @Column(name = "type", length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(name = "count_seats")
    private Integer countSeats;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VehicleStatus vehicleStatus = VehicleStatus.FREE;

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
        this.setVehicleStatus(VehicleStatus.ON_ROUTE);
    }

    public void removeAllJourney(){
        if(CollectionUtils.isEmpty(journeys)) return;;
        journeys.forEach(item->item.setVehicle(null));
        this.setVehicleStatus(VehicleStatus.FREE);
    }

    @Override
    public String toString() {
        return "VehicleEntity{" +
                "id=" + getId() + " , " +
                "name='" + getName() + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", vehicleType=" + vehicleType +
                ", countSeats=" + countSeats +
                ", vehicleStatus=" + vehicleStatus +
                ", additionalInfo=" + additionalInfo +
                '}';
    }
}
