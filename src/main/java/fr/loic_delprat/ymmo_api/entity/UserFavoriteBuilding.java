package fr.loic_delprat.ymmo_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_fav_building", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "building_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavoriteBuilding extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;
}
