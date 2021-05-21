package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.CollectionUtils;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "stop")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
/*@NamedQueries(value = {
        @NamedQuery(name = "findAllAsNamed", query = "from StopEntity")
})
@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name = "findAllAsStoredProcedure",
                procedureName = "find_all_stops",
                parameters = @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Class.class),
                resultClasses = StopEntity.class
        )
)*/
public class StopEntity extends AbstractModifyEntity<Long> implements Serializable{

    @Embedded
    private CommonInfo commonInfo;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "stop", cascade = CascadeType.PERSIST)
    private StopAdditionalInfoEntity additionalInfo;

    @ManyToMany(mappedBy = "stops")
    private List<JourneyEntity> journeys = new ArrayList<>();

    public void addStopAdditionalInfo(StopAdditionalInfoEntity stopAdditionalInfo){
        if (stopAdditionalInfo == null){
            this.additionalInfo = null;
            return;
        }
        stopAdditionalInfo.setStop(this);
        this.additionalInfo = stopAdditionalInfo;
    }

    public void addJourney(JourneyEntity journeyEntity) {
        if (journeyEntity == null)return;
        if (journeys == null) journeys = new ArrayList<>();
        journeys.add(journeyEntity);
    }

    public void removeAllJourney() {
        if(CollectionUtils.isEmpty(journeys)) return;
        journeys.forEach(item -> item.setStops(null));
    }

    public void removeJourney(JourneyEntity entity){
        if(entity == null) {
            throw new IllegalArgumentException("entity is null");
        }
        if (journeys.contains(entity)){
            journeys.remove(entity);
        }
    }

    @Override
    public String toString() {
        return "StopEntity{" +
                "commonInfo=" + commonInfo +
                ", name='" + name + '\'' +
                ", additionalInfo=" + additionalInfo +
                '}';
    }
}
