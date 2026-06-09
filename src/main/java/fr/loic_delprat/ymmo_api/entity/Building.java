package fr.loic_delprat.ymmo_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "buildings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Min(0)
    private float price;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "building_type_id", nullable = false)
    private BuildingType buildingType;

    @ManyToOne
    @JoinColumn(name = "building_state_id", nullable = false)
    private BuildingState buildingState;

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;
}
