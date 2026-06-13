package fr.loic_delprat.ymmo_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_demands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDemand extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
