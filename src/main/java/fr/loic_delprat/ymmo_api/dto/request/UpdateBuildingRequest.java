package fr.loic_delprat.ymmo_api.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBuildingRequest {

    private String name;

    @Min(0)
    private Float price;

    private String address;

    private String description;

    private Long agencyId;

    private String buildingType;

    private String buildingState;

    private String zone;
}