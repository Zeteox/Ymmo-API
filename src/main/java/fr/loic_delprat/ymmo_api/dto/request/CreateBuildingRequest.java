package fr.loic_delprat.ymmo_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBuildingRequest {

    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private Float price;

    @NotBlank
    private String address;

    @NotBlank
    private String description;

    @NotNull
    private Long agencyId;

    @NotNull
    private Long buildingTypeId;

    @NotNull
    private Long buildingStateId;

    @NotNull
    private Long zoneId;
}