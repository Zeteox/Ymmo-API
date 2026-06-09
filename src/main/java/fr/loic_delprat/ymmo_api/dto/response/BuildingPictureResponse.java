package fr.loic_delprat.ymmo_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingPictureResponse {
    private Long id;
    private String path;
    private Long buildingId;
}
