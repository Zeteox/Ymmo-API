package fr.loic_delprat.ymmo_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "building_states")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private BuildingStateName name;
}
