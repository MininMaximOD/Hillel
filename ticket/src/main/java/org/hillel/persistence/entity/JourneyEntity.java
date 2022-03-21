package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "journey")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@NamedQueries(value = {
        @NamedQuery(name = "findAllAsNamed", query = "from JourneyEntity")
})
@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name = "findAllAsStoredProcedure",
                procedureName = "find_all_seat",
                parameters = @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = Class.class),
                resultClasses = JourneyEntity.class
        )
)
public class JourneyEntity extends AbstractModifyEntity<Long>{

    @Column(name = "station_from", length = 60, nullable = false)
    private String stationFrom;

    @Column(name = "station_to", length = 60, nullable = false)
    private String stationTo;

    @Column(name = "date_from", nullable = false)
    private Instant dateFrom;

    @Column(name= "date_to", nullable = false)
    private Instant dateTo;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name ="vek_id")
    private VehicleEntity vehicle;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "seat_map",
            joinColumns = @JoinColumn(name = "journey_id"), inverseJoinColumns = @JoinColumn(name = "seat_number"))
    private List<SeatEntity> seats = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "journey_stop",
            joinColumns = @JoinColumn(name = "journey_id"), inverseJoinColumns = @JoinColumn(name = "stop_id"))
    private List<StopEntity> stops = new ArrayList<>();

    public void addVehicle(final VehicleEntity vehicle){
        this.vehicle = vehicle;
    }

    public void removeVehicle(){
        vehicle = null;
    }

    public void addStop(final StopEntity stop){
        if (stop == null) return;
        if(stops == null) stops = new ArrayList<>();
        stops.add(stop);
        stop.addJourney(this);
    }

    public void removeAllStops(){
        if (CollectionUtils.isEmpty(stops)) return;
        stops.forEach(item -> item.removeJourney(this));
    }

    public void addSeat(SeatEntity seat){
        if (seat == null) return;
        if(seats == null){
            seats = new ArrayList<>();
        }
        seats.add(seat);
        seat.addJourney(this);
    }

    public void removeAllSeats(){
        if (CollectionUtils.isEmpty(seats)) return;
        seats.forEach(item -> item.setActive(false));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JourneyEntity entity = (JourneyEntity) o;
        return getId() != null && Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationFrom);
    }
}
