package fr.loic_delprat.ymmo_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "building_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private BuildingTypeName name;
}
