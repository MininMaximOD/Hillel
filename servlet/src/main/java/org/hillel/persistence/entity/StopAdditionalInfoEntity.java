package org.hillel.persistence.entity;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "stop_additional_info")
@Getter
@Setter
@NoArgsConstructor
public class StopAdditionalInfoEntity extends AbstractModifyEntity<Long> implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "city")
    private String city;

    @OneToOne
    @MapsId
    @JoinColumn(name = "stop_id")
    private StopEntity stop;

}
