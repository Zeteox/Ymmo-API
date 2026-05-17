package fr.loic_delprat.ymmo_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;
}
