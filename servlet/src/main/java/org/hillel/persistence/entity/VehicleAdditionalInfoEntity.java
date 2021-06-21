package org.hillel.persistence.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "vehicle_additional_info")
@Getter
@Setter
@NoArgsConstructor
public class VehicleAdditionalInfoEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "wifi")
    private Boolean wifi;

    @Column(name = "tv")
    private Boolean tv;

    @OneToOne
    @MapsId
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

}
