package fr.loic_delprat.ymmo_api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingResponse {
    private Long id;
    private String name;
    private float price;
    private String description;
    private String address;
    private String type;
    private String state;
    private String zone;
    private Long agencyId;
}

