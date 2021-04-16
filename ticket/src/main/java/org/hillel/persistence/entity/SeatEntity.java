package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@NamedQueries(value = {
        @NamedQuery(name = "findAllAsNamed", query = "from SeatEntity")
})
@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name = "findAllAsStoredProcedure",
                procedureName = "find_all_seats",
                parameters = @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Class.class),
                resultClasses = SeatEntity.class
        )
)
public class SeatEntity extends AbstractModifyEntity<Long>{

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "free")
    private Boolean free;

    @Column(name = "class")
    private String seatClass;

    @ManyToMany
    @MapsId
    @JoinColumn(name = "journey_id")
    private List<JourneyEntity> journeys = new ArrayList<>();

    public void addJourney(final JourneyEntity journey) {
        if(journey == null){
            throw new IllegalArgumentException("journey is null");
        }
        if (journeys == null) {
            journeys = new ArrayList<>();
        }
        this.journeys.add(journey);
    }
}
