package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
public class SeatEntity{

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
